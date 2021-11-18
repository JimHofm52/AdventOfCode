package days;

import java.io.IOException;

import util.*;

public class Day15 {

    public static void update() throws IOException {
        int fileNum = 15;                   //File number of input
        int dataIn[] = ReadInput.GetInputIntCS(fileNum);// Get input in an array
        int len = dataIn.length;

        // ---- Part 1 ------
        int ans = FindSaidLst2(dataIn, 2020);
        System.out.println("\nPart 1 - " + ans);    // Confirmed - Part 1 - 441(151 - 436)
        //1,3,2=1  2,1,3=10  1,2,3=27  2,3,1=78  3,2,1=438  3,1,2=1836

        // ----- Part 2 ------
        ans = FindSaidLst2(dataIn, 30000000);
        System.out.println("\nPart 2 - " + ans);// Confirmed - Part 2 - 10613991(151 - 175594)
        //1,3,2=2578  2,1,3=3544142  1,2,3=261214  2,3,1=6895259  3,2,1=18  3,1,2=362
    }

    // ----------------------------------- Parce Input ----------------------------------
    // None needed

    // ----------------------------------- Part 1 & 2 ----------------------------------

    private static int FindSaidLst2(int initSaid[], int mxSaidCnt){
        //151- 0,1,2,3,    4,    5,    6,7,    8,9,   10,11,    12,     13,     14,    15,...
        //151- 0,3,6,0,    3,    3,    1,0,    4,0,    2, 0,     2,      2,      1,     8,...
        //151- 0,3,6,0,(3-0),(4-1),(5-4),0,(7-3),0,(9-7), 0,(11-9),(12-10),(13-12),(14-6),...
        //152- 0,1,2,3,4,5,     6,7,     8,     9,10,     11,     12,      13,     14,15,...
        //152- 2,1,3,0,0,1,(5-1)4,0,(7-4)3,(8-2)6, 0,(10-7)3,(11-8)3,(13-11)1,(13-5)8, 0,...

        int prvSaid = initSaid[initSaid.length - 1];  //prv said
        int lstSaid = 0;                    //Last said
        int saidCnt = initSaid.length;      //count of nums said.  151, already 3 said
        int saidNdx[] = new int[mxSaidCnt]; //last cnt said, index is the num said

        InitializeSaidNdx2(initSaid, saidNdx);

        do{
            if(saidNdx[lstSaid] < 0){        //Not said before
                saidNdx[lstSaid] = saidCnt;  //Save cnt when said
                lstSaid = 0;                    //Say 0 now
            }else{              //Said before
                                //store or update lastSaid with (saidCnt - fndNdx) at index(lstSaid)
                prvSaid = lstSaid;
                lstSaid = saidCnt - saidNdx[lstSaid];    //Say diff since last said
                saidNdx[prvSaid] = saidCnt;              //Save cnt when said
            }
            saidCnt++;
        }while(saidCnt < mxSaidCnt - 1);
        return lstSaid;
    }

    private static void InitializeSaidNdx2(int initSaid[], int saidNdx[]){
        for(int i = 0; i < saidNdx.length; i++){    //Initialize to -1
            saidNdx[i] = -1;
        }
        for(int i = 0; i < initSaid.length; i++){   //update with initial said
            saidNdx[initSaid[i]] = i;
        }
    }
}