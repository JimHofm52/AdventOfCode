package days19;

import java.io.IOException;
import util.ReadInput;
import intcomputer.IntCode;

public class Day04 {
    private static int[] fileInfo;   //Define array for input type data
    private static int len;             //Length of data

    /**Constructor, not needed but used for standards. */
    private Day04(){}

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputInt("04");   //Get input in an array for 4
        len = fileInfo.length;                    //Length of input array

        question1(fileInfo[0], fileInfo[1]);     //Confirmed: 04-931
        question2(fileInfo[0], fileInfo[1]);     //Confirmed: 04-609
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     * <p>It is a six-digit number.
     * <p>The value is within the range given in your puzzle input.
     * <p>Two adjacent digits are the same (like 22 in 122345).
     * <p>Going from left to right, the digits never decrease;
     * <p>they only ever increase or stay the same (like 111123 or 135679).
     * <p>
     * <p>Part 1 PW is between 272091-815432
     * <p>Implied rule if the next digit is smaller, all others become the same.
     * <p>SOooo, minPW is 277777 and maxPW is 799999
     * <p>277777=>277778, 277779, 277780=>277788, 277789, 277790=>277799, 277800=>277888,
     * 277889, 277890=>277899 ...
     */
    private static void question1(int loPW, int hiPW) {
        int pwOKCnt = evalPW(1, loPW, hiPW);

        //Track ,  Confirmed: 04-931
        System.out.println("\nPart 1: Number of possible PWs: " + pwOKCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     * same criteria as part 1 EXCEPT:
     * Two adjacent digits are the same (like 22 in 122345).
     * is now only 2 adjacent digits. 112233-OK, 123444-NO, 111122-OK.
     */
    private static void question2(int loPW, int hiPW) {
        int pwOKCnt = evalPW(2, loPW, hiPW);

        //Track ,  Confirmed: 04-609
        System.out.println("\nPart 2: Number of possible PWs: " + pwOKCnt);
    }
    
    /**
     * Evaluates all the rules.
     * @param part 1 or 2, evaluate duplicate digits differently
     * @param loPW low PW
     * @param hiPW hi PW
     * @return count of PW's to match rules.
     */
    private static int evalPW(int part, int loPW, int hiPW) {
        boolean pwOK;
        int pwOKCnt = 0;
        int[] digit = new int[6];
        int tmp = loPW;
        //Break PW into individual digits.  272091=>[0]=1, [1]=9, ... [5]=2
        for(int i = 0; i < digit.length; i++){
            digit[i] = tmp % 10;
            tmp = tmp / 10;
        }
        do{
            // System.out.print("Eval: " + pwBack(digit) + " - ");
            pwOK = chkDecr(digit);  //Chk if digits don't decrease.  Update if they do.
            if(pwOK){
                pwOK = chkDup(part, digit); //Chk if (1) .GT. 2: (2).EQ. 2
                if(pwOK) pwOKCnt++;         //If rules matched increment cnt.
                incrPW(digit);              //Chk next PW
            }
            // System.out.println(pwOK + "\tCnt: " + pwOKCnt);

        // }while(digit[5] < 8);   //.GT. 799999   Cheat
        }while(pwBack(digit) < hiPW);   //.GT. 815432

        return pwOKCnt;
    }

    /**
     * Check to see if all digits don't decrease msb (left) to lsb (right)
     * <p>If it does then all other digits MUST be equal to the present digit.
     * <p>272091 the 2nd 2 is .LT. 7 so other digits MUST be 7, 277777.
     * @param arPW Array of PW digits [0]=LSB
     * @return true if no digit is higher than the previous digit else false.
     */
    private static boolean chkDecr(int[] arPW){
        boolean pwOK = true;
        int len = arPW.length;
        int i;
        for(i = len - 1; i > 0; i--){
            if( arPW[i] > arPW[i - 1]){
                pwOK = false;
                for(; i > 0; i--){
                    arPW[i - 1] = arPW[i];
                }
            }
        }
        return pwOK;
    }

    /**
     * Check there is at least 1 double digit, duplicate.
     * <p>This was modded to handle part 2.
     * <p>Part 1 chks for .GTE. 2
     * <p>Part 2 chks for .EQ. 2
     * @param arPW Array of PW digits [0]=LSB
     * @return true if at least 1 duplicate found.
     */
    private static boolean chkDup(int part, int[] arPW){
        int[] digitDup = new int[6];
        int dupIdx = 0;
        int len = arPW.length;
        for(int i = len - 1; i > 0; i--){   //Look for repeats, multiples
            if(arPW[i] == arPW[i - 1]) {
                digitDup[dupIdx]++;
            }else{
                dupIdx++;
            }
        }

        for(int dd : digitDup){
            dd++;   //digit itself + match (dd)
            if(dd >= 2 && part == 1) return true;
            if(dd == 2 && part != 1) return true;
        }
        return false;
    }

    /**
     * Check there is at least 1 double digit, duplicate.
     * @param arPW Array of PW digits [0]=LSB
     * @return true if at least 1 duplicate found.
     */
    private static boolean chkDupOld(int[] arPW){
        boolean pwOK = false;
        int len = arPW.length;
        for(int i = len - 1; i > 0; i--){
            pwOK |= arPW[i] == arPW[i - 1];
        }
        return pwOK;
    }

    /**
     * Increment the array with carry.  Returned in passed array.
     * @param arPW Array of PW digits [0]=LSB
     */
    private static void incrPW(int[] arPW){
        arPW[0] = (arPW[0] + 1) % 10;
        for(int i = 1; i < arPW.length; i++){
            if(arPW[i - 1] == 0) arPW[i] = (arPW[i] + 1) % 10;
            if(arPW[i] < 0) return;
        }
    }

    /**
     * Reconsrtuct the PW array as a decimal value
     * @param arPW Array of PW digits [0]=LSB
     * @return int of the reconstructed PW array
     */
    private static int pwBack(int[] arPW){
        int pw = 0;
        for(int i = 0; i < arPW.length; i++){
            pw += arPW[i] * Math.pow(10,i); 
        }
        return pw;
    }
}