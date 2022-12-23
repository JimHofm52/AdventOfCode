package days22;

import java.io.IOException;
import java.security.AllPermission;
import java.util.Arrays;

import util.ReadInput;

public class Day09 {
    private static String fileInfo[];
    private static int len;
    private static int[][] tailPath = {{0,0}};

    /** Constructor, not needed but used for standards. */
    private Day09(){}

    public static void update() throws IOException {
        String fNum = "09"; //Part1- 6494   Part2- ???
        // String fNum = "091";//Part1- 13   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 09- 6494   091- 13
        question2();    //Confirmed: 09- ???   091- ???
    }

    /**
     * Question 1: Simulate your complete hypothetical series of motions. 
     * How many positions does the tail of the rope visit at least once?
     * Need to revisit. The counter below doesn't work.  It supposed to take the 
     * total moves then count down any duplicates.
     * Had to rewite updTail, add every tail move with evalTail that first checks
     * for existing duplicate and add ony IF none found the updTail.
     */
    private static void question1() {
        move();
        int tLen = tailPath.length;
        int cnt = tLen;
        for(int i = 0; i < tLen; i++){
            for(int j = i + 1; j < tLen; j++){
                if(tailPath[i][0] == tailPath[j][0] &&
                    tailPath[i][1] == tailPath[j][1]){
                    cnt--;
                    // System.out.println("Dup: " + i + " - " + tailPath[i][0] + ", " + tailPath[i][1] +
                    //  "\t: " + j + " - " + tailPath[j][0] + ", " + tailPath[j][1]);
                } 
            }
        }
        //Track 6817-6 hi, 5610 lo,  Confirmed: 09- 6494   091- 13
        System.out.println("\nPart 1: ???: " + cnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 09- ???   091- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }

    private static void move(){
        char dir;
        int dist;
        int[] headXY = {0, 0};
        int[] tailXY = {0, 0};

        for(int s = 0; s < len; s++){
            dir = fileInfo[s].charAt(0);
            dist = Integer.parseInt(fileInfo[s].substring(2));
            if(s >= 18){
                int b = 0;
            }
            switch(dir){
                case 'R':
                for(int i = 0; i < dist; i++){
                    headXY[0]++;
                    if(Math.abs(headXY[0] - tailXY[0]) > 1){
                        tailXY[0]++;
                        tailXY[1] = headXY[1];
                        evalTail(tailXY);
                    }
                }
                break;
                case 'L':
                for(int i = 0; i < dist; i++){
                    headXY[0]--;
                    if(Math.abs(headXY[0] - tailXY[0]) > 1){
                        tailXY[0]--;
                        tailXY[1] = headXY[1];
                        evalTail(tailXY);
                    }
                }
                break;
                case 'U':
                for(int i = 0; i < dist; i++){
                    headXY[1]++;
                    if(Math.abs(headXY[1] - tailXY[1]) > 1){
                        tailXY[1]++;
                        tailXY[0] = headXY[0];
                        evalTail(tailXY);
                    }
                }
                break;
                case 'D':
                for(int i = 0; i < dist; i++){
                    headXY[1]--;
                    if(Math.abs(headXY[1] - tailXY[1]) > 1){
                        tailXY[1]--;
                        tailXY[0] = headXY[0];
                        evalTail(tailXY);
                    }
                }
                break;
            }
        }
    }

    private static int allCnt = 0;
    private static int dupCnt = 0;
    private static void evalTail(int[] tailXY){
        allCnt++;
        boolean fnd = false;
        for(int[] tail : tailPath){
            if(tail[0] == tailXY[0] &&
                tail[1] == tailXY[1]){
                fnd = true;
                dupCnt++;
            } 
        }
        if(!fnd) updTail(tailXY);
    }

    private static void updTail(int[] tailXY){
        int tLen = tailPath.length;
        tailPath = Arrays.copyOf(tailPath, tLen + 1);
        tailPath[tLen] = new int[2];
        tailPath[tLen][0] = tailXY[0];
        tailPath[tLen][1] = tailXY[1];
    }
}