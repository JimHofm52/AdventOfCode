package days22;

import java.io.IOException;

import type.T_Rps;
import util.ReadInput;

public class Day02 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02";
        // String fNum = "021";    //Should rtn a score of 15 (8 + 1 + 6)
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        T_Rps[] rps = parceInput(fileInfo);


        question1(rps);    //Confirmed: 02-10816   021-15
        question2(rps);    //Confirmed: 02-11657   021-12
    }

    /**
     * Question 1: Score for your responses to the opponent's.
     */
    private static void question1(T_Rps[] play) {
        int total1 = 0;
        for(T_Rps rps : play) total1 += calcScore1(rps);
        //Track 11272 hi,  Confirmed: 02-10816   021-15 = 8+1+6
        System.out.println("\nPart 1: Your score: " + total1);
        // System.out.println("\nPart 2: Your score: " + total2);  //Confirmed: 02-11657,  021-12
    }
    
    /**
     * Question 2: Score if you respond lose, tie, win to the opponents play.
     */
    private static void question2(T_Rps[] play) {
        int total = 0;
        for(T_Rps rps : play) total += calcScore2(rps);
        System.out.println("\nPart 2: Your score: " + total);  //Confirmed: 02-11657,  021-12
    }

    /**
     * Parce the string input to an array of opponent's rpsOPP & your rpsYou responses.
     * @param fileIn
     * @return Array of type T_Rps reponses.
     */
    private static T_Rps[] parceInput(String[] fileIn){
        T_Rps[] rps = new T_Rps[fileIn.length];
        for(int i = 0; i < fileIn.length; i++){
            rps[i] = new T_Rps(fileIn[i].charAt(0), fileIn[i].charAt(2));
        }
        return rps;
    }

    /**
     * Calc score for part 1.  X, Y, Z are your responce, rock, paper, sissors.
     * @param rps Array of your Reponses & opponent response 
     * @return score
     */
    public static int calcScore1(T_Rps rps){
        int score = 0;
        switch(rps.rpsYou){
            case 'X':    //Rock(+1) X=A/t, X<B/l, X>C=w
            return 1 + (rps.rpsOpp == 'A' ? 3 : rps.rpsOpp == 'B' ? 0 : 6);
            case 'Y':    //Paper(+2) Y>A/w, Y=B/t, Y<C=l
            return 2 + (rps.rpsOpp == 'A' ? 6 : rps.rpsOpp == 'B' ? 3 : 0);
            case 'Z':    //Sissors (+3) Z<A/l, Z>B/w, Z=C=t
            return 3 + (rps.rpsOpp == 'A' ? 0 : rps.rpsOpp == 'B' ? 6 : 3);
            default:
            System.out.println("HMmmmm 2?  You " + rps.rpsYou + ", oppenent " + rps.rpsOpp);
        }
        return 0;
    }

    /**
     * Calc for part 2.  X, Y & Z are the lose, tie & win
     * <p> detrermine your response to XYZ for opponent play.  And score.
     * @param rps Array of your need to lose, tie, or win to the opponent response 
     * @return score
     */
    public static int calcScore2(T_Rps rps){
        switch(rps.rpsYou){
            case 'X':    //Lose A=>Z, B=>X, C=>Y
            return rps.rpsOpp == 'A' ? 3 : rps.rpsOpp == 'B' ? 1 : 2;
            case 'Y':    //Tie (+3) A=>X, B=>Y, C=>Z
            return 3 + (rps.rpsOpp - '@');
            case 'Z':    //Win (+6) A=>Y, B=>Z, C=>X
            return 6 + (rps.rpsOpp == 'A' ? 2 : rps.rpsOpp == 'B' ? 3 : 1);
            default:
            System.out.println("HMmmmm 2?  You " + rps.rpsYou + ", oppenent " + rps.rpsOpp);
        }
        return 0;
    }

        /**
     * Calc score for part 1.  X, Y, Z are your responce, rock, paper, sissors.
     * @param rps Array of your Reponses & opponent response 
     * @return score
     */
    public static int calcScore1A(T_Rps rps){
        int score = 0;
        switch(rps.rpsOpp){
            case 'A':    //Rock(+1) X=A/t, X<B/l, X>C=w
            return 1 + (rps.rpsYou == 'X' ? 3 : rps.rpsOpp == 'B' ? 0 : 6);
            case 'B':    //Paper(+2) Y>A/w, Y=B/t, Y<C=l
            return 2 + (rps.rpsOpp == 'A' ? 6 : rps.rpsOpp == 'B' ? 3 : 0);
            case 'C':    //Sissors (+3) Z<A/l, Z>B/w, Z=C=t
            return 3 + (rps.rpsOpp == 'A' ? 0 : rps.rpsOpp == 'B' ? 6 : 3);
            default:
            System.out.println("HMmmmm 2?  You " + rps.rpsYou + ", oppenent " + rps.rpsOpp);
        }
        return 0;
    }

}