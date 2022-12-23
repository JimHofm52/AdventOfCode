package days22;

import java.io.IOException;

import util.ReadInput;

public class Day11 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day11(){}

    public static void update() throws IOException {
        // String fNum = "11"; //Part1- ???   Part2- ???
        String fNum = "111";//Part1- 10605   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 11- ???   111- 10605
        question2();    //Confirmed: 11- ???   111- ???
    }

    /**
     * Question 1: What is the level of monkey business 
     * after 20 rounds of stuff-slinging simian shenanigans?
     */
    private static void question1() {
        //Track ,  Confirmed: 11- ???   111- 10605
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 11- ???   111- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}