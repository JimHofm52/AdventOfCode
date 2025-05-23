package days24;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day05 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started Dec 5, 2024,  Finished on Dec 9, 2024.
     * <p>Took ~xx hour.  Runtime 0.221 S.
    */
    private Day05(){}

    public static void update() throws IOException {
        String fNum = "05";//Part1- 3608   Part2- 4922
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        int brk = 0;
        for(; brk < len; brk++) if(fileInfo[brk].isEmpty()) break;
        int[][] rules = new int[brk++][2];
        int[][] updates = new int[len - brk][];
        Boolean[] updatesOK = new Boolean[updates.length];
        parceInput(fileInfo, rules, updates);
        findValidUpd(rules, updates, updatesOK);
        question1(updates, updatesOK);    //Confirmed: 05- 3608   051- 143

        int badCnt = 0;
        for(Boolean i : updatesOK) if(i == false) badCnt++;
        int[][] updatesBad = new int[badCnt][];
        badCnt = 0;
        for(int i = 0; i < updates.length; i++){
            if(!updatesOK[i]) updatesBad[badCnt++] = updates[i];
        }
        fixBadUpdates(rules, updatesBad);
        updatesOK = null;
        updatesOK = new Boolean[updatesBad.length];
        findValidUpd(rules, updatesBad, updatesOK);

        question2(updatesBad, updatesOK);    //Confirmed: 05- 4922   051- 123
    }

    /**
     * Question 1: What do you get if you add up the 
     * middle page number from those correctly-ordered updates?
     */
    private static void question1(int[][] upd, Boolean[] updOK) {
        int sumCenter = 0;
        for(int i = 0; i < upd.length; i++){
            if(updOK[i]) sumCenter += upd[i][(upd[i].length) / 2];
        }

        //Track ,  Confirmed: 05- 3608   051- 143
        System.out.println("\nPart 1: Correctly-ordered updates, middle pg sum: " + sumCenter);
    }
    
    /**
     * Question 2: Find the updates which are not in the correct order. 
     * What do you get if you add up the middle page numbers after correctly 
     * ordering just those updates?
     */
    private static void question2(int[][] upd, Boolean[] updOK) {
        int sumCenter = 0;
        for(int i = 0; i < upd.length; i++){
            if(updOK[i]) sumCenter += upd[i][(upd[i].length) / 2];
        }

        //Track ,  Confirmed: 05- 4922   051- 123
        System.out.println("\nPart 2: Corrected-ordered updates, middle pg sum: " + sumCenter);
    }

    //----------------- Parce inlut into rules and instructions -----------
    /**
     * Parce input into Rules amd Ordered Updates. Seperated by a blank line.
     * @param inStr Array of the input strings
     * @param rIn Array to pass back the rules
     * @param inUpd Array to pass back the updates
     */
    private static void parceInput(String[] inStr, int[][] rIn, int[][] inUpd){
        int brk = rIn.length;
        int d;  //Index of deliminator '|'
        //parce rules
        for(int i = 0; i < brk; i++){
            d = inStr[i].indexOf('|');
            rIn[i][0] = Integer.parseInt(inStr[i].substring(0, d++));
            rIn[i][1] = Integer.parseInt(inStr[i].substring(d));
        }
        brk++;  //until break
        int[] num;
        //parce updates
        for(int i = brk; i < len; i++){
            inUpd[i - brk] = ReadWriteFiles.parceStrIntCS(inStr[i]);
        }
    }

    //---------------  Question 1  -------------------------

    /**
     * Find all valid updates.  Return each update status in updateOK
     * @param rules
     * @param upd
     * @param updOK Pass back each update status.
     */
    private static void findValidUpd(int[][] rules, int[][] upd, Boolean[] updOK){
        Boolean tmpBool;
        int[] idx = new int[2];

        for(int u = 0; u < upd.length; u++){
            for(int ru = 0; ru < rules.length; ru++ ){
                tmpBool = isRuleInUpd(rules[ru], upd[u], idx);
                if(updOK[u] == null) updOK[u] = tmpBool;
                if(tmpBool != null) updOK[u] &= tmpBool;
            }
            if(updOK[u] == null) updOK[u] = false;
        }
    }

    /**
     * 
     * @param rule
     * @param upd
     * @param idx pass back int of index where page num sppesr in update
     * @return null - 0 or 1 num only ignor, false - both appear, true - both appear in order 
     */
    private static Boolean isRuleInUpd(int[] rule, int[] upd, int[] idx){
        int a = -1;
        int b = -1;
        for(int i = 0; i < upd.length; i++){
            if(rule[0] == upd[i]) a = i;
            if(rule[1] == upd[i]) b = i;
        }
        idx[0] = a;
        idx[1] = b;
        if(a < 0 || b < 0) return null;
        return a < b;
    }

    //-----------------------  Part 2  ----------------------------

    /**
     * Fix all the updates using the rules
     * @param rules
     * @param upd
     */
    private static void fixBadUpdates(int[][] rules, int[][] upd){
        Boolean tmpBool;
        int[] tmpIdx = new int[2];
        int swap;
        int swapCnt;

        for(int u = 0; u < upd.length; u++){        //For each update
            do{
                swapCnt = 0;
                for(int ru = 0; ru < rules.length; ru++ ){
                    tmpBool = isRuleInUpd(rules[ru], upd[u], tmpIdx);
                    if(tmpBool != null){
                        if(tmpBool == false){   //Swap page nums
                            swap = upd[u][tmpIdx[0]];
                            upd[u][tmpIdx[0]] = upd[u][tmpIdx[1]];
                            upd[u][tmpIdx[1]] = swap;
                            swapCnt++;
                        }
                    }
                }
            }while(swapCnt > 0);    //Until no more swaps
        }
    }

}