package days24;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day08 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started May 25, 2025,  Finished on May ?? 2025.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day08(){}

    public static void update() throws IOException {
        String fNum = "081";//Part1- ???   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 08- ???   081- 14
        question2();    //Confirmed: 08- ???   081- ???
    }

    /**
     * Question 1: Calculate the impact of the signal. 
     * How many unique locations within the bounds of the map contain an antinode?
     */
    private static void question1() {
        //Track ,  Confirmed: 08- ???   081- 14
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 08- ???   081- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}