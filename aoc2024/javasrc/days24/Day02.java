package days24;

import java.io.IOException;

import util.ReadInput;

public class Day02 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards.
     * <p>Started Dec 2, 2024,  Finished on Dec 2, 2024.
     * <p>Took too long.  Runtime 0.207 S.
     */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02";//Part1- 279   Part2- 343
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 02- 279   021- 2
        question2();    //Confirmed: 02- 343   021- 4
    }

    /**
     * Question 1: Red-Nosed Reindeer nuclear fusion/fission plant engineers 
     * need help analyzing some unusual data from the Red-Nosed reactor reports.
     * <p>Each report (record) is a list of numbers called levels that are separated by spaces.
     * <p>A report only counts as safe if both of the following are true,
     * <p> - The levels are either all increasing or all decreasing.
     * <p> - Any two adjacent levels differ by at least one and at most three.
     * <p>
     * <p>How many reports are safe?
     */
    private static void question1() {
        boolean[] safe = new boolean[len];
        int safeCnt = 0;
        int[] recd = new int[len];
        for(int i = 0; i < len; i++){
            recd = parceStr2Int(fileInfo[i]);
            safe[i] = chkSafe(recd) < 0;
        }
        for(boolean sCnt : safe) if(sCnt) safeCnt++;
        
        //Track ,  Confirmed: 02- 279   021- 2
        System.out.println("\nPart 1: Number of safe reports: " + safeCnt);
    }
    
    /**
     * Question 2: The Problem Dampener is a reactor-mounted module that lets 
     * the reactor safety systems tolerate a single bad level.
     * <p>The same rules apply as before, except if removing a single level 
     * from an unsafe report would make it safe, the report instead counts as safe.
     * <p>
     * <p>How many reports are now safe?
     */
    private static void question2() {
        boolean[] safe = new boolean[len];
        int safeCnt = 0;
        int[] recd = new int[len];
        for(int i = 0; i < len; i++){
            recd = parceStr2Int(fileInfo[i]);
            safe[i] = chkSafe2(recd) < 0;
        }
        for(boolean sCnt : safe) if(sCnt) safeCnt++;
        
        //Track ,  Confirmed: 02- 343   Hi-399, Lo-339  021- 4
        System.out.println("\nPart 2: Number of safe reports (with Dampener): " + safeCnt + "\n");
    }

    /**
     * Convert a String with numbers separated by spaces to and array of integers.
     * @param recdIn Record string of level numbers.
     * @return An int array of those numbers.
     */
    private static int[] parceStr2Int(String recdIn){
        String[] tmpStr = recdIn.split("\\s");
        int[] recdLvl = new int[tmpStr.length];
        for(int i = 0; i < tmpStr.length; i++) recdLvl[i] = Integer.parseInt(tmpStr[i]);
        return recdLvl;
    }

    /**
     * Check if record is safe.
     * @param recdLvl Record level to check
     * @return true if safe else false.
     */
    private static int chkSafe(int[] recdLvl){
        boolean isPos = (recdLvl[1] - recdLvl[0] > 0);
        for(int i = 0; i < recdLvl.length - 1; i++){
            if(!isSafe(recdLvl[i], recdLvl[i + 1], isPos)) return i;
        }
        return -1;
    }

    /**
     * Check if 2 level numbers is safe.
     * @param lvl1
     * @param lvl2
     * @param isPos needed to check if polarity matches first 2 levels.
     * @return
     */
    private static boolean isSafe(int lvl1, int lvl2, boolean isPos){
        int tmpInt = lvl2 - lvl1;
        if(isPos ^ (tmpInt > 0)) return false;     //1. all increasing or all decreasing
        tmpInt = Math.abs(tmpInt);
        if(tmpInt > 3 || tmpInt < 1) return false;  //2. at least one and at most three
        return true;
    }

    /**
     * Check if record is safe.  Added Problem Damper.
     * <p>Check full.
     * <p>Check without level index where issue occured.
     * <p>Check without level after index where issue occured.
     * <p>Check without level PRIOR to index where issue occured.
     * @param recdLvl Record level to check
     * @return true if safe else false.
     */
    private static int chkSafe2(int[] recdLvl){
        int[] tmpRLvl;

        int lvlIdx = chkSafe(recdLvl);          //Chk normal
        if(lvlIdx < 0) return -1;
        tmpRLvl = removeLvl(lvlIdx, recdLvl);   //else remove 1st lvl
        if(chkSafe(tmpRLvl) < 0) return -1;     //try again 
        tmpRLvl = removeLvl(lvlIdx + 1, recdLvl);   //else remove 2nd lvl
        if(chkSafe(tmpRLvl) < 0) return -1;     //try again
        tmpRLvl = removeLvl(lvlIdx - 1, recdLvl);   //else remove prv lvl
        if(chkSafe(tmpRLvl) < 0) return -1;     //try again
        return 999;
    }

    /**
     * Remove the level at the index from the array.
     * @param rmvLvl index location to remove.
     * @param longLvl array to remove it from.
     * @return int array with 1 less level.
     */
    private static int[] removeLvl(int rmvLvl, int[] longLvl){
        int[] tmpInt = new int[longLvl.length - 1];
        int i = 0;
        for(; i < rmvLvl; i++) tmpInt[i] = longLvl[i];
        i++;
        for(; i < longLvl.length; i++) tmpInt[i - 1] = longLvl[i];
        return tmpInt;
    }
}