package days19;

import java.io.IOException;

import type.Amp;
import util.ReadInput;
import util.IntCode;

public class Day07{
    private static int fileInfo[];
    private static int len;
    private static Amp amp[] = new Amp[5];
    // private static int[] phs = {4,3,2,1,0}; //Hmmm, need to reorder 2^5 times?
    // private static int[] phs = {0,1,2,3,4}; //for 072
    private static int[] phs = {1,0,4,3,2}; //for 073

    /**Constructor, not needed but used for standards. */
    public Day07(){}

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputIntCS("073");   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        for(int i = 0; i < 5; i++){
            amp[i] = new Amp(fileInfo, phs[i], 0);
        }

        question1();
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        int maxPwr = 0;
        int phsing = 0;
        int num = 1234;
        int tmp = 0;
        do{
            phs = findNextNum(num);
            tmp = calcPwr(phs);
            if(tmp > maxPwr){ 
                maxPwr = tmp;
                phsing = phs[0]*10000+phs[1]*1000+phs[2]*100+phs[3]*10+phs[4];
                System.out.println("Phs: " + phsing + " PWr: " + maxPwr);
            }
        }while(num < 43210);

        int a = 0;
        //Track ,  Confirmed: 071A:43210=>43210 // 071B:01234=>54321 // 071C: 10432=>65210
        System.out.println("\nPart 1: Phasing: " + phsing + " produces max power of " + maxPwr);
    }

    private static int calcPwr(int[] phs){
        int pwr = 0;
        for(int i = 0; i < amp.length; i++){
            amp[i].setPwr(pwr);
            pwr = amp[i].runAmp();
        }
        return pwr;
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 04-609
        // System.out.println("\nPart 2: Number of possible PWs: " + pwOKCnt);
    }
    
    private static int[] findNextNum(int num){
        int[] digits = new int[5];
        do{
            getDigits(num, digits);
            num++;
        }while(!chkDigitsUniq(digits));
        return digits;
    }

    private static void getDigits(int num, int[] digits){
        for(int i = 0; i < digits.length; i++ ){
            digits[i] = num % 10;
            num = num / 10;
        }
    }

    private static boolean chkDigitsUniq(int[] digits){
        for(int i = 0; i < digits.length; i++ ){
            for(int j = i + 1; j < digits.length; j++){
                if(digits[i] == digits[j]) return false;
            }
        }
        return true;
    }

}