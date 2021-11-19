package days;

import java.io.DataInput;
import java.io.IOException;

import util.*;

public class Day17{

    public static void update() throws IOException {
        int fileNum = 171;                   //File number of input
        String dataIn[] = ReadInput.getInputStr(fileNum);// Get input in an array
        int len = dataIn.length;

        //Initial data is a square 1 layer deep. 171 is 3x3x1, 17 is 8x8x1, xyz
        //Each cycle expands the evaluation space by 1 in all dimensions.
        //So after 6 cycles the eval space is init + (2 * (6 + 1)) = init + 14
        //IE.  3x3x1 => 17x17x15  or  8x8x1 => 22x22x15  Hmmm, center on even sq?

        int[][] zyx = parce_3D(dataIn);    //Declare eval space and initalize at center with data

        // ---- Part 1 ------
        zyx = rptEval(zyx, 4);  //Repeat evaluation part 1, 6 times
        int ans = cntAllCubes(zyx);
        long a = 0;
        System.out.println("\nPart 1 - " + ans);    // Confirmed - Part 1 - ??(171 - 112)

        // ----- Part 2 ------
        // ans = MatchRules(nbTkt, myTkt);     //Find which col matches rules, apply Departure in my tkt
        // System.out.println("\nPart 2 - " + ans);// Confirmed - Part 2 - ??(171 - 848)
    }

    // ----------------------------------- Parce Input ----------------------------------
    private static int[][] parce_3D(String[] dataIn){
        int len = dataIn.length;
        int[][] zyxVal = new int[1][len]; //Declare space        //Initialize dataIn in the center of the eval space
        String str;
        int xBit = 1 << (len - 1);
        for(int i = 0; i < len; i++){
            str = dataIn[i];
            for(int j = 0; j < len; j++){
                if(str.charAt(j) == '#'){
                    zyxVal[0][i] |= xBit >> j;
                }
            }
        }
        return zyxVal;
    }

    // ----------------------------------- Part 1 ----------------------------------

    private static int[][] rptEval(int[][] dataIn, int rpt){
        int[][] data2;
        data2 = AryUtil.CopyInt2By(dataIn);
        //Evaluate rules each cycle
        for(int i = 0; i < rpt; i++){
            data2 = expBy1Copy(data2);
            data2 = evalCubes(data2);
            int a = 0;
        }

        return data2;
    }

    private static int[][] expBy1Copy( int[][] from){
        int[][] data2 = new int[from.length + 1][from[0].length + 2];
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[0].length; j++){
                data2[i][j + 1] = from[i][j] << 1;
            }
        }
        return data2;
    }

    private static int[][] evalCubes( int[][] from){
        int cubeCnt = 0;
        int[][] data2 = AryUtil.CopyInt2By(from);
        for(int z = 0; z < from.length; z++){
            for(int y = 0; y < from[0].length; y++){
                for(int x = 0; x < from[0].length; x++){
                    cubeCnt = cntCubes(x, y, z, from);
                    if((from[z][y] & (1 << x)) > 0){
                        cubeCnt--;      //Don't eval itself.
                        if(cubeCnt != 2 && cubeCnt != 3) data2[z][y] &= ~(1 << x);
                    }else{
                        if(cubeCnt == 3) data2[z][y] |= 1 << x;
                    }
                    int a = 0;
                }
            }
        }
        return data2;
    }

    private static int cntCubes(int x, int y, int z, int[][] from){
        int bitCnt = 0;
        int totCnt = 0;
        int bits;
        int xMsk = x > 0 ?  7 << (x - 1) : 3;
        int yLen = y < from[0].length - 1 ? y + 2 : from[0].length;
        int zLen = z < from.length - 1 ? z + 2 : from.length;

        for(int i = z > 0 ? z-1 : 0; i < zLen; i++){
            for(int j = y > 0 ? y-1 : 0; j < yLen; j++){
                bits = from[i][j] & xMsk; //Masked test bits for this YZ
                // bits = bits >> x-1;   //Speed testing by shifting out already masked bits.
                do{                 //Count bits for this YZ
                    if((bits & 1) > 0) bitCnt++; 
                    bits = bits >> 1;                   
                }while(bits > 0);
            }
            totCnt += (z == 0 && i > 0)? bitCnt * 2 : bitCnt;
            bitCnt = 0;
        }
        System.out.println("Total Cnt for [" + x + ", " + y + ", " + z + "] = " + totCnt );
        return totCnt;
    }

    private static int cntAllCubes(int[][] zyx){
        int totCubes = 0;
        int bitCnt;
        int zLen = zyx.length;
        int yLen = zyx[0].length;
        int bits;
        for(int z = 0; z < zLen; z++){
            for(int y =0; y < yLen; y++){
                bits = zyx[z][y];
                bitCnt = 0;
                do{                 //Count bits for this YZ
                    if((bits & 1) > 0) bitCnt++; 
                    bits = bits >> 1;                   
                }while(bits > 0);
                totCubes += (z > 0 ? bitCnt * 2 : bitCnt);
            }
        }
        return totCubes;
    }
    // ----------------------------------- Part 2 ----------------------------------
}