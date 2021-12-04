package days;

import java.io.IOException;
import util.ReadInput;

public class Day03 {
    private static String fileInfo[];
    private static int len;

    /**
     * Constructor, not needed but used for standards.
     */
    public Day03(){
    }

    public static void update() throws IOException {
        String fNum = "03";
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 3
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 03-2635 * 1460 = 3847100   031-22 *  9 = 198
        question2();    //Confirmed: 03-2735 * 1501 = 4105235   031-23 * 10 = 230
    }

    /**
     * Question 1: Evalluate list for gamma epsilon rate
     * <p> Each bit in the gamma rate can be determined by finding the
     * most common bit in the corresponding position of all numbers
     * in the diagnostic report.
     * <p> The epsilon rate bits is are 1 for least least common.
     * <p> Note: least common can also be ready as less then or equal. 
     * 012345
     * 011101
     */
    private static void question1() {
        int gRate = 0;
        int eRate = 0;
        int col = fileInfo[0].length();
        int[] gCnt = new int[col];
        for(String s : fileInfo) cntRate(s, gCnt);      //Cnt each column
        
        for(int i = 0; i < col; i++){                       //Total gRate.  
            if(gCnt[i] > len / 2) gRate |= 1 << (i);        //If .EQ. don't cnt
            if(len - gCnt[i] > len / 2) eRate |= 1 << (i);  //either way.
        }
        //Track 15335424 hi,  ,  Confirmed: 03-2635 * 1460 = 3847100   031-22 *  9 = 198
        System.out.println("\nPart 1: gamma: " + gRate + " * epsilon rate: " + eRate +
                                         " = " + gRate * eRate);
    }
    
    /**
     * Cnt bits in each column for a daignostic sting in.  Return in gCnt[].
     * @param diagIn
     * @param gCnt
     * @return
     */
    private static int cntRate(String diagIn, int[] gCnt){
        int evalCnt = 0;
        int col = diagIn.length();
        for(int i = 0; i < col; i++){
            if(diagIn.charAt(i) == '1') gCnt[col - 1 - i]++;    
        }
        return evalCnt;
    }

    /**
     * Question 2: Find the Oxygen Generator & CO2 Scrubber rating.
     * <p>-Keep only numbers selected by the bit criteria
     * <p>-If you only have one number left, stop;
     * <p>-Repeat the process, considering the next bit to the right.
     * <p>OxyRate, Most common, keep nums w/that value. If .EQ. keep 1's.
     * <p>C02Rate, Least common, keep nums w/that value. If .EQ. keep 0's.
     */
    private static void question2() {
        int oxg = eval(true);   //Evaluate for oxygen generator
        int co2 = eval(false);  //Evaluate for CO2 scrubber

        //Track ,  Confirmed: 03-2735 * 1501 = 4105235   031-23 * 10 = 230
        System.out.println("\nPart 2: Oxy: " + oxg + " * CO2: " + co2 +
                            " produces Life support rating: " + oxg * co2);
    }

    /**
     * Evaluate for most significant (oxy gen) else least significant (CO2 scrubber)
     * @param oxyGen if true do oxy gen else do co2 srubber
     * @return integer of last number found
     */
    private static int eval(boolean oxyGen){
        boolean[] eval = new boolean[len];
        char bitChar = 0;
        int idx;

        for(int b = 0; b < len; b++) eval[b] = true; //Initalize as true.

        for(int c = 0; c < fileInfo[0].length(); c++){  //For each col
            bitChar = cntBits(c, eval, oxyGen);         //get '0' or '1'
            idx = 0;
            for(int s = 0; s < len; s++){
                eval[s] &= fileInfo[s].charAt(c) == bitChar;    //if not match stop eval
                if(eval[s]) idx++;      //count of still true
            }
            if(idx < 2) break;  //stop when at 1
        }
        idx = 0;
        while(!eval[idx]) idx++;    //Find index still true
        return strBin2Int(fileInfo[idx]);   //Convert binary string to integer & return
    }

    /**
     * Count bits in column as 0 and 1.  Return char '0' or '1' for
     * most or least as requested.
     * @param col - column to evaluate
     * @param eval - array if string should be evaluated.
     * @param mostCmn - true most common else least common
     * @return char '0' or '1'
     */
    private static char cntBits(int col, boolean[] eval, boolean mostCmn){
        int[] bitCnt = new int[2];
        for(int s = 0; s < len; s++){
            if(eval[s]){
                if(fileInfo[s].charAt(col) == '0') bitCnt[0]++;
                if(fileInfo[s].charAt(col) == '1') bitCnt[1]++;
            }
        }

        if(mostCmn){
            return bitCnt[1] >= bitCnt[0] ? '1' : '0';  //oxy use most common
        }else{
            return bitCnt[0] <= bitCnt[1] ? '0' : '1';  //co2 use least common
        }
    }

    /**
     * Convert a string of 0's and 1's to an integer
     * @param x - string to convert
     * @return integer
     */
    private static int strBin2Int(String x){
        int y = 0;
        int d = 1;
        int sLen = x.length();
        for(int i = 0; i < sLen; i++){
            y += x.charAt(sLen - 1 - i) == '1' ? d : 0;
            d = d << 1; //1,2,4,8,16,32, ...
        }
        return y;
    }
}