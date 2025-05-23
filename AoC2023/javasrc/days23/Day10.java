package days23;

import java.io.IOException;

import util.ReadWriteFiles;
import type.Pipe;

public class Day10 {
    private static String fileInfo[];
    private static int len;
    private static int[]locXY = new int[2]; //Upper left is [0, 0]

    /** Constructor, not needed but used for standards. */
    private Day10(){}

    public static void update() throws IOException {
        String fNum = "10"; //Part1- ???   Part2- ???
        // String fNum = "101";//Part1- ???   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        // testInput();
        locXY = findS();

        question1();    //Confirmed: 10- ???   101- ???
        question2();    //Confirmed: 10- ???   101- ???
    }

    /**
     * Question 1: Find the single giant loop starting at S. How many steps 
     * along the loop does it take to get from the starting position to the 
     * point farthest from the starting position?
     */
    private static void question1() {
        //Track ,  Confirmed: 10- ???   101- ???
        // System.out.println("\nPart 1: ???: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 10- ???   101- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }

    /**
     * The pipes are arranged in a two-dimensional grid of tiles:  Matrix [X, Y], [EW, HS]
     * To go North is -1, South +1. To go East +1, West -1.  If pipe is to the: right  left  above  below
     * | is a vertical pipe north and south if on top or bottom (go N | S).       n/a | n/a  | 0,-2 | 0,2
     * - is a horizontal pipe east and west if left or right (go E | W).          2,0 |-2,0  | n/a  | n/a
     * L is a 90-degree connecting north and east (go NW [left] | SE [below]).    n/a |-1,-1 | n/a  | 1,1
     * J is a 90-degree connecting north and west (go NE [right] | SW [below]).   1,-1| n/a  | n/a  |-1,1
     * 7 is a 90-degree connecting south and west (go NW [above] | SE [right]).   1,1 | n/a  |-1,-1 | n/a
     * F is a 90-degree connecting south and east (go NE [above] | SW [left]).    n/a |-1,1  | 1,-1 | n/a
     * . is ground; there is no pipe in this tile      (go no where).             n/a | n/a  | n/a  | n/a
     * S is the starting position of the animal; there is a pipe on this tile, 
     * but your sketch doesn't show what shape the pipe has.
     */
    private static void testInput(){
        int[] test = new int[2];
        int ns = 0;
        int ew = 0;
        char ch;
        for(int j = 0; j < Pipe.pType.length(); j++){
            ch = Pipe.pType.charAt(j);
            System.out.print(ch + "\t");
            for(int i = 0; i < 5; i++){
                test = Pipe.getPipeMove(ch, i);
                ns = test[0];
                ew = test[1];
                System.out.print(" [" + ns + ", " + ew + "]  ");
            }
            System.out.println("");
        }
    }

    /**
     * @return the starting location XY point
     */
    private static int[] findS(){
        int y = -1;
        while(fileInfo[++y].indexOf('S') == -1);
        int[] loc = {fileInfo[y].indexOf('S'), y};
        return loc;
    }

    private static int flwPath(int[] actXY){
        int cntMoves = 0;
        char ch = getCharAt(actXY);
        int dir = Pipe.getMovesToChk(ch);
        int[] tmpMv = new int[2];
        do{
            switch(dir){
                case 0: // .
                case 3: // |
                case 5: // F
                case 6: // L
                case 9: // 7
                case 10:// J
                case 12:// -
                case 15:// S
            }

        }while(cntMoves < 100);  //getPipeMove(fileInfo[actXY[1]].charAt(X)));
        
        return cntMoves;
    }

    private static char getCharAt(int[] atXY){
        return fileInfo[atXY[1]].charAt(atXY[0]);
    }
}