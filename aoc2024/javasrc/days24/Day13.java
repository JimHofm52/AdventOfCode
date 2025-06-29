package days24;

import java.io.IOException;
import java.util.Arrays;

import type.ClawMachine;
import util.ReadWriteFiles;

public class Day13 {
    private static String[] fileInfo;
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started June 28, 2025,  Finished on June xx, 2025.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day13(){}

    public static void update() throws IOException {
        String fNum = "13";//Part1- 38714   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        ClawMachine[] clawMachs = parceInput(fileInfo);

        question1(clawMachs);    //Confirmed: 13- 38714   131- 480
        question2();    //Confirmed: 13- ???   131- ???
    }

    /**
     * Question 1: What is the fewest tokens you would have to spend to win all possible prizes?
     */
    private static void question1(ClawMachine[] clawMachs) {
        int totCost = 0;
        for(ClawMachine cm : clawMachs){ totCost += cm.getCost(); }
        //Track ,  Confirmed: 13- 38714   131- 480
        System.out.println("\nPart 1: Fewest tokens to win all possible prizes " + totCost);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 13- ???   131- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }

    private static ClawMachine[] parceInput(String[] input){
        ClawMachine[] tmpMach = new ClawMachine[0];
        String[] tmpInfo = new String[3];
        for (int i = 0; i < input.length; i += 4) {
            tmpInfo[0] = input[i];
            tmpInfo[1] = input[i + 1];
            tmpInfo[2] = input[i + 2];

            tmpMach = Arrays.copyOf(tmpMach, tmpMach.length + 1);
            tmpMach[tmpMach.length - 1] = new ClawMachine(tmpInfo);
        }
        return tmpMach;
    }
}