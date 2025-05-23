package days24;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import util.ReadWriteFiles;

public class Day03 {
    private static String fileInfo[];
    private static int len;
    private static boolean enable = true;

    /** Constructor, not needed but used for standards. 
     * <p>Started Dec 3, 2024,  Finished on Dec 4, 2024.
     * <p>Took alot.  Runtime 0.161 S.
    */
    private Day03(){}

    public static void update() throws IOException {
        String fNum = "03";//Part1- 188192787   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 03- 188192787   031- 161 (2*4 + 5*5 + 11*8 + 8*5)
        question2();    //Confirmed: 03- 113965544   031- 48 (2*4 + 8*5)
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        int[][] prod = new int[0][4];
        String tstStr;
        // Boolean enable = true;
        for(String s : fileInfo){
            tstStr = s;
            prod = findProd(tstStr, prod, false);
        }
        int totProd = 0;
        for(int i = 0; i < prod.length; i++) totProd += prod[i][2];
        
        //Track ,  Confirmed: 03- 188192787  Lo-30534327 Lo-183205962  031- 161 (2*4 + 5*5 + 11*8 + 8*5)
        System.out.println("\nPart 1: Total of products: " + totProd);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        int[][] prod = new int[0][4];
        String tstStr;
        // Boolean enable = true;
        for(String s : fileInfo){
            tstStr = s;
            prod = findProd(tstStr, prod, true);
        }
        int totProd = 0;
        for(int i = 0; i < prod.length; i++) totProd += prod[i][2];
        
        //Track ,  Confirmed: 03- 113965544  Hi-134529204  Lo-94077134  031- 48 (2*4 + 8*5)
        System.out.println("\nPart 2: ???: " + totProd + "\n");
    }

    private static int[][] findProd(String str, int[][] prodIn, boolean part2){
        // Scanner sc = new Scanner(str);
        int[][] valIn = Arrays.copyOf(prodIn, prodIn.length);
        String tmpStr;
        int[] num = new int[4];
        int beg = str.indexOf("mul(", 0);
        int end = beg;
        int begDo = beg;
        int begDont = beg;
        // boolean ena = true;
        while(end < str.length()){
            beg = str.indexOf("mul(", beg);

            if(part2){
                if(beg > begDont && begDont > 0) enable = false;
                if(beg > begDo && begDo > 0) enable = true;
                begDo = str.indexOf("do()", beg);
                begDont = str.indexOf("don't()", beg);
            }

            if(beg < 0) break;
            beg += 4;
            end = str.indexOf(')', beg);
            if(end < 0) break;
            tmpStr = str.substring(beg, end);
            if(enable && chkNums(tmpStr, num)) valIn = addNums(num, valIn, beg);
            beg = beg;
        }
        return valIn;
    }

    private static boolean chkNums(String str, int[] nums){
        char ch;
        for(int i = 0; i < str.length(); i++){      //Check for digit or comma
            ch = str.charAt(i);
            if(!(Character.isDigit(ch) || ch == ',')) return false;
        }
        String[] numStr = str.split(",");
        if(numStr.length != 2) return false;
        nums[0] = Integer.parseInt(numStr[0]);
        nums[1] = Integer.parseInt(numStr[1]);
        if(nums[0] < 0 || nums[0] > 999) return false;
        if(nums[1] < 0 || nums[1] > 999) return false;
        return true;
    }

    private static int[][] addNums(int[] numAdd, int[][] addTo, int numIdx){
        int[][] newNum = Arrays.copyOf(addTo, addTo.length + 1);
        newNum[addTo.length] = Arrays.copyOf(numAdd, numAdd.length);
        newNum[addTo.length][2] = numAdd[0] * numAdd[1];
        newNum[addTo.length][3] = numIdx;
        return newNum; 
    }

    private static void findDoS(int[][] doInfo){

    }

}