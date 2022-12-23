package days22;

import java.io.IOException;
import java.util.Arrays;

import type.T_Knot;
import util.ReadInput;

public class Day09A {
    private static String fileInfo[];
    private static int len;
    private static int kLen;
    private static T_Knot[] knot;

    /** Constructor, not needed but used for standards. */
    private Day09A(){}

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
     */
    private static void question1() {
        kLen = 2;
        knot = new T_Knot[kLen];
        for(int k = 0; k < kLen; k++) knot[k] = new T_Knot();
        parceInput();
        int cnt = cntNoDups();
        //Track 6817-6 hi, 5610 lo,  Confirmed: 09- 6494   091- 13
        System.out.println("\nPart 1: ???: " + cnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        kLen = 10;
        knot = new T_Knot[kLen];
        for(int k = 0; k < kLen; k++) knot[k] = new T_Knot();
        parceInput();
        int cnt = cntNoDups();
        //Track ,  Confirmed: 09- ???   091- ???
        System.out.println("\nPart 2: ???: " + cnt);
    }

    private static void parceInput(){
        for(int s = 0; s < len; s++){
            char dir = fileInfo[s].charAt(0);
            int dist = Integer.parseInt(fileInfo[s].substring(2));
            int adj = (dir == 'R' || dir == 'U') ? 1 : -1;
            int ldIdx = (dir == 'R' || dir == 'L') ? 0 : 1;
            for(int d = 0; d < dist; d++){
                knot[0].posXY[ldIdx] += adj;
                for(int k = 1; k < kLen; k++){
                    int[] diff = calcDiff(k - 1, k);
                    if(Math.abs(diff[0]) > 1 || Math.abs(diff[1]) > 1){
                        moveLg(k, diff);
                        if(k == kLen - 1) updPath(k);
                    }else{
                        break;
                    }
                }
            }
        }
    }

    private static int[] calcDiff(int ldIdx, int lgIdx){
        int[] diff = {0, 0};
        diff[0] = knot[ldIdx].posXY[0] - knot[lgIdx].posXY[0];
        diff[1] = knot[ldIdx].posXY[1] - knot[lgIdx].posXY[1];
        return diff;
    }

    private static void moveLg(int lgIdx, int[] diff){
        if(diff[0] > 1){            //Right
            diff[0]--;
        }else if(diff[0] < -1){     //Left
            diff[0]++;
        }
        if(diff[1] > 1){      //Up
            diff[1]--;
        }else if(diff[1] < -1){     //Dn
            diff[1]++;
        }
        knot[lgIdx].posXY[0] += diff[0];
        knot[lgIdx].posXY[1] += diff[1];
    }

    private static void updPath(int idx){
        int pLen = knot[idx].pathXY.length;
        knot[idx].pathXY = Arrays.copyOf(knot[idx].pathXY, pLen + 1);
        knot[idx].pathXY[pLen] = new int[2];
        knot[idx].pathXY[pLen][0] = knot[idx].posXY[0];
        knot[idx].pathXY[pLen][1] = knot[idx].posXY[1];
    }

    private static int cntNoDups(){
        int pLen = knot[kLen - 1].pathXY.length;
        int cnt = pLen;
        for(int k1 = 0; k1 < pLen; k1++){
            for(int k2 = k1 + 1; k2 < pLen; k2++){
                if(knot[kLen - 1].pathXY[k1][0] == knot[kLen - 1].pathXY[k2][0] &&
                   knot[kLen - 1].pathXY[k1][1] == knot[kLen - 1].pathXY[k2][1]){
                    cnt--;
                    break;
                }
            }
        }
        return cnt;
    }
    
}