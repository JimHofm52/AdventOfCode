package days;

import java.io.IOException;
import util.ReadInput;

public class Day03 {
    private static String fileInfo[];
    private static int len;

    /**
     * Constructor, not needed but used for standards.
     */
    public Day03(){
    }

    public static void update() throws IOException {
        String fNum = "031";
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 3
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 03-???   031-???
        question2();    //Confirmed: 03-???   031-???
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        //Track ,  Confirmed: 03-???   031-???
        // System.out.println("\nPart 1: Number of possible PWs: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 03-???   031-???
        // System.out.println("\nPart 2: Number of possible PWs: " + pwOKCnt);
    }
}