package util;

import java.util.Arrays;
/**
 * A set of array utilities to copy, expand or append arrays.
 * Mainly, 2d arrays.
 */
public class AryUtil {
    /**
     * Copy values from an array to another array (not reference).
     * Not sure why I need this.  Could use newAry = arr.copyOf(ary)
     * @param from array to copy from
     * @return a new array with copied values.
     */
    public static int[] copyInt(int[] from){
        int [] myAry = new int[from.length];
        for(int i = 0; i < from.length; i++){
            myAry[i] = from[i];
        }
        return myAry;
    }

    /**
     * Copy values from a 2d array and return a new 2d array (not reference).
     * @param from 2d array to copy from.
     * @return a new 2d array with copied values.
     */
    public static int[][] copyInt2By(int[][] from){
        int [][] myAry = new int[from.length][from[0].length];
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[0].length; j++){
                myAry[i][j] = from[i][j];
            }
        }
        return myAry;
    }

    /**
     * Copy values from a 2d array to another 2d array.
     * @param from 2d array to copy from.
     * @param to 2d array, passed, with copied values.
     */
    public static void copyInt2By(int[][] from, int[][] to){
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[0].length; j++){
                to[i][j] = from[i][j];
            }
        }
        return;
    }

    /**
     * Copies a 2d array and resizes
     * @param from 2d int array to copy 
     * @param size new size
     * @return return original array, with values, + new size
     */
    public static int[][] copyInt2By(int[][] from, int size){
        int [][] myAry = new int[size][from[0].length];
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[0].length; j++){
                myAry[i][j] = from[i][j];
            }
        }
        return myAry;
    }

    /**
     * Returns a copy of an array.
     * Not sure why I wrote this?  Use newAry = Arrays.copyOf(ary).
     * @param from
     * @return
     */
    public static String[] copyStr(String[] from){
        String [] myAry = new String[from.length];
        for(int i = 0; i < from.length; i++){
            myAry[i] = from[i];
        }
        return myAry;
    }

    /**
     * Append a 2d array to the end of another 2d array.
     * @param org original 2d array
     * @param app 2d array to append
     * @return original arrary + appended array
     */
    public static long[][] appendLong2By(long[][] org, long app[][]){
        int orgLen = org.length;
        int appLen = app.length;

        org = Arrays.copyOf(org, orgLen + appLen);
        for(int i = 0; i < appLen; i++){
            org[orgLen + i] = Arrays.copyOf(app[i], app[0].length);
        }
        return org;
    }

    /**
     * Sort a 2d array.  ----Not done ----
     * @param org original 2d array
     * @param app 2d array to append
     * @return original arrary + appended array
     */
    // public static int[][] sortAry2By(int[][] aryIn){
        // int orgLen = aryIn.length;
        // int subLen = aryIn[0].length;

        // Arrays.sort(aryIn[0]);
        // for(int i = 0; i < appLen; i++){
        //     org[orgLen + i] = Arrays.copyOf(app[i], app[0].length);
        // }
        // return org;
    // }

}