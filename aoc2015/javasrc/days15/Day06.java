package days15;

import java.io.IOException;

import type.LiteInstr;
import util.ReadInput;

public class Day06 {
    private static String[] fileInfo;
    private static int len;
    private static LiteInstr[] liteInstr;

    /** Constructor, not needed but used for standards. */
    private Day06(){}

    public static void update() throws IOException {
        String fNum = "06";//Part1- 377891   Part2- 14110788
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        liteInstr = new LiteInstr[len];
        for(int i = 0; i < len; i++){
            liteInstr[i] = new LiteInstr(fileInfo[i]);
        }

        question1();    //Confirmed: 06- 377891     061- 998996
        question2();    //Confirmed: 06- 14110788   061- 1001996
    }

    /**
     * Question 1: To defeat your neighbors this year in the holiday house decorating contest, 
     * all you have to do is set up your lights by doing the instructions Santa sent you in order.
     * Lighting grid is 1000 x 1000, 0,0 to 999, 999.  For example:
     * turn on 0,0 through 999,999 would turn on (or leave on) every light.
     * toggle 0,0 through 999,0 would toggle the first line of 1000 lights, 
     * turning off the ones that were on, and turning on the ones that were off.
     * turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
     * 
     * After following the instructions, how many lights are lit?
     */
    private static void question1() {
        int litCnt = 0;
        boolean[][] lites = new boolean[1000][1000];
        for(LiteInstr li : liteInstr){
            for(int x = li.getCoor1()[0]; x <= li.getCoor2()[0]; x++){
                for(int y = li.getCoor1()[1]; y <= li.getCoor2()[1]; y++){
                    lites[x][y] = execLiteCmd(li.getCmd(), lites[x][y]);
                }
            }
        }
        litCnt = cntLitesOn(lites);
        //Track ,  Confirmed: 06- 377891   061- 998996
        System.out.println("\nPart 1: Lites on: " + litCnt);
    }
    
    /**
     * Question 2: Opps, change turn on, off & toggle instructions:
     * turn on actually means that you should increase the brightness of those lights by 1.
     * turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.
     * toggle actually means that you should increase the brightness of those lights by 2.
     * 
     * What is the total brightness of all lights combined after following Santa's instructions?
     */
    private static void question2() {
        int totBright = 0;
        int[][] lites = new int[1000][1000];
        for(LiteInstr li : liteInstr){
            for(int x = li.getCoor1()[0]; x <= li.getCoor2()[0]; x++){
                for(int y = li.getCoor1()[1]; y <= li.getCoor2()[1]; y++){
                    lites[x][y] = Math.max(0, lites[x][y] + li.getCmd());
                }
            }
        }
        totBright = sumBrite(lites);
        //Track ,  Confirmed: 06- 14110788  hi-21877680  061- 1001996
        System.out.println("\nPart 2: Total brightness: " + totBright);
    }

    /**Convert lite command to off, on or toggle. */
    private static boolean execLiteCmd(int cmd, boolean val){
        switch(cmd){
            case -1:        //Lite off
            return false;
            case 1:         //Lite on
            return true;
            case 2:         //Toggle value passed
            return !val;
            default:
            System.out.println("Opps, Not a valid lite cmd: " + cmd);
            return val;
        }
    }

    /**Used in Part 1: Count the lites turned on. */
    private static int cntLitesOn(boolean[][] lit){
        int cnt = 0;
        for(int x = 0; x < lit.length; x++){
            for(int y = 0; y < lit[0].length; y++){
                if(lit[x][y]) cnt++;
            }
        }
        return cnt;
    }

    /**Used in Part 2:  Sum the brightness of the lites. */
    private static int sumBrite(int[][] lit){
        int cnt = 0;
        for(int x = 0; x < lit.length; x++){
            for(int y = 0; y < lit[0].length; y++){
                cnt += lit[x][y];
            }
        }
        return cnt;
    }

}