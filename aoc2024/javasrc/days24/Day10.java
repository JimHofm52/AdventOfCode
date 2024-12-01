package days24;

import java.io.IOException;

import util.ReadInput;

public class Day10 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started Dec 10, 2024,  Finished on Dec 10, 2024.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day10(){}

    public static void update() throws IOException {
        String fNum = "10";//Part1- ???   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 10- ???   101- ???
        question2();    //Confirmed: 10- ???   101- ???
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        //Track ,  Confirmed: 10- ???   101- ???
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 10- ???   101- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}