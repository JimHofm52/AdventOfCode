package days15;

import java.io.IOException;

import type.Box;
import util.ReadInput;

public class Day02 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02"; //Part1- 1586300   Part2- 3737498
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        Box[] santaBox = new Box[len];
        for(int idx = 0; idx < len; idx++) santaBox[idx] = new Box(fileInfo[idx]);

        question1(santaBox);    //Confirmed: 02- 1586300  021- 58
        question2(santaBox);    //Confirmed: 02- 3737498   021- 34
    }

    /**
     * Question 1: How many total feet of ribbon should they order?
     */
    private static void question1(Box[] sBox) {
        int totalPaper = 0;
        for(Box sb : sBox) totalPaper += sb.getTotalArea();
        //Track ,  Confirmed: 02- 1586300   021- ???
        System.out.println("\nPart 1: Total square feet of wrapping paper: " + totalPaper);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2(Box[] sBox) {
        int totalRibbon = 0;
        for(Box sb : sBox) totalRibbon += sb.getMinPerim() + sb.getTotalVol();
        //Track ,  Confirmed: 02- 3737498   021- 34
        System.out.println("\nPart 2: Total length of ribbon: " + totalRibbon);
    }
}