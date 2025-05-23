package days20;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day3 {

    private static String fileInfo[];
    private static int len;

    /** Constructor, no action.  Standard only. */
    private Day3(){}

    public static void update() throws IOException {
        fileInfo = ReadWriteFiles.getInputStr(3);    //Get input in an array for Day3
        len = fileInfo.length;
        System.out.println();
        question1();                            //Puzzle answer was 252.
        question2();                            //Puzzle answer was 2608962048.
    }

    /**
     * Find how many trees (#) you hit following a fixed pattern, 3 right, 1 down.
     * The pattern of a row repeats to the right (modulo).  252.
     */
    private static void question1(){
        displayInfo(1, cntTreesHit(3, 1));
    }

    /**
     * Find how many trees (#) you hit following a the following patterns
     *<p>Right 1, down 1.
     *<p>Right 3, down 1. (This is the slope you already checked.)
     *<p>Right 5, down 1.
     *<p>Right 7, down 1.
     *<p>Right 1, down 2.
     *<p>then multiply all the answers.  2608962048 LONG!.
     */
    private static void question2(){
        long prodTreeHit = 1;
        prodTreeHit *= cntTreesHit(1, 1);   //57
        prodTreeHit *= cntTreesHit(3, 1);   //252
        prodTreeHit *= cntTreesHit(5, 1);   //64
        prodTreeHit *= cntTreesHit(7, 1);   //66
        prodTreeHit *= cntTreesHit(1, 2);   //43
        displayInfo(2, prodTreeHit);
    }

    /**
     * Find how many trees (#) you hit following a pattern passed.
     * The pattern of a row repeats to the right (modulo).
     */
    private static int cntTreesHit(int moveR, int moveDn){
        int width = fileInfo[0].length();
        int rw = moveDn;
        int col = moveR;
        char tree = '#';
        int treeCnt = 0;
        for(; rw < len; rw += moveDn){
            if(fileInfo[rw].charAt(col) == tree) treeCnt++;
            col += moveR;    col %= width;  //Calc move right on repeating pattern
        }

        return treeCnt;
    }

   /**************************************************************************
     * Print the Answsers
     */
    private static void displayInfo(int num, long cnt){
        System.out.println("Rules " + num + "\tValue - " + cnt);
    }
}