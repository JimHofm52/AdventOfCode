package days;

import java.io.IOException;
import util.*;

public class Day10 {

    public static void update() throws IOException {
        int adpIn[] = ReadInput.GetInputInt(10);//Get input in an array for 10(101)
        int len = adpIn.length;                 //Length of input array
        SortAr.Sort(adpIn);                     //Sort adptors low to hi
        int diff[] = new int[len];              //Array for diff between adptors
        //---- Part 1 ------
        int cntDiff[] = new int[4];     //[0]=not 1-3, [1]=1, [2]=2, [3]=3
        cntDiff[1]++; cntDiff[3]++;     //Add 1 to [1] for outlet and 1 to [3] for device
        CountAdpDiff(adpIn, diff, cntDiff);   //Count the adapters and differentials
        System.out.println("\nDiffs\tother - " + cntDiff[0] +
                            "\t1 - " + cntDiff[1] + "\t2 - " + cntDiff[2] + "\t3 - " + cntDiff[3] +
                            "\tProduct of 1 & 3 is " + (cntDiff[1] * cntDiff[3]));
        //----- Part 2 ------
        long totAdpCombos = CalcTotCombos(diff);
        System.out.println("\nTotal combinations - " + totAdpCombos);
    }

    /**
     * Count jolts between adptors for 1, 2 & 3.  Also store jolt differences
     * 
     * @param adpIn
     * @param diff
     * @param cntDiff
     */
    private static void CountAdpDiff(int adpIn[], int diff[], int cntDiff[]){
        for(int i = 0; i < adpIn.length - 1; i++){
            int tmpDiff = adpIn[i + 1] - adpIn[i];
            diff[i] = tmpDiff;
            switch(tmpDiff){
                case 1:
                case 2:
                case 3:
                cntDiff[tmpDiff]++;
                break;
                default:
                cntDiff[0]++;
            }
        }
        diff[adpIn.length - 1] = 3;
    }    

    /**
     * Calc total adptor combinations.  Can't be more than 3 jolts diff.
     * If 3 in a row can't delete all.  So, not 8 (2^3) combos, just 7. 
     * 
     * @param diff
     * @return
     */
    private static long CalcTotCombos(int diff[]){
        long tot = 1;
        int tmpCnt = 0;
        boolean skipNext = false;

        for(int i = 0; i < diff.length; i++){
            switch(diff[i]){
                case 1:
                case 2:
                if(!skipNext) tmpCnt++;
                skipNext = false;
                break;
                case 3:
                if(tmpCnt >= 3){
                    tot *= (Math.pow(2, tmpCnt) - 1);
                }else{
                    tot *= Math.pow(2, tmpCnt);
                }
                tmpCnt = 0;
                skipNext = true;
                break;
                default:
            }
        }
        return tot;
    }    
}