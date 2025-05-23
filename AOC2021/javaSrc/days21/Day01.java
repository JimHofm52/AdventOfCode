package days21;

import java.io.IOException;
import util.ReadWriteFiles;

public class Day01 {
    private static int fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day01(){}

    public static void update() throws IOException {
        String fNum = "01";
        fileInfo = ReadWriteFiles.getInputInt(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1(fileInfo);
        question2(fileInfo);
    }

    /**
     * Question 1: Cnt increasing depth reading
     */
    private static void question1(int[] rdg) {
        int incCnt = 0;
        for(int i = 1; i < rdg.length; i++){
            if(rdg[i] > rdg[i - 1]) incCnt++;
        }
        //Track ,  Confirmed: 01-1298   011-7
        System.out.println("\nPart 1: Number Increasing depth readings: " + incCnt);
    }
    
    /**
     * Question 2: Cnt increasing depth readings using a 3 value sliding window.
     */
    private static void question2(int[] rdg) {
        int incCnt = 0;
        int smpl1 = 0;                          //1st sliding 3 sample
        int smpl2 = rdg[0] + rdg[1] + rdg[2];   //2nd sliding 3 sample
        for(int i = 3; i < rdg.length; i++){
            smpl1 = smpl2;
            smpl2 += (rdg[i] - rdg[i - 3]); 
            if(smpl2 > smpl1) incCnt++;
        }
        //Track ,  Confirmed: 01-1248   011-5
        System.out.println("\nPart 2: Number Increasing sliding depth readings: " + incCnt + "\n");
    }
}