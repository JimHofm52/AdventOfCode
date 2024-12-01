package days24;

import java.io.IOException;

import util.ReadInput;
import util.SortAr;

public class Day01 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day01(){}

    public static void update() throws IOException {
        String fNum = "01";//Part1- 3569916   Part2- 26407426
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        int[] leftNum = new int[len];
        int[] rightNum = new int[len];
        parceInput(leftNum, rightNum);

        question1(leftNum, rightNum);    //Confirmed: 01- 3569916   011- 11
        question2(leftNum, rightNum);    //Confirmed: 01- 26407426   011- 31
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1(int[] lNum, int[] rNum) {
        int diff = 0;
        for(int i = 0; i < len; i++) diff += Math.abs(lNum[i] - rNum[i]);
        //Track ,  Confirmed: 01- 3569916   011- 11
        System.out.println("\nPart 1: Total difference is: " + diff);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2(int[] lNum, int[] rNum) {
        int sim = 0;
        for(int i = 0; i < len; i++) sim += cntNum(lNum[i], rNum) * lNum[i];

        //Track ,  Confirmed: 01- 26407426   011- 31
        System.out.println("\nPart 2: Total similarity score is: " + sim);
    }

    /**
     * Parce the input into 2 columns of numbers and sort each column.
     * @param lNum left column of numbers
     * @param rNum right column of numbers
     */
    private static void parceInput(int[] lNum, int[] rNum){
        String[] tmpStr;
        for(int i = 0; i < len; i++){
            tmpStr = fileInfo[i].split("\\s");
            lNum[i] = Integer.parseInt(tmpStr[0]);
            rNum[i] = Integer.parseInt(tmpStr[3]);
        }
        SortAr.sort(lNum);
        SortAr.sort(rNum);
    }

    /**Count the number of occurances of a number in the list of numbers passed.
     * 
     * @param fnd number to be counted
     * @param num list of number to look in
     * @return the count of numbers found in the list
     */
    private static int cntNum(int fnd, int[] num){
        int tmpInt = 0;
        for(int i : num) if(i == fnd) tmpInt++;
        return tmpInt;
    }
}