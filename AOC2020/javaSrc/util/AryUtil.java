package util;

import java.util.Arrays;

public class AryUtil {
    public static int[] CopyInt(int[] from){
        int [] myAry = new int[from.length];
        for(int i = 0; i < from.length; i++){
            myAry[i] = from[i];
        }
        return myAry;
    }

    public static int[][] CopyInt2By(int[][] from){
        int [][] myAry = new int[from.length][from[0].length];
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[0].length; j++){
                myAry[i][j] = from[i][j];
            }
        }
        return myAry;
    }

    public static void CopyInt2By(int[][] from, int[][] to){
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[0].length; j++){
                to[i][j] = from[i][j];
            }
        }
        return;
    }

    public static int[][] CopyInt2By(int[][] from, int size){
        int [][] myAry = new int[size][from[0].length];
        for(int i = 0; i < from.length; i++){
            for(int j = 0; j < from[1].length; j++){
                myAry[i][j] = from[i][j];
            }
        }
        return myAry;
    }

    public static String[] CopyStr(String[] from){
        String [] myAry = new String[from.length];
        for(int i = 0; i < from.length; i++){
            myAry[i] = from[i];
        }
        return myAry;
    }

    public static long[][] AppendLong2By(long[][] org, long app[][]){
        int orgLen = org.length;
        int appLen = app.length;

        org = Arrays.copyOf(org, orgLen + appLen);
        for(int i = 0; i < appLen; i++){
            org[orgLen + i] = Arrays.copyOf(app[i], app[0].length);
        }
        return org;
    }

}