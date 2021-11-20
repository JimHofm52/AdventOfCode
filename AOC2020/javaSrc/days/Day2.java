package days;

import java.io.IOException;
import util.ReadInput;

public class Day2 {
    private static String fileInfo[];

    /**
     * Constructor, not needed but used for standards.
     */
    private Day2(){
    }

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputStr(2);    //Get input in an array for 5
        System.out.println();
        question1();                            //Puzzle answer was 655.
        question2();                            //Puzzle answer was 673.
    }

    /**
     * Find the cnt of PWs where the char cnt is <= min num >= max num.
     * Ex. 1-3 a: abcde  - must contain 'a' at least 1 time and at most 3 times.
     */
    private static void question1(){
        int cntOKPWs = 0;
        for(String strIn : fileInfo){
            if(parceInput(1, strIn)) cntOKPWs++;
        }
        displayInfo(1, cntOKPWs);
    }

    /**
     * Find the cnt of PWs where the char cnt is <= min num >= max num.
     * Ex. 1-3 a: abcde  - must contain 'a' at least 1 time and at most 3 times.
     */
    private static void question2(){
        int cntOKPWs = 0;
        for(String strIn : fileInfo){
            if(parceInput(2, strIn)) cntOKPWs++;
        }
        displayInfo(2, cntOKPWs);
    }

    /**
     * Break input line into minNum, maxNum, char & str
     * @return 
     */
    private static boolean parceInput(int ruleNum, String str){
        int dashIdx, colonIdx;
        int minNum = 0, maxNum = 0;
        char ch;
        String pw;

        dashIdx = str.indexOf('-');
        colonIdx = str.indexOf(':');
        minNum = Integer.parseInt(str.substring(0, dashIdx));
        maxNum = Integer.parseInt(str.substring(dashIdx + 1, colonIdx - 2));
        ch = str.charAt(colonIdx - 1);
        pw = str.substring(colonIdx + 2);
        if(ruleNum == 1){
            return chkRule1(minNum, maxNum, ch, pw);
        }else{
            return chkRule2(minNum, maxNum, ch, pw);
        }
    }

    /**
     * @return Is ch count GTE num1 & LTE num2.
     */
    private static boolean chkRule1(int num1, int num2, char ch, String pw){
        int chCnt = 0;
        for( int i = 0; i < pw.length(); i++){
            if(pw.charAt(i) == ch) chCnt++;
        }
        return ((chCnt >= num1) && (chCnt <= num2));
    }

    /**
     * @return Is ch occur in position but not the other.
     */
    private static boolean chkRule2(int num1, int num2, char ch, String pw){
        int chCnt = 0;
        if(pw.charAt(num1 - 1) == ch) chCnt++;
        if(pw.charAt(num2 - 1) == ch) chCnt++;
        return (chCnt == 1);
    }

   /**************************************************************************
     * Print the Answsers
     */
    private static void displayInfo(int num, int cnt){
        System.out.println("Rules " + num + "\tValue - " + cnt);
    }
}