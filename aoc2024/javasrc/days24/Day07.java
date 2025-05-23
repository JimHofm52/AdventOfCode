package days24;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day07 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started May 23, 2025,  Finished on Dec 7, 20??.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day07(){}

    public static void update() throws IOException {
        String fNum = "07";//Part1- ???   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 07- ???   071- ???
        question2();    //Confirmed: 07- ???   071- ???
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        //Track ,  Confirmed: 07- ???   071- ???
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 07- ???   071- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}