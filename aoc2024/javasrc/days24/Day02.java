package days24;

import java.io.IOException;

import util.ReadInput;

public class Day02 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards.
     * <p>Started Dec 2, 2024,  Finished on Dec 2, 2024.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
     */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02";//Part1- ???   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 02- ???   021- ???
        question2();    //Confirmed: 02- ???   021- ???
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        //Track ,  Confirmed: 02- ???   021- ???
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 02- ???   021- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}