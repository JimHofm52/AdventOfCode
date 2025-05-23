package days23;

import java.io.IOException;

import type.BoatGame;
import util.ReadWriteFiles;

public class Day06 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day06(){}

    public static void update() throws IOException {
        String fNum = "06";  int cheat = 4; //Part1- 131376   Part2- ???  4134594 Lo
        // String fNum = "061";  int cheat = 3; //Part1- 288 (4 * 8 * 9)   Part2- 71503
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        BoatGame boatInfo = new BoatGame(fileInfo, cheat);

        question1(boatInfo);    //Confirmed: 06- 131376   061- 288 (4 * 8 * 9)
        question2(boatInfo);    //Confirmed: 06- 34123437 4134594 Lo  061- 71503
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1(BoatGame bi) {
        //Track ,  Confirmed: 06- 131376   061- 288 (4 * 8 * 9), 18768 Lo,
        System.out.println("\nPart 1: ???: " + bi.winProd);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2(BoatGame bi) {
        //Track ,  Confirmed: 06-34123437  41334594 Lo, 061- 71503
        System.out.println("\nPart 2: ???: " + bi.winProd2);
    }
}