package days23;

import java.io.IOException;

import util.ReadInput;

public class Day09 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day09(){}

    public static void update() throws IOException {
        // String fNum = "09"; //Part1- ???   Part2- ???
        String fNum = "091";//Part1- ???   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 09- ???   091- ???
        question2();    //Confirmed: 09- ???   091- ???
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        //Track ,  Confirmed: 09- ???   041- ???
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 09- ???   041- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}