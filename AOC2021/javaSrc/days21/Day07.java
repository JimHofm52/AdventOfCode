package days21;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day07 {
    private static int fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day07(){}

    public static void update() throws IOException {
        String fNum = "07";
        fileInfo = ReadWriteFiles.getInputIntCS(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 07-@ lvl 352 336131   071-@ lvl 2 37
        question2();    //Confirmed: 07-92676646   071-168
    }

    /**
     * Question 1: Find the common level for the little crab submarines that uses
     * the minimum amount of fuel.
     */
    private static void question1() {
        int[] fuel = new int[fileInfo.length];
        for(int f = 0; f < len; f++){
            for(int s : fileInfo) fuel[f] += Math.abs(s - fileInfo[f]);
        }
        int minIdx = findMinIdxOf(fuel);
        //Track ,  Confirmed: 07-@lvl 352 336131  071-@lvl 2, 37 
        System.out.println("\nPart 1: At level " + minIdx + " min fuel is " + fuel[minIdx]);
    }
    
    /**
     * Question 2: Find minimun fuel to get all subs to a common level,
     * using the crab 1+ method. 3 diff = 1+2+3 = 6 fuel.
     */
    private static void question2() {
        int[] fuel = sumFuel();
        int minIdx = findMinIdxOf(fuel);
        //Track ,  Confirmed: 07-92676646   071-168
        System.out.println("\nPart 2: At level " + minIdx + " min fuel is " + fuel[minIdx]);
    }

    private static int[] sumFuel(){
        int diff = 0;
        int minIdx = findMinIdxOf(fileInfo);
        int maxIdx = findMaxIdxOf(fileInfo);
        int[] fuelSum = new int[fileInfo[maxIdx] - fileInfo[minIdx]];
        for(int f = fileInfo[minIdx]; f < fileInfo[maxIdx]; f++){
            for(int s : fileInfo) {
                diff = Math.abs(s - f);
                fuelSum[f] += (diff + 1.0) * (diff / 2.0);
            }
        }
        return fuelSum;
    }

    /**
     * Find the min value in the array.  Return the index of that value
     * @param ary
     * @return index of that value
     */
    private static int findMinIdxOf(int[] ary){
        int minIdx = 0;
        for(int i = 1; i < ary.length; i++){
            if(ary[i] < ary[minIdx]) minIdx = i;
        }
        return minIdx;
    }

    /**
     * Find the max value in the array.  Return the index of that value
     * @param ary
     * @return index of that value
     */
    private static int findMaxIdxOf(int[] ary){
        int maxIdx = 0;
        for(int i = 1; i < ary.length; i++){
            if(ary[i] > ary[maxIdx]) maxIdx = i;
        }
        return maxIdx;
    }
}