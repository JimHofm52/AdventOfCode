package days23;

import java.io.IOException;

import util.ReadInput;

public class Day10 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day10(){}

    public static void update() throws IOException {
        // String fNum = "10"; //Part1- ???   Part2- ???
        String fNum = "101";//Part1- ???   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 10- ???   101- ???
        question2();    //Confirmed: 10- ???   101- ???
    }

    /**
     * Question 1: Find the single giant loop starting at S. How many steps 
     * along the loop does it take to get from the starting position to the 
     * point farthest from the starting position?
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