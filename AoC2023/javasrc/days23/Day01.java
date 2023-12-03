package days23;

import java.io.IOException;

import type.StrNum;
import util.ReadInput;

public class Day01 {
    private static String[] fileInfo;
    private static int len;
    /** Constructor, not needed but used for standards. */
    private Day01(){}

    public static void update() throws IOException {
        String fNum = "01";     //01-55130  02-54985
        // String fNum = "011";    //011-142   012-XX
        // String fNum = "012";    //011-xx    012-281
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        question1();
        question2();
    }

    /**
     * Question 1: Find the first & last digit in each string, ar1tfg3df=>12.
     * <p>Then add them.
     */
    private static void question1() {
        int[] nums = new int[len];      //first & last digits
        parceForNums(fileInfo, nums);   //returned in nums
        int sum = 0;
        for(int i = 0; i < nums.length; i++) sum += nums[i];    //add all the nums
        //Track ,  Confirmed: 011-142   01-55130
        System.out.println("\nPart 1: Sum 1st & last digits: " + sum);
    }
    
    /**
     * Question 2:  Find the first & last digit in each string, ar1tfg3df=>12.
     * <p>Then add them.
     * <p>But now string numbers count.  eightwo23=>8223=>83
     */
    private static void question2() {
        replStrDigits(fileInfo);    //replace str nums w/ a num eightwo=>8igh2wo
        int[] nums = new int[len];      //first & last digits
        parceForNums(fileInfo, nums);   //returned in nums
        int sum = 0;
        for(int i = 0; i < nums.length; i++) sum += nums[i];    //add all the nums
        //Track ,  Confirmed: 012-281, 01-54980 Lo / 54985
        System.out.println("\nPart 2: Sum 1st & last digits: " + sum);
    }

    /**
     * Parce for 1st & last digit in each line, qw1sdg2df=>12 or sk4fg5kk9=>49 or afs8afra=>88.
     * @param inStr array of strings
     * @param numRet array of numbers
     */
    private static void parceForNums(String[] inStr, int[] numRet){
        String s;
        int tmp;
        int[] digits = new int[2];
        for(int i = 0; i < inStr.length; i++){
            s = inStr[i];
            digits[0] = -1;
            for(int j = 0; j < s.length(); j++){
                tmp = Character.getNumericValue(s.charAt(j));
                if(tmp <= 9 && tmp >= 0){
                    if(digits[0] < 0) digits[0] = tmp;
                    digits[1] = tmp;
                }
            }
            numRet[i] = digits[0] * 10 + digits[1];
        }
    }

    /**
     * Replace string digits from left to right with single digit.
     * <p>eight=>8ight. eightwo=>8igh2wo
     * @param inStr
     */
    private static void replStrDigits(String[] inStr){
        for(int i = 0; i < inStr.length; i++){
            inStr[i] = findNxtRepl(inStr[i]);
        }
    }

    /**
     * Replace string digits from left to right with single digit.
     * <p>eight=>8ight. eightwo=>8igh2wo
     * @param str
     * @return
     */
    private static String findNxtRepl(String str){
        int loc;
        int idx;
        int tmp;
        boolean done = false;
        do{
            done = true;
            idx = 1000;
            loc = 1000;
            for(int i = 0; i < 10; i++){
                tmp = str.indexOf(StrNum.getStr(i));
                if(tmp > -1 && tmp < loc){
                    idx = i;
                    loc = tmp;
                    done = false;
                }
            }
            if(!done){
                str = str.substring(0, loc) + idx + str.substring(loc + 1);
            }
        }while(!done);
        return str;
    }

}