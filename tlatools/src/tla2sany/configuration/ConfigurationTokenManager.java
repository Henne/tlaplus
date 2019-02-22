// Copyright (c) 2003 Compaq Corporation.  All rights reserved.
/* Generated By:JavaCC: Do not edit this line. ConfigurationTokenManager.java */
/***************************************************************************
* This file was originally created by running javacc the grammar file      *
* config.jj that specifies the parsing of the string                       *
* ConfigurationConstants.defaultConfig.  The current file has since been   *
* modified by DRJ (David Jefferson).                                       *
***************************************************************************/

package tla2sany.configuration;

public class ConfigurationTokenManager implements ConfigurationConstants
{
  public static  java.io.PrintStream debugStream = System.out;
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x10L) != 0L)
         {
            jjmatchedKind = 21;
            return 61;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 40:
         return jjMoveStringLiteralDfa1_0(0x10L);
      default :
         return jjMoveNfa_0(7, 0);
   }
}
static private final int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 42:
         if ((active0 & 0x10L) != 0L)
            return jjStartNfaWithStates_0(1, 4, 56);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
static private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
static private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 174;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 7:
                  if ((0xf400fffa00000000L & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(56);
                  }
                  else if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 22)
                        kind = 22;
                     jjCheckNAdd(67);
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 22)
                        kind = 22;
                  }
                  if (curChar == 36)
                     jjstateSet[jjnewStateCnt++] = 71;
                  else if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 66;
                  else if (curChar == 40)
                     jjstateSet[jjnewStateCnt++] = 61;
                  if (curChar == 36)
                     jjstateSet[jjnewStateCnt++] = 64;
                  break;
               case 61:
               case 56:
                  if ((0xf400fffa00000000L & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(56);
                  break;
               case 59:
                  if (curChar == 41 && kind > 21)
                     kind = 21;
                  break;
               case 62:
                  if (curChar == 40)
                     jjstateSet[jjnewStateCnt++] = 61;
                  break;
               case 63:
                  if (curChar == 36)
                     jjstateSet[jjnewStateCnt++] = 64;
                  break;
               case 65:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 66;
                  break;
               case 66:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjCheckNAdd(67);
                  break;
               case 67:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjCheckNAdd(67);
                  break;
               case 68:
                  if (curChar == 48 && kind > 22)
                     kind = 22;
                  break;
               case 71:
                  if (curChar == 36)
                     jjstateSet[jjnewStateCnt++] = 69;
                  break;
               case 72:
                  if (curChar == 36)
                     jjstateSet[jjnewStateCnt++] = 71;
                  break;
               case 81:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 80;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 7:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(58);
                  }
                  else if ((0x78000000f8000001L & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(56);
                  }
                  if (curChar == 110)
                     jjAddStates(0, 2);
                  else if (curChar == 78)
                     jjAddStates(3, 8);
                  else if (curChar == 112)
                     jjAddStates(9, 10);
                  else if (curChar == 80)
                     jjAddStates(11, 12);
                  else if (curChar == 111)
                     jjAddStates(13, 14);
                  else if (curChar == 79)
                     jjAddStates(15, 16);
                  else if (curChar == 76)
                     jjAddStates(17, 18);
                  else if (curChar == 82)
                     jjAddStates(19, 20);
                  else if (curChar == 66)
                     jjAddStates(21, 22);
                  else if (curChar == 92)
                     jjCheckNAdd(58);
                  else if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 54;
                  else if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 47;
                  else if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 42;
                  else if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 38;
                  else if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 31;
                  else if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 24;
                  else if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 19;
                  else if (curChar == 99)
                     jjstateSet[jjnewStateCnt++] = 14;
                  else if (curChar == 67)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 61:
                  if ((0x78000000f8000001L & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(56);
                  }
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 60;
                  break;
               case 0:
                  if (curChar == 84 && kind > 7)
                     kind = 7;
                  break;
               case 1:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 2:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 3:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 4:
                  if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 5:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 8:
                  if (curChar == 116 && kind > 7)
                     kind = 7;
                  break;
               case 9:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 11:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 12:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 13:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 14:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 15:
                  if (curChar == 99)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 16:
                  if (curChar == 88 && kind > 9)
                     kind = 9;
                  break;
               case 17:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 18:
                  if (curChar == 70)
                     jjstateSet[jjnewStateCnt++] = 17;
                  break;
               case 19:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 18;
                  break;
               case 20:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 21:
                  if (curChar == 120 && kind > 9)
                     kind = 9;
                  break;
               case 22:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 23:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 24:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 25:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 26:
                  if (curChar == 77 && kind > 14)
                     kind = 14;
                  break;
               case 27:
                  if (curChar == 89)
                     jjstateSet[jjnewStateCnt++] = 26;
                  break;
               case 28:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 29:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 30:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 31:
                  if (curChar == 89)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 32:
                  if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 33:
                  if (curChar == 109 && kind > 14)
                     kind = 14;
                  break;
               case 34:
                  if (curChar == 121)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 35:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 36:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 37:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 36;
                  break;
               case 38:
                  if (curChar == 121)
                     jjstateSet[jjnewStateCnt++] = 37;
                  break;
               case 39:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 38;
                  break;
               case 40:
                  if (curChar == 116 && kind > 15)
                     kind = 15;
                  break;
               case 41:
               case 98:
                  if (curChar == 102)
                     jjCheckNAdd(40);
                  break;
               case 42:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 41;
                  break;
               case 43:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 44:
                  if (curChar == 116 && kind > 16)
                     kind = 16;
                  break;
               case 45:
               case 91:
                  if (curChar == 104)
                     jjCheckNAdd(44);
                  break;
               case 46:
                  if (curChar == 103)
                     jjstateSet[jjnewStateCnt++] = 45;
                  break;
               case 47:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 48:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 49:
                  if (curChar == 110 && kind > 18)
                     kind = 18;
                  break;
               case 50:
                  if (curChar == 105)
                     jjCheckNAdd(49);
                  break;
               case 51:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 50;
                  break;
               case 52:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 51;
                  break;
               case 53:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 52;
                  break;
               case 54:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 53;
                  break;
               case 55:
                  if (curChar == 98)
                     jjstateSet[jjnewStateCnt++] = 54;
                  break;
               case 56:
                  if ((0x78000000f8000001L & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(56);
                  break;
               case 57:
                  if (curChar == 92)
                     jjCheckNAdd(58);
                  break;
               case 58:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(58);
                  break;
               case 60:
                  if (curChar == 88)
                     jjstateSet[jjnewStateCnt++] = 59;
                  break;
               case 64:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjstateSet[jjnewStateCnt++] = 64;
                  break;
               case 69:
                  if (curChar == 95)
                     jjCheckNAdd(70);
                  break;
               case 70:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjCheckNAdd(70);
                  break;
               case 73:
                  if (curChar == 66)
                     jjAddStates(21, 22);
                  break;
               case 74:
                  if (curChar == 78 && kind > 18)
                     kind = 18;
                  break;
               case 75:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 74;
                  break;
               case 76:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 75;
                  break;
               case 77:
                  if (curChar == 76)
                     jjstateSet[jjnewStateCnt++] = 76;
                  break;
               case 78:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 77;
                  break;
               case 79:
                  if (curChar == 85)
                     jjstateSet[jjnewStateCnt++] = 78;
                  break;
               case 80:
                  if (curChar == 73)
                     jjCheckNAdd(49);
                  break;
               case 82:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 83:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 82;
                  break;
               case 84:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 83;
                  break;
               case 85:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 84;
                  break;
               case 86:
                  if (curChar == 82)
                     jjAddStates(19, 20);
                  break;
               case 87:
                  if (curChar == 84 && kind > 16)
                     kind = 16;
                  break;
               case 88:
                  if (curChar == 72)
                     jjstateSet[jjnewStateCnt++] = 87;
                  break;
               case 89:
                  if (curChar == 71)
                     jjstateSet[jjnewStateCnt++] = 88;
                  break;
               case 90:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 89;
                  break;
               case 92:
                  if (curChar == 103)
                     jjstateSet[jjnewStateCnt++] = 91;
                  break;
               case 93:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 92;
                  break;
               case 94:
                  if (curChar == 76)
                     jjAddStates(17, 18);
                  break;
               case 95:
                  if (curChar == 84 && kind > 15)
                     kind = 15;
                  break;
               case 96:
                  if (curChar == 70)
                     jjstateSet[jjnewStateCnt++] = 95;
                  break;
               case 97:
                  if (curChar == 69)
                     jjstateSet[jjnewStateCnt++] = 96;
                  break;
               case 99:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 98;
                  break;
               case 100:
                  if (curChar == 79)
                     jjAddStates(15, 16);
                  break;
               case 101:
                  if (curChar == 82 && kind > 8)
                     kind = 8;
                  break;
               case 102:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 101;
                  break;
               case 103:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 102;
                  break;
               case 104:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 103;
                  break;
               case 105:
                  if (curChar == 82)
                     jjstateSet[jjnewStateCnt++] = 104;
                  break;
               case 106:
                  if (curChar == 69)
                     jjstateSet[jjnewStateCnt++] = 105;
                  break;
               case 107:
                  if (curChar == 80)
                     jjstateSet[jjnewStateCnt++] = 106;
                  break;
               case 108:
                  if (curChar == 80 && kind > 8)
                     kind = 8;
                  break;
               case 109:
                  if (curChar == 111)
                     jjAddStates(13, 14);
                  break;
               case 110:
                  if (curChar == 114 && kind > 8)
                     kind = 8;
                  break;
               case 111:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 110;
                  break;
               case 112:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 111;
                  break;
               case 113:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 112;
                  break;
               case 114:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 113;
                  break;
               case 115:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 114;
                  break;
               case 116:
                  if (curChar == 112)
                     jjstateSet[jjnewStateCnt++] = 115;
                  break;
               case 117:
                  if (curChar == 112 && kind > 8)
                     kind = 8;
                  break;
               case 118:
                  if (curChar == 80)
                     jjAddStates(11, 12);
                  break;
               case 119:
                  if (curChar == 88 && kind > 10)
                     kind = 10;
                  break;
               case 120:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 119;
                  break;
               case 121:
                  if (curChar == 70)
                     jjstateSet[jjnewStateCnt++] = 120;
                  break;
               case 122:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 121;
                  break;
               case 123:
                  if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 122;
                  break;
               case 124:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 123;
                  break;
               case 125:
                  if (curChar == 88 && kind > 11)
                     kind = 11;
                  break;
               case 126:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 125;
                  break;
               case 127:
                  if (curChar == 70)
                     jjstateSet[jjnewStateCnt++] = 126;
                  break;
               case 128:
                  if (curChar == 69)
                     jjstateSet[jjnewStateCnt++] = 127;
                  break;
               case 129:
                  if (curChar == 82)
                     jjstateSet[jjnewStateCnt++] = 128;
                  break;
               case 130:
                  if (curChar == 112)
                     jjAddStates(9, 10);
                  break;
               case 131:
                  if (curChar == 120 && kind > 10)
                     kind = 10;
                  break;
               case 132:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 131;
                  break;
               case 133:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 132;
                  break;
               case 134:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 133;
                  break;
               case 135:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 134;
                  break;
               case 136:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 135;
                  break;
               case 137:
                  if (curChar == 120 && kind > 11)
                     kind = 11;
                  break;
               case 138:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 137;
                  break;
               case 139:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 138;
                  break;
               case 140:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 139;
                  break;
               case 141:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 140;
                  break;
               case 142:
                  if (curChar == 78)
                     jjAddStates(3, 8);
                  break;
               case 143:
                  if (curChar == 88 && kind > 12)
                     kind = 12;
                  break;
               case 144:
                  if (curChar == 73)
                     jjstateSet[jjnewStateCnt++] = 143;
                  break;
               case 145:
                  if (curChar == 70)
                     jjstateSet[jjnewStateCnt++] = 144;
                  break;
               case 146:
                  if (curChar == 80 && kind > 13)
                     kind = 13;
                  break;
               case 147:
               case 149:
               case 151:
                  if (curChar == 79)
                     jjCheckNAdd(146);
                  break;
               case 148:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 147;
                  break;
               case 150:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 149;
                  break;
               case 152:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 151;
                  break;
               case 153:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 152;
                  break;
               case 154:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 153;
                  break;
               case 155:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 154;
                  break;
               case 156:
                  if (curChar == 69 && kind > 17)
                     kind = 17;
                  break;
               case 157:
                  if (curChar == 78)
                     jjstateSet[jjnewStateCnt++] = 156;
                  break;
               case 158:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 157;
                  break;
               case 159:
                  if (curChar == 101 && kind > 17)
                     kind = 17;
                  break;
               case 160:
               case 172:
                  if (curChar == 110)
                     jjCheckNAdd(159);
                  break;
               case 161:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 160;
                  break;
               case 162:
                  if (curChar == 110)
                     jjAddStates(0, 2);
                  break;
               case 163:
                  if (curChar == 120 && kind > 12)
                     kind = 12;
                  break;
               case 164:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 163;
                  break;
               case 165:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 164;
                  break;
               case 166:
                  if (curChar == 112 && kind > 13)
                     kind = 13;
                  break;
               case 167:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 166;
                  break;
               case 168:
                  if (curChar == 110)
                     jjstateSet[jjnewStateCnt++] = 167;
                  break;
               case 169:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 168;
                  break;
               case 170:
                  if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 169;
                  break;
               case 171:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 170;
                  break;
               case 173:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 172;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 174 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static private final int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 42:
         return jjMoveStringLiteralDfa1_1(0x20L);
      default :
         return 1;
   }
}
static private final int jjMoveStringLiteralDfa1_1(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 41:
         if ((active0 & 0x20L) != 0L)
            return jjStopAtPos(1, 5);
         break;
      default :
         return 2;
   }
   return 2;
}
static final int[] jjnextStates = {
   165, 171, 173, 145, 148, 150, 155, 158, 161, 136, 141, 124, 129, 116, 117, 107, 
   108, 97, 99, 90, 93, 79, 85, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, };
public static final String[] lexStateNames = {
   "DEFAULT", 
   "IN_COMMENT", 
};
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, 1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xe7ff81L, 
};
static final long[] jjtoSkip = {
   0x2eL, 
};
static final long[] jjtoSpecial = {
   0x20L, 
};
static final long[] jjtoMore = {
   0x50L, 
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[174];
static private final int[] jjstateSet = new int[348];
static StringBuffer image;
static int jjimageLen;
static int lengthOfMatch;
static protected char curChar;
public ConfigurationTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}
public ConfigurationTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   // Bug: The following line used to read "input_stream = stream; but this bug was corrected
   // by DRJ.  The bug should also be corrected in the .jcc file, but isn't.
   input_stream = null;
   ReInitRounds();
}
static private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 174; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
static public void SwitchTo(int lexState)
{
   if (lexState >= 2 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

public static Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }
   image = null;
   jjimageLen = 0;

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100000600L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         if (jjmatchedPos == 0 && jjmatchedKind > 6)
         {
            jjmatchedKind = 6;
         }
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
              SkipLexicalActions(matchedToken);
           }
           else 
              SkipLexicalActions(null);
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
        jjimageLen += jjmatchedPos + 1;
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

static void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
}
