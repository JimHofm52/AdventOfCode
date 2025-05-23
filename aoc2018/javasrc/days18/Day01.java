package days18;

import java.io.IOException;
import util.AryUtil;
import util.ReadWriteFiles;

public class Day01 {
    private static int fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day01(){}

    public static void update() throws IOException {
        String fNum = "01";//Part1- 430   Part2- 462
        fileInfo = ReadWriteFiles.getInputInt(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 01- 430
        question2();    //Confirmed: 01- 462
    }

    /**
     * Question 1: Starting with a frequency of zero, 
     * what is the resulting frequency after all of the 
     * changes in frequency have been applied?
     */
    private static void question1() {
        int totFreq = 0;
        for(int i : fileInfo){
            totFreq += i;
        }
        //Track ,  Confirmed: 01- 430
        System.out.println("\nPart 1: Total frequency: " + totFreq);
    }
    
    /**
     * Question 2: What is the first frequency your device reaches twice?
     */
    private static void question2() {
        Integer secondFreq = findRepeat();
        if(findRepeat() != null){
            //Track ,  Confirmed: 02- 462 after 144 loops!   nope -60727
            System.out.println("\nPart 2: Freq that repeated: " + secondFreq);
        }else{
            //Error - no answer!?
            System.out.println("\nPart 2: ERROR - Freq that repeated was not found");
        }
    }

    private static Integer findRepeat(){
        int[] sumFreqAry = {0};
        int sumFreq = 0;
        int safeCntr = 0;
        do{
            for(int val : fileInfo){
                sumFreq += val;
                if(chkDup(sumFreq, sumFreqAry)){
                    return sumFreq;
                }else{
                    //add to list
                    sumFreqAry = AryUtil.appendInt(sumFreqAry, sumFreq);
                }
            }
            safeCntr++;
        }while (safeCntr < 200);
        return null;
    }

    private static boolean chkDup(int sumFrq, int[] sumLst){
        for(int sum : sumLst){
            if(sumFrq == sum) return true;
        }

        return false;
    }
}