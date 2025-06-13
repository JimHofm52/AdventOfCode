package days24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.ReadWriteFiles;

public class Day11 {
    private static String[] fileInfo;
    private static int len;
    private static long startTm;

    /** Constructor, not needed but used for standards. 
     * <p>Started June 3, 2025,  Finished on June 13, 2025.
     * <p>Took Days.  Runtime 0.296 S.
    */
    private Day11(){}

    public static void update() throws IOException {
        String fNum = "11";     //Part1- 212655   Part2- 253582809724830
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        long[] stonesIn = ReadWriteFiles.parceStrIntSpS(fileInfo[0]);
    
        HashMap<Long, Long> stones = new HashMap<>();   //Build hashMap
        for (long stone : stonesIn) {                   //add (key, value), (stone, occurances) 
            stones.put(stone, stones.getOrDefault(stone, Long.valueOf(0)) + 1);
        }

        int q1 = 25;
        int q2 = 75;

        stones = question1(stones, q1);     //Confirmed: 11- 212655  hi-230036  111- 55312, for 25
        //Already did the first 25.
        question2(stones, (q2 - q1));       //Confirmed: 11- 253582809724830   111- ???
    }

    /**
     * Question 1: Consider the arrangement of stones in front of you. 
     * How many stones will you have after blinking 25 times?
     * <p>Ex. 111 => initial stones: 125 17
     * <p>After 6 blinks 22: 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2
     * <p>After 25 blinks 55312
     */
    private static HashMap<Long, Long> question1(HashMap<Long, Long> stones,  int blinks) {
        // Long stoneCnt = blink6(blinks);

        stones = blink7(stones, blinks);
        Long stoneCnt = Long.valueOf(0);
        for (Long value : stones.values()) {
            stoneCnt += value;
        }

        //Track ,  Confirmed: 212655  hi-230036   111- 55312
        System.out.println("\nPart 1: Total stones after " + blinks + " blinks: " + stoneCnt);

        return stones;
    }
    
    private static void question2(HashMap<Long, Long> stones, int blinks) {
        // Long stoneCnt = blink6(blinks);

        stones = blink7(stones, blinks);
        Long stoneCnt = Long.valueOf(0);
        for (Long value : stones.values()) {
            stoneCnt += value;
        }

        //Track ,  Confirmed: 253582809724830   111- 55312  -- 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2
        System.out.println("\nPart 2: Total stones after " + blinks +" more blinks: " + stoneCnt);
    }

    /**
     * Stoled ... I mean borrowed this from https://github.com/der-siebte-schatten/AdventOfCode-2024/blob/master/src/Day11.java
     * after 5 trys on my own and DAYS of effort.
     * <p>My first try was going to ~9 months to run.
     * <p>Other solutions, best guess, my solution would have taken 17 days.
     * <p>Learned sumpthin new.  SOOooo, learning, not stealing.  ;-)
     */
    private static long blink6(int blinks){
        //Create a hashMap (key, value), (stone, blinks?) and read in stones.
        HashMap<Long, Long> stones = new HashMap<>();
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
            HashMap<Long, Long> new_stones = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stones.entrySet()) { //for each stone in previous (or original) list
                Long stone = entry.getKey();                        //get stone num
                Long n = entry.getValue();                          //and occurance cnt
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
     * Reworked blink6 to my style.
     * @param stones HashMap of unique stone nums as the key and occurances as the value
     * @param blinks to evaluate.
     * @return a the hashMap with new stones added
     */
    private static HashMap<Long, Long> blink7(HashMap<Long, Long> stones, int blinks){
        for (int k = 0; k < blinks; k++) {
            //New hashMap (new stones, cnt?)
            HashMap<Long, Long> newStones = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stones.entrySet()) { //for each stone in previous (or original) list
                Long stone = entry.getKey();                        //get stone num
                Long n = entry.getValue();                          //and occurances cnt
                if (stone == 0)                                     //If == 0
                    newStones.put(Long.valueOf(1), n);           //add stone (1) & value n if new else replace value with n
                else if (Long.toString(stone).length() % 2 == 0) {  //If even digits
                    //Add first half stone & value with default or update value if stone exists Plus n
                    newStones.put(Long.parseLong(Long.toString(stone).substring(0, Long.toString(stone).length() / 2)),
                            newStones.getOrDefault(Long
                                    .parseLong(Long.toString(stone).substring(0, Long.toString(stone).length() / 2)),
                                    Long.valueOf(0))
                                    + n);
                    //Add second half stone & value with default or update value if stone exists Plus n
                    newStones.put(Long.parseLong(Long.toString(stone).substring(Long.toString(stone).length() / 2)),
                            newStones.getOrDefault(
                                    Long.parseLong(Long.toString(stone).substring(Long.toString(stone).length() / 2)),
                                    Long.valueOf(0)) + n);
                } else
                    newStones.put(stone * 2024, n);    //else multi 2034 and add (or update)
            }
            stones = newStones;    //Replace the previous (or original) stones map with the new stones map.
        }
        return stones;
    }

}