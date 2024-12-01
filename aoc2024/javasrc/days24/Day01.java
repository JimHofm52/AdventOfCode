package days24;

import java.io.IOException;

import util.ReadInput;
import util.SortAr;

public class Day01 {
    private static String fileInfo[];
    private static int len;

    /**
     * Constructor, not needed but used for standards.
     * <p>Started Dec 1, 2024,  Finished on Dec 1, 2024.
     * <p>Took ~1 hour.  Runtime 0.208.  Done!
     */
    private Day01(){}

    public static void update() throws IOException {
        String fNum = "01";//Part1- 3569916   Part2- 26407426
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        int[] leftNum = new int[len];
        int[] rightNum = new int[len];
        parceInput(leftNum, rightNum);

        question1(leftNum, rightNum);    //Confirmed: 01- 3569916    011- 11
        question2(leftNum, rightNum);    //Confirmed: 01- 26407426   011- 31
    }

    /**
     * Question 1: Within each pair, figure out how far apart the two numbers are; 
     * you'll need to add up all of those distances.
     * 
     * Your actual left and right lists contain many location IDs. What is 
     * the total distance between your lists?
     */
    private static void question1(int[] lNum, int[] rNum) {
        int diff = 0;
        for(int i = 0; i < len; i++) diff += Math.abs(lNum[i] - rNum[i]);
        //Track ,  Confirmed: 01- 3569916   011- 11
        System.out.println("\nPart 1: Total difference is: " + diff);
    }
    
    /**
     * Question 2: This time, you'll need to figure out exactly how often each 
     * number from the left list appears in the right list. Calculate a 
     * total similarity score by adding up each number in the left list after 
     * multiplying it by the number of times that number appears in the right list.
     * 
     * What is their similarity score?
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