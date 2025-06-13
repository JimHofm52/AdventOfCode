package days24;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day12 {
    private static String[] fileInfo;
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started June 14, 2025,  Finished on June XX, 2025.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day12(){}

    public static void update() throws IOException {
        String fNum = "12";//Part1- ???   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 12- ???   121- ???
        question2();    //Confirmed: 12- ???   121- ???
    }

    /**
     * Question 1: What is the total price of fencing all regions on your map?
     * Example 121: price = area * perimeter
     * R plants with price 12 * 18 = 216.
     * I plants with price 4 * 8 = 32.
     * C plants with price 14 * 28 = 392.
     * F plants with price 10 * 18 = 180.
     * V plants with price 13 * 20 = 260.
     * J plants with price 11 * 20 = 220.
     * C plants with price 1 * 4 = 4.
     * E plants with price 13 * 18 = 234.
     * I plants with price 14 * 22 = 308.
     * M plants with price 5 * 12 = 60.
     * S plants with price 3 * 8 = 24.
     * So, it has a total price of 1930.
     */
    private static void question1() {
        //Track ,  Confirmed: 12- ???   121- ???
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 12- ???   121- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}