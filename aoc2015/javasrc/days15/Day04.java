package days15;

import java.io.IOException;

import type.MD5Hash;
import util.ReadWriteFiles;

public class Day04 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day04(){}

    public static void update() throws IOException {
        /**All in same file.  [0]-04 Q1 & 2, [3]=041a, [8]=041b */
        String fNum = "04"; //Part1- 254575   Part2- 1038736
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 04- 254575   041a- 609043  041b- 1048970
        question2();    //Confirmed: 04- 1038736
    }

    /**
     * Question 1: Santa needs to find MD5 hashes which, in hexadecimal, start with at least 
     * five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given 
     * below) followed by a number in decimal. To mine AdventCoins, you must find Santa 
     * the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
     * 
     * If your secret key is abcdef, [3], the answer is 609043, because the MD5 hash of abcdef609043 
     * starts with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
     * If your secret key is pqrstuv [8], the lowest number it combines with to make an MD5 hash 
     * starting with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks 
     * like 000006136ef....
     */
    private static void question1() {
        String md5Rash = "";
        int i = 100000;
        for(; i < 9999999; i++){
            md5Rash = MD5Hash.getMd5(fileInfo[0] + i);
            if(chkMD5LeadZeros(md5Rash, 5)) break;
        }
        //Track ,  Confirmed: 04- 254575   041a- 609043  041b- 1048970
        System.out.println("\nPart 1: Advent Coin for 5 lz: " + md5Rash + " with " + i);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        String md5Rash = "";
        int i = 100000;
        for(; i < 9999999; i++){
            md5Rash = MD5Hash.getMd5(fileInfo[0] + i);
            if(chkMD5LeadZeros(md5Rash, 6)) break;
        }
        //Track ,  Confirmed: 04- 1038736
        System.out.println("\nPart 2: Advent Coin for 6 lz: " + md5Rash + " with " + i);
    }

    //---------------- Part 1 ---------------------
    private static boolean chkMD5LeadZeros(String inMD5, int ld0Cnt){
        boolean lead5Zero = true;
        for(int i = 0; i < ld0Cnt; i++) lead5Zero &= inMD5.charAt(i) == '0';
        return lead5Zero;
    }
}