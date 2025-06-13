package days24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import util.ReadWriteFiles;

public class Day11Save {
    private static String[] fileInfo;
    private static int len;
    private static long startTm;
    private static boolean oldMethod = false;

    /** Constructor, not needed but used for standards. 
     * <p>Started Dec XX, 2024,  Finished on Dec XX, 2024.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day11Save(){}

    public static void update() throws IOException {
        startTm = System.currentTimeMillis();
        String fNum = "11";//Part1- 212655   Part2- 253582809724830
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        long[] stones = ReadWriteFiles.parceStrIntSpS(fileInfo[0]);

        int q1 = 25;
        int q2 = 75;

        // int[] stonesCnt = blink2(stones, q1, q2);
        // int stonesCnt = blink3(stones, q1);

        // stones = question1(stones, q1);     //Confirmed: 11- 212655  hi-230036  111- 55312, for 25
        //Already did the first 25.
        question2(stones, (q2));       //Confirmed: XX- ???   XX1- ???

        // tstDrvrDigitParce();
    }
    /*
    Part 2: Total stones after 15 blinks 3265           Execution time: 0.188
    Part 2: Total stones after 25 blinks 212655         Execution time: 0.223
    Part 2: Total stones after 35 blinks 13895215       Execution time: 0.37
    Part 2: Total stones after 45 blinks 908241074      Execution time: 5.371
    Part 2: Total stones after 55 blinks 59353017846    Execution time: 310.037
    Part 2: Total stones after 65 blinks 3879285942073  Execution time: 20252.718
     */

    /**
     * Question 1: Consider the arrangement of stones in front of you. 
     * How many stones will you have after blinking 25 times?
     * <p>Ex. 111 => initial stones: 125 17
     * <p>After 6 blinks 22: 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2
     * <p>After 25 blinks 55312
     */
    private static long[] question1(long[] stones, int blink) {
        for(int i = 0; i < blink; i++){
            stones = blink2(stones);
            System.out.println("Blink: " + (i + 1) +
                               "  Stones: " + stones.length +
                               "  Runtime: " + (System.currentTimeMillis() - startTm)/60000.0);
        }
        //Track ,  Confirmed: 212655- ?  hi-230036 111- 55312  -- 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2
        System.out.println("\nPart 1: Total stones after " + blink + " blinks: " + stones.length);

        return stones;
    }
    
    private static void question2(long[] stones, int blinkMx) {
        long stoneCnt = blink6(blinkMx);

        // long stoneCnt = 0;
        // startTm = System.currentTimeMillis();
        // for(int i = 0; i < stones.length; i++){
        //     stoneCnt += blink4(stones[i], blinkMx, 0);
        //     System.out.println("Stone: " + (i + 1) +
        //                        "  Cnt: " + stoneCnt +
        //                        "  Runtime: " + (System.currentTimeMillis() - startTm)/60000.0);
        // }
        // stoneCnt += stones.length;

        //Track ,  Confirmed: 253582809724830   111- 55312  -- 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2
        System.out.println("\nPart 2: Total stones after " + blinkMx +" blinks " + stoneCnt);
    }
    
    /**
     * Question 2: How many stones would you have after blinking a total of 75 times?
     * <p>Note: We already did 25 in part 1.
     */
    private static void question2a(long[] stones, int blink) {
        blink--;
        for(int i = 0; i < blink; i++){
            stones = blink2(stones);
            System.out.println("Pass: " + (i) +
                               "  Cnt: " + stones.length +
                               "  Runtime: " + (System.currentTimeMillis() - startTm)/60000.0);
        }
        //Track ,  Confirmed: 11- ???   111- same
        System.out.println("\nPart 2: Total stones after 75 blinks " + stones.length);
    }

    /**
     * Stoled ... I mean borrowed this from https://github.com/der-siebte-schatten/AdventOfCode-2024/blob/master/src/Day11.java
     * after 5 trys on my own and DAYS of effort.  Best guess, my solution would have taken 17 days.  Learned sumpthin new.
     */
    private static long blink6(int blinks){
        //Create a hashMap (key, value), (stone, blinks?) and read in stones.
        HashMap<Long, Long> stones = new HashMap<Long, Long>();
        try (BufferedReader bin = new BufferedReader(new FileReader("C:\\Users\\Hofmjc\\Documents\\_Prog\\AdventOfCode\\AoC2024\\aoc2024\\javasrc\\textIn\\Day11Input.txt"))) {
        // try (BufferedReader bin = new BufferedReader(new FileReader("Day11Input.txt"))) {
            String s = bin.readLine();
            String[] tokens = s.split(" ");     //String array of input
            for (String string : tokens) {            //Build hashMap 
                stones.put(Long.parseLong(string), stones.getOrDefault(Long.parseLong(string), Long.valueOf(0)) + 1);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        for (int k = 0; k < blinks; k++) {
            //New hashMap (new stones, cnt?)
            HashMap<Long, Long> new_stones = new HashMap<Long, Long>();
            for (Map.Entry<Long, Long> entry : stones.entrySet()) { //for each stone in previous (or original) list
                Long stone = entry.getKey();                        //get stone num
                Long n = entry.getValue();                          //and blink cnt
                if (stone == 0)                                     //If == 0
                    new_stones.put(Long.valueOf(1), n);           //add stone (1) & value n if new else replace value with n
                else if (Long.toString(stone).length() % 2 == 0) {  //If even digits
                    //Add first half stone & value with default or update value if stone exists Plus n
                    new_stones.put(Long.parseLong(Long.toString(stone).substring(0, Long.toString(stone).length() / 2)),
                            new_stones.getOrDefault(Long
                                    .parseLong(Long.toString(stone).substring(0, Long.toString(stone).length() / 2)),
                                    Long.valueOf(0))
                                    + n);
                    //Add second half stone & value with default or update value if stone exists Plus n
                    new_stones.put(Long.parseLong(Long.toString(stone).substring(Long.toString(stone).length() / 2)),
                            new_stones.getOrDefault(
                                    Long.parseLong(Long.toString(stone).substring(Long.toString(stone).length() / 2)),
                                    Long.valueOf(0)) + n);
                } else
                    new_stones.put(stone * 2024, n);    //else multi 2034 and add (or update)
            }
            stones = new_stones;    //Replace the previous (or original) stones map with the new stones map.
        }
        Long stone_count = Long.valueOf(0);
        for (Long value : stones.values()) {
            stone_count += value;
        }
        System.out.println(stone_count);
        return stone_count;
    }

    /**
     * Apply rules to stones
     * <p>1. If 0 => 1
     * <p>2. even digits, split into 2. left get left half of digits, right gets the rest
     * <p>3. else multipy by 2024.
     * @param stonesIn
     * @return new array of stones
     */
    private static void blink(long[] stonesIn){
        int tmpNum;
        boolean done;

        for(int i = 0; i < stonesIn.length; i++){
            done = false;
            if(stonesIn[i] == 0){
                stonesIn[i] = 1;
                done = true;
            }
            if(!done){
                tmpNum = (int) (Math.log10(stonesIn[i]) + 1);   //valueIn cannot be 0
                if(tmpNum % 2 == 0){
                    tmpNum /= 2;
                    tmpNum = (int)Math.pow(10, tmpNum);
                    
                    stonesIn = Arrays.copyOf(stonesIn, stonesIn.length + 1);
                    stonesIn[stonesIn.length - 1] = stonesIn[i] % tmpNum;
                    stonesIn[i] = stonesIn[i] / tmpNum;
                    done = true;
                };
            }
            if(!done){
                stonesIn[i] = stonesIn[i] * 2024;
            }
        }
    }

    private static long[] blink2(long[] stonesIn){
        long[] tmpStone = Arrays.copyOf(stonesIn, stonesIn.length);
        int tmpNum;

        for(int i = 0; i < stonesIn.length; i++){
            if(tmpStone[i] == 0){
                tmpStone[i] = 1;
            }else{
                tmpNum = (int) (Math.log10(tmpStone[i]) + 1);   //valueIn cannot be 0
                if(tmpNum % 2 == 0){
                    tmpNum /= 2;
                    tmpNum = (int)Math.pow(10, tmpNum);
                    tmpStone = Arrays.copyOf(tmpStone, tmpStone.length + 1);
                    tmpStone[tmpStone.length - 1] = stonesIn[i] % tmpNum;   //Left digits
                    tmpStone[i] = stonesIn[i] / tmpNum;   //Right digits
                }else{
                    tmpStone[i] *= 2024;
                }
            }
        }
        return tmpStone;
    }

    /**
     * Apply rules to stones
     * <p>1. If 0 => 1
     * <p>2. even digits, split into 2. left get left half of digits, right gets the rest
     * <p>3. else multipy by 2024.
     * @param stonesIn
     * @return new array of stones
     */
    private static int blink3(long stoneIn, int blinks){
        long[] tmpStone = new long[1];
        tmpStone[0] = stoneIn;
        int tmpNum;
        boolean done;
        int tmpCnt;

        for(int b = 0; b < blinks; b++){
            tmpCnt = tmpStone.length;
            for(int s = 0; s < tmpCnt; s++){
                done = false;
                if(tmpStone[s] == 0){
                    tmpStone[s] = 1;
                    done = true;
                }
                if(!done){
                    tmpNum = (int) (Math.log10(tmpStone[s]) + 1);   //valueIn cannot be 0
                    if(tmpNum % 2 == 0){
                        tmpNum /= 2;
                        tmpNum = (int)Math.pow(10, tmpNum);
                        tmpStone = Arrays.copyOf(tmpStone, tmpStone.length + 1);
                        tmpStone[tmpStone.length - 1] = tmpStone[s] % tmpNum;
                        tmpStone[s] = tmpStone[s] / tmpNum;
                        done = true;
                    }
                }
                if(!done){
                    tmpStone[s] = tmpStone[s] * 2024;
                }
            }
            System.out.println(b + "\t" + tmpStone.length);
        }
        return tmpStone.length;
    }

    /**
     * Apply rules to stones
     * <p>1. If 0 => 1
     * <p>2. even digits, split into 2. left get left half of digits, right gets the rest
     * <p>3. else multipy by 2024.
     * @param stonesIn
     * @param blinks
     * @param stonesCnt
     * @return stone count
     */
    private static long blink4(long stoneIn, int blinks, long stoneCnt){
        if(blinks < 1) return stoneCnt;

        int tmpNum = 0;
        if(stoneIn == 0 && oldMethod){
            stoneCnt += blink4(1, --blinks, 0);
        }else if(stoneIn < 10 && !oldMethod){
            stoneCnt += digitParce((int)stoneIn, blinks);
            int a = 0;
        }else if(((int) (Math.log10(stoneIn) + 1)) % 2 == 0){  //stoneIn cannot be 0
            tmpNum = (int) (Math.log10(stoneIn) + 1);
            tmpNum /= 2;
            tmpNum = (int) Math.pow(10, tmpNum);
            stoneCnt += blink4(stoneIn % tmpNum, --blinks, 0);
            stoneCnt += blink4(stoneIn / tmpNum, blinks, 0);
            stoneCnt++;
        }else{
            stoneCnt += blink4(stoneIn * 2024, --blinks, 0);
        }
        return stoneCnt;
    }

    /**
     * Driver to test digitParce().  
     */
    private static void tstDrvrDigitParce(){
        long stonesIn = 125;
        int blinksIn = 26;
        long stonesO = 0;
        long stonesN = 0;
        // long stones = digitParce(7, 9);
        for(int i = 1; i < blinksIn; i++){
            oldMethod = true;
            stonesO = blink4(stonesIn, i, 0);
            oldMethod = false;
            stonesN = blink4(stonesIn, i, 0);
            System.out.print("For " + i + " blinks, cnt is \told: \t" + stonesO);
            System.out.println(" \t new: " + stonesN + " \t diff: " + (stonesO - stonesN));
        }
    }

    /**
     * handle digits 0 - 9, 1 at a time.
     * @param digit
     * @param blinksRemaining
     * @return
     */
    private static long digitParce(int digit, int blinksRemaining){
        long stoneCnt = 0;
        int[] nxtInt = null;
        int blkLmt = 1;
        switch(digit){
            case 0:     //Goes from 0 to 1
                if(--blinksRemaining < blkLmt) break;      //blink 0 => 1
            case 1:     //1 => 2024 => 20 24
                if(--blinksRemaining < blkLmt) break;      //blink 1 => 2024
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;      //blink 2024 => 20, 24
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;      //blink 20, 24 => 2,0,2,4
                nxtInt = new int[] {2, 0, 2, 4};
            break;
            case 2:     //2 => 4048 => 40, 48 => 4,0,4,8
                if(--blinksRemaining < blkLmt) break;      //blink 2 => 4048
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;  //blink 4048 => 40, 48
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;  //blink 40, 48 => 4,0,4,8
                nxtInt = new int[] {4, 0, 4, 8};
            break;
            case 3:     //3 => 6072 => 60, 72 => 6, 0, 7, 2
                if(--blinksRemaining < blkLmt) break;    //blink 3 => 6072
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;    //blink 6102 => 60, 72
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;    //blink 61, 2 => 6, 0, 7, 2
                nxtInt = new int[] {6, 0, 7, 2};
            break;
            case 4:     //4 => 8096 => 80, 96 => 8,0,9,6
                if(--blinksRemaining < blkLmt) break;    //blink 4 => 8096
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;    //blink 8096 => 80, 96
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;    //blink 80, 96 => 8, 0, 9, 6
                nxtInt = new int[] {8, 0, 9, 6};
            break;
            case 5:     //5 => 10120 => 20482880 => 2048, 2880 => 20, 24, 28, 80 => 2,0,2,4,2,8,8,0
                if(--blinksRemaining < blkLmt) break;      //blink 5 => 10120
                if(--blinksRemaining < blkLmt) break;      //blink 10120 => 20482880
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;      //blink 20482880 => 2048, 2880
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;      //blink 2048, 2880 => 20, 48, 28, 80
                stoneCnt += 4;
                if(--blinksRemaining < blkLmt) break;      //blink 20, 48, 28, 80 => 2,0,4,8,2,8,8,0
                nxtInt = new int[] {2, 0, 4, 8, 2, 8, 8, 0};
            break;
            case 6:     //6 => 12144 => 24579456 => 2457, 9456 => 24, 57, 94, 56 => 2,4,5,7,9,4,5,6
                if(--blinksRemaining < blkLmt) break;      //6 => 12144
                if(--blinksRemaining < blkLmt) break;      //12144 => 24579456
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;      //24579456 => 2457, 9456
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;      //2457, 9456 => 24, 57, 94, 56
                stoneCnt += 4;
                if(--blinksRemaining < blkLmt) break;      //24, 57, 94, 56 => 2,4,5,7,9,4,5,6
                nxtInt = new int[] {2, 4, 5, 7, 9, 4, 5, 6};
            break;
            case 7:     //7 => 14168 => 28676032 => 2867, 6032 => 28, 67, 60, 32
                if(--blinksRemaining < blkLmt) break;      //7 => 14168
                if(--blinksRemaining < blkLmt) break;      //14168 => 28676032
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;      //28676032 => 2867, 6032
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;      //2867, 6032 => 28, 67, 60, 32
                stoneCnt += 4;
                if(--blinksRemaining < blkLmt) break;      //28, 67, 60, 32 => 2, 8, 6, 7, 6, 0, 3, 2
                nxtInt = new int[] {2, 8, 6, 7, 6, 0, 3, 2};
            break;
            case 8:     //8 => 16192 => 32772608 => 3277, 2608 => 32, 77, 26, 8 => 3, 2, 7, 7, 2, 6, 16192 
                if(--blinksRemaining < blkLmt) break;      //8 => 16192
                if(--blinksRemaining < blkLmt) break;      //16192 => 32772608
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;      //32772608 => 3277, +2608
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;      //3277, 2608 => 32, +77, 26, +8
                stoneCnt += digitParce(8, blinksRemaining);
                stoneCnt += 3;
                if(--blinksRemaining < blkLmt) break;      //32, +77, 26 => 3, 2, 7, 7, 2, 6
                nxtInt = new int[] {3, 2, 7, 7, 2, 6};
            break;
            case 9:     //9 => 18216 => 36869184 => 3686, 9184 => 36, 86, 91, 84 => 3, 6, 8, 6, 9, 1, 8, 4
                if(--blinksRemaining < blkLmt) break;      //9 => 18216
                if(--blinksRemaining < blkLmt) break;      //18216 => 36869184
                stoneCnt++;
                if(--blinksRemaining < blkLmt) break;      //36869184 => 3686, 9184
                stoneCnt += 2;
                if(--blinksRemaining < blkLmt) break;      //3686, 9184 => 36, 86, 91, 84
                stoneCnt += 4;
                if(--blinksRemaining < blkLmt) break;      //236, 86, 91, 84 => 3, 6, 8, 6, 9, 1, 8, 4
                nxtInt = new int[] {3, 6, 8, 6, 9, 1, 8, 4};
            break;
        }
        if(nxtInt != null){
            for(int num : nxtInt) stoneCnt += digitParce(num, blinksRemaining);
            int a = 0;
        }
        return stoneCnt;
    }
    /* Tried Q1 212655 stones as input to Q2 using blimk4 & didgitParce.
     * Best guess, 17 DAYS!
     * Stone: 9998  Cnt: 12126577626276  Runtime: 1067.90 min.
     * Stone: 9999  Cnt: 12127517150083  Runtime: 1068.00
     * Stone: 10000  Cnt: 12128789318300  Runtime: 1068.12
     * Stone: 10001  Cnt: 12130061486517  Runtime: 1068.23
     * Stone: 10002  Cnt: 12131117575626  Runtime: 1068.32
     */
}