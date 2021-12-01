package days;

import java.io.IOException;

import util.ReadInput;

public class Day05 {
    private static int[] fileInfo;   //Define array for input type data
    private static int len;             //Length of data

    /**
     * Constructor, not needed but used for standards.
     */
    private Day05(){
    }

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputIntCS("05");   //Get input in an array for 4
        len = fileInfo.length;                    //Length of input array

        question1(fileInfo[0], fileInfo[1]);     //Confirmed: 04-931
        question2(fileInfo[0], fileInfo[1]);     //Confirmed: 04-609
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1(int loPW, int hiPW) {

        //Track ,  Confirmed: 04-931
        // System.out.println("\nPart 1: Number of possible PWs: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2(int loPW, int hiPW) {

        //Track ,  Confirmed: 04-609
        // System.out.println("\nPart 2: Number of possible PWs: " + pwOKCnt);
    }
    
}