// Copyright (c) 2003 Compaq Corporation.  All rights reserved.
// Portions Copyright (c) 2003 Microsoft Corporation.  All rights reserved.
// Last modified on Wed  4 Jul 2007 at 17:46:34 PST by lamport  
//      modified on Fri Jan 18 11:33:51 PST 2002 by yuanyu   

package tlc2.tool;

import java.io.IOException;

import tla2sany.modanalyzer.SpecObj;
import tla2sany.semantic.ExprNode;
import tlc2.TLCGlobals;
import tlc2.output.EC;
import tlc2.output.MP;
import tlc2.tool.fp.FPSet;
import tlc2.tool.fp.MultiFPSet;
import tlc2.tool.liveness.LiveCheck;
import tlc2.tool.queue.DiskStateQueue;
import tlc2.tool.queue.StateQueue;
import tlc2.util.IdThread;
import tlc2.util.LongVec;
import tlc2.util.ObjLongTable;
import tlc2.value.Value;
import util.DebugPrinter;
import util.FileUtil;
import util.FilenameToStream;
import util.ToolIO;
import util.UniqueString;

/** 
 *  A TLA+ Model checker
 */
// SZ Feb 20, 2009: major refactoring of this class introduced due to the changes
// in the type hierarchy. multiple methods has been pulled up to the super class.
// unused constructors has been removed
// the class now contains only the parts, which are different from the DFIDModelChecker
// the name resolver and support for the external specification object has been added
public class ModelChecker extends AbstractChecker
{
    public FPSet theFPSet; // the set of reachable states (SZ: note the type)
    public StateQueue theStateQueue; // the state queue
    public TLCTrace trace; // the trace file
    protected Worker[] workers; // the workers

    /* Constructors  */
    /**
     * The only used constructor of the TLA+ model checker
     * SZ Feb 20, 2009
     * @param resolver name resolver to be able to load files (specs and configs) from managed environments 
     * @param specObj external SpecObj added to enable to work on existing specification 
     */
    public ModelChecker(String specFile, String configFile, String dumpFile, boolean deadlock, String fromChkpt,
            FilenameToStream resolver, SpecObj specObj) throws EvalException, IOException
    {
        // call the abstract constructor
        super(specFile, configFile, dumpFile, deadlock, fromChkpt, true, resolver, specObj);

        // SZ Feb 20, 2009: this is a selected alternative
        this.theStateQueue = new DiskStateQueue(this.metadir);
        // this.theStateQueue = new MemStateQueue(this.metadir);

        // SZ Feb 20, 2009: this is a selected alternative
        this.theFPSet = new MultiFPSet(1);
        // this.theFPSet = new DiskFPSet(-1);
        // this.theFPSet = new MemFPSet();
        // this.theFPSet = new MemFPSet1();
        // this.theFPSet = new MemFPSet2();

        // initialize the set
        this.theFPSet.init(TLCGlobals.getNumWorkers(), this.metadir, specFile);

        // Finally, initialize the trace file:
        this.trace = new TLCTrace(this.metadir, specFile, this.tool);

        // Initialize all the workers:
        this.workers = new Worker[TLCGlobals.getNumWorkers()];
        for (int i = 0; i < this.workers.length; i++)
        {
            this.workers[i] = new Worker(i, this);
        }
    }

    /**
     * This method does model checking on a TLA+ spec. All the visited
     * states are stored in the variable theFPSet. All the states whose
     * next states have not been explored are stored in the variable
     * theStateQueue.
     */
    public void modelCheck() throws Exception
    {
        report("entering modelCheck()");

        // Initialization for liveness checking:
        if (this.checkLiveness)
        {
            report("initializing liveness checking");
            LiveCheck.init(this.tool, this.actions, this.metadir);
            report("liveness checking initialized");
        }

        boolean recovered = this.recover();
        if (!recovered)
        {

            // We start from scratch. Initialize the state queue and the
            // state set to contain all the initial states.
            if (!this.checkAssumptions())
                return;
            try
            {
                report("doInit(false)");
                // SZ Feb 23, 2009: do not ignore cancel on creation of the init states
                if (!this.doInit(false))
                {
                    report("exiting, because init failed");
                    return;
                }
            } catch (Throwable e)
            {
                report("exception in init");
                report(e);
                // Initial state computation fails with an exception:

                if (this.errState != null)
                {
                    MP.printError(EC.TLC_INITIAL_STATE, new String[] { e.getMessage(), this.errState.toString() });
                } else
                {
                    MP.printError(EC.GENERAL, e.getMessage());
                }

                // Replay the error with the error stack recorded:
                this.tool.setCallStack();
                try
                {
                    this.numOfGenStates = 0;
                    // SZ Feb 23, 2009: ignore cancel on error reporting
                    this.doInit(true);
                } catch (Throwable e1)
                {
                    // Assert.printStack(e);
                    MP.printError(EC.TLC_NESTED_EXPRESSION, this.tool.getCallStack().toString());
                }
                this.printSummary(false);
                this.cleanup(false);
                report("exiting, because init failed with exception");
                return;
            }

            if (this.numOfGenStates == this.theFPSet.size())
            {
                String plural = (this.numOfGenStates == 1) ? "" : "s";
                ToolIO.out.println("Finished computing initial states: " + this.numOfGenStates + " distinct state"
                        + plural + " generated.");
            } else
            {
                ToolIO.out.println("Finished computing initial states: " + this.numOfGenStates
                        + " states generated, with " + this.theFPSet.size() + " of them distinct.");
            }
        }

        report("init processed");
        // Finished if there is no next state predicate:
        if (this.actions.length == 0)
        {
            this.reportSuccess();
            this.printSummary(true);
            this.cleanup(true);
            report("exiting with actions.length == 0");
            return;
        }

        boolean success = false;
        try
        {
            report("running TLC");
            success = this.runTLC(Integer.MAX_VALUE);
            if (!success)
            {
                report("TLC terminated with error");
                return;
            }
            if (this.errState == null)
            {
                // Always check liveness properties at the end:
                if (this.checkLiveness)
                {
                    ToolIO.out.println("--Checking temporal properties for the complete state space...");
                    ToolIO.out.flush();
                    report("checking liveness");
                    success = LiveCheck.check();
                    report("liveness check complete");
                    if (!success)
                    {
                        report("exiting error status on liveness check");
                        return;
                    }
                }

                // We get here because the checking has been completed.
                success = true;
                this.reportSuccess();
            } else if (this.keepCallStack)
            {
                // Replay the error with the error stack recorded:
                this.tool.setCallStack();
                try
                {
                    this.doNext(this.predErrState, new ObjLongTable(10));
                } catch (Throwable e)
                {
                    // Assert.printStack(e);
                    MP.printError(EC.TLC_NESTED_EXPRESSION, this.tool.getCallStack().toString());
                }
            }
        } catch (Exception e)
        {
            // Assert.printStack(e);
            success = false;
            MP.printError(EC.GENERAL, e.getMessage());
        } finally
        {
            this.printSummary(success);
            this.cleanup(success);
        }

        report("normal exit");
    }

    /** 
     * Check the assumptions.  
     */
    public boolean checkAssumptions()
    {
        ExprNode[] assumps = this.tool.getAssumptions();
        for (int i = 0; i < assumps.length; i++)
        {
            try
            {
                if (!this.tool.isValid(assumps[i]))
                {
                    MP.printError(EC.TLC_ASSUMPTION_FALSE, assumps[i].toString());
                    return false;
                }
            } catch (Exception e)
            {
                // Assert.printStack(e);
                MP.printError(EC.TLC_ASSUMPTION_EVALUATION_ERROR,
                        new String[] { assumps[i].toString(), e.getMessage() });
                return false;
            }
        }
        return true;
    }

    /**
     * Initialize the model checker
     * @return status, if false, the processing should be stopped
     * @throws Throwable
     */
    public final boolean doInit(boolean ignoreCancel) throws Throwable
    {
        // SZ Feb 23, 2009: cancel flag set, quit
        if (!ignoreCancel && this.cancellationFlag)
        {
            return false;
        }

        TLCState curState = null;

        try
        {
            // Generate the initial states:
            StateVec theInitStates = this.tool.getInitStates();
            this.numOfGenStates = theInitStates.size();
            for (int i = 0; i < theInitStates.size(); i++)
            {
                curState = theInitStates.elementAt(i);
                // Check if the state is a legal state
                if (!this.tool.isGoodState(curState))
                {
                    MP.printError(EC.TLC_INITIAL_STATE, curState.toString());
                    return false;
                }
                boolean inModel = this.tool.isInModel(curState);
                boolean seen = false;
                if (inModel)
                {
                    long fp = curState.fingerPrint();
                    seen = this.theFPSet.put(fp);
                    if (!seen)
                    {
                        if (this.allStateWriter != null)
                        {
                            this.allStateWriter.writeState(curState);
                        }
                        curState.uid = this.trace.writeState(1, fp);
                        this.theStateQueue.enqueue(curState);

                        // build behavior graph for liveness checking
                        if (this.checkLiveness)
                        {
                            LiveCheck.addInitState(curState, fp);
                        }
                    }
                }
                // Check properties of the state:
                if (!seen)
                {
                    for (int j = 0; j < this.invariants.length; j++)
                    {
                        if (!this.tool.isValid(this.invariants[j], curState))
                        {
                            // We get here because of invariant violation:
                            MP.printError(EC.TLC_INVARIANT_VIOLATED_INITIAL, new String[] {this.tool.getInvNames()[j].toString(), curState.toString()});
                            if (!TLCGlobals.continuation)
                                return false;
                        }
                    }
                    for (int j = 0; j < this.impliedInits.length; j++)
                    {
                        if (!this.tool.isValid(this.impliedInits[j], curState))
                        {
                            // We get here because of implied-inits violation:
                            MP.printError(EC.TLC_PROPERTY_VIOLATED_INITIAL, new String[] {this.tool.getImpliedInitNames()[j], curState.toString()});
                            return false;
                        }
                    }
                }
            }
        } catch (Throwable e)
        {
            // Assert.printStack(e);
            if (e instanceof OutOfMemoryError)
            {
                MP.printError(EC.SYSTEM_OUT_OF_MEMORY_TOO_MANY_INIT);
                return false;
            }
            this.errState = curState;
            throw e;
        }
        return true;
    }

    /**
     * Compute the set of the next states.  For each next state, check that
     * it is a valid state, check that the invariants are satisfied, check
     * that it satisfies the constraints, and enqueue it in the state queue.
     * Return true if the model checking should stop.
     * 
     * This method is called from the workers on every step
     */
    public final boolean doNext(TLCState curState, ObjLongTable counts) throws Throwable
    {
        // SZ Feb 23, 2009: cancel the calculation
        if (this.cancellationFlag)
        {
            return false;
        }

        boolean deadLocked = true;
        TLCState succState = null;
        StateVec liveNextStates = null;
        LongVec liveNextFPs = null;

        if (this.checkLiveness)
        {
            liveNextStates = new StateVec(2);
            liveNextFPs = new LongVec(2);
        }

        try
        {
            int k = 0;
            // <--
            // <--
            for (int i = 0; i < this.actions.length; i++)
            {
                // SZ Feb 23, 2009: cancel the calculation
                if (this.cancellationFlag)
                {
                    return false;
                }

                StateVec nextStates = this.tool.getNextStates(this.actions[i], curState);
                int sz = nextStates.size();
                this.incNumOfGenStates(sz);
                deadLocked = deadLocked && (sz == 0);

                for (int j = 0; j < sz; j++)
                {
                    succState = nextStates.elementAt(j);
                    // Check if succState is a legal state.
                    if (!this.tool.isGoodState(succState))
                    {
                        if (this.setErrState(curState, succState, false))
                        {
                            MP.printError(EC.TLC_STATE_NOT_COMPLETELY_SPECIFIED_NEXT);
                            this.trace.printTrace(curState.uid, curState, succState);
                            this.theStateQueue.finishAll();

                            synchronized (this)
                            {
                                this.notify();
                            }
                        }
                        return true;
                    }
                    if (TLCGlobals.coverageInterval >= 0)
                    {
                        ((TLCStateMutSource) succState).addCounts(counts);
                    }

                    boolean inModel = (this.tool.isInModel(succState) && this.tool.isInActions(curState, succState));
                    boolean seen = false;
                    if (inModel)
                    {
                        long fp = succState.fingerPrint();
                        seen = this.theFPSet.put(fp);
                        if (!seen)
                        {
                            // Write out succState when needed:
                            if (this.allStateWriter != null)
                            {
                                this.allStateWriter.writeState(succState);
                            }
                            // Enqueue succState only if it satisfies the model constraints:
                            long loc = this.trace.writeState(curState.uid, fp);
                            succState.uid = loc;
                            this.theStateQueue.sEnqueue(succState);
                        }
                        // For liveness checking:
                        if (this.checkLiveness)
                        {
                            liveNextStates.addElement(succState);
                            liveNextFPs.addElement(fp);
                        }
                    }
                    // Check if succState violates any invariant:
                    if (!seen)
                    {
                        try
                        {
                            int len = this.invariants.length;
                            for (k = 0; k < len; k++)
                            {
                                // SZ Feb 23, 2009: cancel the calculation
                                if (this.cancellationFlag)
                                {
                                    return false;
                                }

                                if (!tool.isValid(this.invariants[k], succState))
                                {
                                    // We get here because of invariant violation:
                                    synchronized (this)
                                    {
                                        if (TLCGlobals.continuation)
                                        {
                                            MP.printError(EC.TLC_INVARIANT_VIOLATED_BEHAVIOR, this.tool.getInvNames()[k]);
                                            this.trace.printTrace(curState.uid, curState, succState);
                                            break;
                                        } else
                                        {
                                            if (this.setErrState(curState, succState, false))
                                            {
                                                MP.printError(EC.TLC_INVARIANT_VIOLATED_BEHAVIOR, this.tool.getInvNames()[k]);
                                                this.trace.printTrace(curState.uid, curState, succState);
                                                this.theStateQueue.finishAll();
                                                this.notify();
                                            }
                                            return true;
                                        }
                                    }
                                }
                            }
                            if (k < len)
                                continue;
                        } catch (Exception e)
                        {
                            if (this.setErrState(curState, succState, true))
                            {
                                MP.printError(EC.TLC_INVARIANT_EVALUATION_FAILED, new String[]{this.tool.getInvNames()[k], e.getMessage()});
                                this.trace.printTrace(curState.uid, curState, succState);
                                this.theStateQueue.finishAll();
                                this.notify();
                            }
                            throw e;
                        }
                    }
                    // Check if the state violates any implied action. We need to do it
                    // even if succState is not new.
                    try
                    {
                        int len = this.impliedActions.length;
                        for (k = 0; k < len; k++)
                        {
                            // SZ Feb 23, 2009: cancel the calculation
                            if (this.cancellationFlag)
                            {
                                return false;
                            }

                            if (!tool.isValid(this.impliedActions[k], curState, succState))
                            {
                                // We get here because of implied-action violation:
                                synchronized (this)
                                {
                                    if (TLCGlobals.continuation)
                                    {
                                        MP.printError(EC.TLC_ACTION_PROPERTY_VIOLATED_BEHAVIOR, this.tool.getImpliedActNames()[k]);
                                        this.trace.printTrace(curState.uid, curState, succState);
                                        break;
                                    } else
                                    {
                                        if (this.setErrState(curState, succState, false))
                                        {
                                            MP.printError(EC.TLC_ACTION_PROPERTY_VIOLATED_BEHAVIOR, this.tool.getImpliedActNames()[k]);
                                            this.trace.printTrace(curState.uid, curState, succState);
                                            this.theStateQueue.finishAll();
                                            this.notify();
                                        }
                                        return true;
                                    }
                                }
                            }
                        }
                        if (k < len)
                            continue;
                    } catch (Exception e)
                    {
                        if (this.setErrState(curState, succState, true))
                        {
                            MP.printError(EC.TLC_ACTION_PROPERTY_EVALUATION_FAILED, new String[]{this.tool.getImpliedActNames()[k], e.getMessage()});
                            this.trace.printTrace(curState.uid, curState, succState);
                            this.theStateQueue.finishAll();
                            this.notify();
                        }
                        throw e;
                    }
                }
                // Must set state to null!!!
                succState = null;
            }
            // Check for deadlock:
            if (deadLocked && this.checkDeadlock)
            {
                synchronized (this)
                {
                    if (this.setErrState(curState, null, false))
                    {
                        MP.printError(EC.TLC_DEADLOCK_REACHED);
                        this.trace.printTrace(curState.uid, curState, null);
                        this.theStateQueue.finishAll();
                        this.notify();
                    }
                }
                return true;
            }
            // Finally, add curState into the behavior graph for liveness checking:
            if (this.checkLiveness)
            {
                // Add the stuttering step:
                long curStateFP = curState.fingerPrint();
                liveNextStates.addElement(curState);
                liveNextFPs.addElement(curStateFP);
                LiveCheck.addNextState(curState, curStateFP, liveNextStates, liveNextFPs);
            }
            return false;
        } catch (Throwable e)
        {
            // Assert.printStack(e);
            boolean keep = ((e instanceof StackOverflowError) || (e instanceof OutOfMemoryError));
            synchronized (this)
            {
                if (this.setErrState(curState, succState, !keep))
                {
                    if (e instanceof StackOverflowError)
                    {
                        MP.printError(EC.SYSTEM_STACK_OVERFLOW);
                    } else if (e instanceof OutOfMemoryError)
                    {
                        MP.printError(EC.SYSTEM_OUT_OF_MEMORY);
                    } else
                    {
                        MP.printError(EC.GENERAL, e.getMessage());
                    }
                    this.trace.printTrace(curState.uid, curState, succState);
                    this.theStateQueue.finishAll();
                    this.notify();
                }
            }
            throw e;
        }
    }

    /**
     * Things need to be done here:
     * Check liveness: check liveness properties on the partial state graph.
     * Create checkpoint: checkpoint three data structures: the state set,
     *                    the state queue, and the state trace.
     */
    public final boolean doPeriodicWork() throws Exception
    {
        if (this.theStateQueue.suspendAll())
        {
            // Run liveness checking, if needed:
            long stateNum = this.theFPSet.size();
            boolean doCheck = this.checkLiveness && (stateNum >= nextLiveCheck);
            if (doCheck)
            {
                ToolIO.out.println("--Checking temporal properties for the current state space...");
                if (!LiveCheck.check())
                    return false;
                nextLiveCheck = (stateNum <= 640000) ? stateNum * 2 : stateNum + 640000;
            }

            // Checkpoint:
            ToolIO.out.print("--Checkpointing of run " + this.metadir + " compl");
            // start checkpointing:
            this.theStateQueue.beginChkpt();
            this.trace.beginChkpt();
            this.theFPSet.beginChkpt();
            this.theStateQueue.resumeAll();
            UniqueString.internTbl.beginChkpt(this.metadir);
            if (this.checkLiveness)
                LiveCheck.beginChkpt();

            // commit checkpoint:
            this.theStateQueue.commitChkpt();
            this.trace.commitChkpt();
            this.theFPSet.commitChkpt();
            UniqueString.internTbl.commitChkpt(this.metadir);
            if (this.checkLiveness)
                LiveCheck.commitChkpt();
            ToolIO.out.println("eted.");
        }
        return true;
    }

    public final boolean recover() throws IOException
    {
        boolean recovered = false;
        if (this.fromChkpt != null)
        {
            // We recover from previous checkpoint.
            ToolIO.out.println("--Starting recovery from checkpoint " + this.fromChkpt);
            this.trace.recover();
            this.theStateQueue.recover();
            this.theFPSet.recover();
            if (this.checkLiveness)
                LiveCheck.recover();
            ToolIO.out.println("--Recovery completed. " + this.recoveryStats());
            recovered = true;
            this.numOfGenStates = this.theFPSet.size();
        }
        return recovered;
    }

    private final String recoveryStats()
    {
        return (this.theFPSet.size() + " states examined. " + this.theStateQueue.size() + " states on queue.");
    }

    private final String stats()
    {
        return (this.numOfGenStates + " states generated, " + this.theFPSet.size() + " distinct states found, "
                + this.theStateQueue.size() + " states left on queue.");
    }

    private final void cleanup(boolean success) throws IOException
    {
        this.theFPSet.close();
        this.trace.close();
        if (this.checkLiveness)
            LiveCheck.close();
        if (this.allStateWriter != null)
            this.allStateWriter.close();
        FileUtil.deleteDir(this.metadir, success);
    }

    public final void printSummary(boolean success) throws IOException
    {
        super.reportCoverage(this.workers);
        ToolIO.out.println(this.stats());
        if (success)
        {
            ToolIO.out.println("The depth of the complete state graph search is " + this.trace.getLevel() + ".");
        }
    }

    public final void reportSuccess() throws IOException
    {
        long d = this.theFPSet.size();
        double prob1 = (d * (this.numOfGenStates - d)) / Math.pow(2, 64);
        double prob2 = this.theFPSet.checkFPs();

        ToolIO.out.println("Model checking completed. No error has been found.\n"
                + "  Estimates of the probability that TLC did not check "
                + "all reachable states\n  because two distinct states had " + "the same fingerprint:\n"
                + "    calculated (optimistic):  " + prob1 + "\n" + "    based on the actual fingerprints:  " + prob2);
    }

    public final void setAllValues(int idx, Value val)
    {
        for (int i = 0; i < this.workers.length; i++)
        {
            workers[i].setLocalValue(idx, val);
        }
    }

    public final Value getValue(int i, int idx)
    {
        return workers[i].getLocalValue(idx);
    }

    /**
     * Spawn the worker threads
     */
    protected IdThread[] startWorkers(AbstractChecker checker, int checkIndex)
    {
        for (int i = 0; i < this.workers.length; i++)
        {
            this.workers[i].start();
        }
        return this.workers;
    }

    /**
     * Work to be done prior entering to the worker loop
     */
    protected void runTLCPreLoop()
    {
        // nothing to do in this implementation
    }

    /**
     * Process calculation 
     * @param count
     * @param depth
     * @throws Exception
     */
    protected void runTLCContinueDoing(int count, int depth) throws Exception
    {
        int level = this.trace.getLevel();
        ToolIO.out.println("Progress(" + level + "): " + this.stats());
        if (level > depth)
        {
            this.theStateQueue.finishAll();
            this.done = true;
        } else
        {
            if (count == 0)
            {
                super.reportCoverage(this.workers);
                count = TLCGlobals.coverageInterval / TLCGlobals.progressInterval;
            } else
            {
                count--;
            }
            this.wait(TLCGlobals.progressInterval);
        }
    }

    /**
     * Debugging support
     * @param message
     */
    private void report(String message)
    {
        DebugPrinter.print(message);
    }

    /**
     * Debugging support
     * @param e
     */
    private void report(Throwable e)
    {
        DebugPrinter.print(e);
    }

}