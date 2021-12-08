package days;

import java.io.IOException;

import util.ReadInput;

public class Day06 {
    private static int[] fileInfo;
    private static int fiLen;
    private static int[] fishDay;

    /**
     * Constructor, not needed but used for standards.
     */
    public Day06(){
    }

    public static void update() throws IOException {
        String fNum = "06";
        fileInfo = ReadInput.getInputIntCS(fNum);   //Get input in an array for 1
        fiLen = fileInfo.length;                      //Length of input array

        fishDay = fileInfo.clone();
        question1();    //Confirmed: 06-380758   061-@18-26, @80-5934
        fishDay = fileInfo.clone();
        question2();    //Confirmed: 06-1710623015163      061-@256-26984457539
    }

    /**
     * Question 1: Count fish after 80 days.
     */
    private static void question1() {
        //Track ,  Confirmed: 06-380758   061-5934
        System.out.println("\nPart 1: Fish: " + evalFish(80));
    }
    
    /**
     * Question 2: Count fish after 256 days.
     */
    private static void question2() {
        //Track ,  Confirmed: 06-1710623015163      061-@256-26984457539
        System.out.println("\nPart 2: Fish: " + evalFish(256));
    }

    /**
     * Count fish for days passed.
     * 
     */
    private static long evalFish(int days) {
        long[] fishCycleCnt = new long[9];
        int fccLen = fishCycleCnt.length;
        long tmp = 0;
        for(int i = 0; i < fishDay.length; i++){
            fishCycleCnt[fishDay[i]]++;
        }
        for(int day = 0; day < days; day++){
            tmp = fishCycleCnt[0];
            for(int d = 1; d < fccLen; d++){
                fishCycleCnt[d - 1] = fishCycleCnt[d];
            }
            fishCycleCnt[6] += tmp;
            fishCycleCnt[8] = tmp;
        }

        tmp = 0;
        for(int d = 0; d < fccLen; d++) tmp += fishCycleCnt[d];
        return tmp;
    }
}