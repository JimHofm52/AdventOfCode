package days;

import java.io.IOException;
import util.ReadInput;

public class Day12 {
    private static String fileInfo[];
    private static int len;

    /**
     * Constructor, not needed but used for standards.
     */
    public Day12(){
    }

    public static void update() throws IOException {
        String fNum = "121";
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 12-???   121-???
        question2();    //Confirmed: 12-???   121-???
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        //Track ,  Confirmed: 04-???   041-???
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 04-???   041-???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }
}