package days22;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import type.T_BigMonkey;
import util.ReadInput;

public class Day11Big {
    final static long execTm = System.currentTimeMillis();
    private static String fileInfo[];
    private static int len;
    private static T_BigMonkey[] mnky;
    private static int mlen = 0;

    /** Constructor, not needed but used for standards. */
    private Day11Big(){}

    public static void update() throws IOException {
        // String fNum = "11"; //Part1- 54054   Part2- ???
        String fNum = "111";//Part1- 10605   Part2- 2713310158
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        for(String s : fileInfo) if(s.contains("Monkey")) mlen++;
        mnky = new T_BigMonkey[mlen];
        for(int i = 0; i < mlen; i++) mnky[i] = new T_BigMonkey();
        parceInput(3);

        question1();    //Confirmed: 11- 54054   111- 10605
        parceInput(1);
        question2();    //Confirmed: 11- ???   111- 2713310158
    }

    /**
     * Question 1: What is the level of monkey business 
     * after 20 rounds of stuff-slinging simian shenanigans?
     */
    private static void question1() {
        long iCntr = 0;
        long tmpCntr = 0;
        long[] iCnt = {0, 0};
        for(int loop = 0; loop < 20; loop++){
            for(int m = 0; m < mlen; m++){
                throwItems(mnky[m].evalMnky());
            }
        }

        for(int m = 0; m < mlen; m++){
            iCntr = mnky[m].getEvalCntr();
            System.out.println("Monkey " + m + " inspected items " + iCntr + " times.");
            if(iCntr > iCnt[0]){
                tmpCntr = iCnt[0];
                iCnt[0] = iCntr;
                iCntr = tmpCntr;
            }
            if(iCntr > iCnt[1]) iCnt[1] = iCntr;
        }

        iCntr = iCnt[0] * iCnt[1];
        //Track ,  Confirmed: 11- 54054   111- 10605
        System.out.println("\nPart 1: Exec Tm: " + ((System.currentTimeMillis() - execTm)/1000.0));
        System.out.println("\nPart 1: The top 2 product: " + iCnt[0] * iCnt[1]);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        long evalCntr = 0L;
        long tmpCntr = 0L;
        long[] evalCnt = {0L, 0L};
        for(int loop = 0; loop < 10000; loop++){
            if(loop == 1000){
                int z = 0;
            }
            for(int m = 0; m < mlen; m++){
                throwItems(mnky[m].evalMnky());
            }
            System.out.println("\nLoop:" + loop + " time: " + ((System.currentTimeMillis() - execTm)/1000.0));
        }

        for(int m = 0; m < mlen; m++){
            evalCntr = mnky[m].getEvalCntr();
            System.out.println("Monkey " + m + " inspected items " + Long.toUnsignedString(evalCntr) + " times.");

            if(Long.compareUnsigned(evalCntr, evalCnt[0]) == 1){
                tmpCntr = evalCnt[0];
                evalCnt[0] = evalCntr;
                evalCntr = tmpCntr;
            }
            if(evalCntr > evalCnt[1]) evalCnt[1] = evalCntr;
        }

        evalCntr = evalCnt[0] * evalCnt[1];
        //Track ,  Confirmed: 11- ???   111- 52166[0] * 52013[3] = 2713310158
        System.out.println("\nPart 2: Exec Tm: " + ((System.currentTimeMillis() - execTm)/1000.0));
        System.out.println("\nPart 2: Eval : " + Long.toUnsignedString(evalCnt[0]) + "\t" + Long.toUnsignedString(evalCnt[1]));
        System.out.println("\nPart 2: The top 2 product: " + Long.toUnsignedString(evalCntr));
    }

    private static void throwItems(BigInteger[][] itemsToThrow){
        if(itemsToThrow != null){
            for(int t = 0; t < itemsToThrow.length; t++){
                mnky[itemsToThrow[t][0].intValue()].addItem(itemsToThrow[t][1]);
            }
        }
    }

    /**
     * Parce initial data and save to T_Monkey ararry, mnky[].
     */
    private static void parceInput(int wryReduc){
        String[] line = new String[6];  //6 lines + 1 blank
        int[] tmpItem;
        int tmp;
        for(int i = 0; i < mlen; i++){  //for the number of monkeys
            for(int l = 0; l < 6; l++) line[l] = fileInfo[i * 7 + l];   //readin the 6 lines at a time, skip 7th
            if(mnky[i].getMnkyID() == parceLine0(line[0])){ //Check we are reading the right monkey, line 0
                mnky[i].clearItems();                       //Clear Items.  Needed for part 2
                mnky[i].resetEvalCntr();                    //Reset the evaluation conter for part 2
                tmpItem = parceLine1(line[1]);              //Read items from line 1
                for(int s : tmpItem) mnky[i].addItem(BigInteger.valueOf(s));    //then add items to monkey
                tmp = parceLine2(line[2]);                  //Read math operation and worry parm
                mnky[i].setMathOp(tmp % 10);                //set the math operation. 0=+, 1=*, 2=^2
                mnky[i].setWryParm(tmp / 10);               //save the worry parm
                mnky[i].setTestParm(parceLine3(line[3]));   //Get and save test parm
                mnky[i].setMnkyTrue(parceLine4(line[4]));   //Get and save maonkey to throw to if test is true
                mnky[i].setMnkyFalse(parceLine5(line[5]));  //Get and save maonkey to throw to if test is false
                mnky[i].setWryReducParm(wryReduc);          //Set the worry reduction parm
            }else{
                System.out.println("Parceing for wrong monkey: " + i + ", " + line[i]);
            }
        }
    }


    private static int parceLine0(String line){
        if(!line.contains("Monkey")){
            System.out.println("Not Monkey ID line: " + line);
            return -1;
        }
        int sp = line.indexOf(" ");
        return Integer.parseInt(line.substring(sp + 1, line.length() - 1));
    }

    private static int[] parceLine1(String line){
        if(!line.contains("Starting items:")){
            System.out.println("Not Starting Items: line: " + line);
            return new int[0];
        }
        if(line.length() < 17) return new int[0];   //No items

        int beg = 18;
        int end = line.indexOf(",");
        int[] wry = new int[0];
        int wLen = 0;
        while(end > 0){
            wry = Arrays.copyOf(wry, wLen + 1);
            wry[wLen++] = Integer.parseInt(line.substring(beg, end));
            beg = end + 2;
            end = line.indexOf(",", beg);
        }
        wry = Arrays.copyOf(wry, wLen + 1);
        wry[wLen] = Integer.parseInt(line.substring(beg));

        return wry;
    }

    /**
     * Parce the 3rd line for math operation and worry parm. 
     * @param line Ex. Operation: new = old * 7
     * @return operation in LSD (mod 10) and worry parm in the other digits (divide by 10)
     */
    private static int parceLine2(String line){
        if(!line.contains("Operation:")){
            System.out.println("Not Operation line: " + line);
            return -1;
        }
        if(line.contains("old * old")) return 2;    //new = old * old

        int tmp = line.lastIndexOf(" ");
        tmp = Integer.parseInt(line.substring(++tmp));
        tmp *= 10;
        tmp += line.contains("+") ? 0 : 1;
        return tmp;
    }

    private static int parceLine3(String line){
        if(!line.contains("Test:")){
            System.out.println("Not Test line: " + line);
            return -1;
        }
        int sp = line.lastIndexOf(" ");
        return Integer.parseInt(line.substring(sp + 1));
    }

    private static int parceLine4(String line){
        if(!line.contains("If true:")){
            System.out.println("Not If true: line: " + line);
            return -1;
        }
        int sp = line.lastIndexOf(" ");
        return Integer.parseInt(line.substring(sp + 1));
    }

    private static int parceLine5(String line){
        if(!line.contains("If false:")){
            System.out.println("Not If false: line: " + line);
            return -1;
        }
        int sp = line.lastIndexOf(" ");
        return Integer.parseInt(line.substring(sp + 1));
    }

    private static int parceLine6(String line){
        return 0;
    }

}