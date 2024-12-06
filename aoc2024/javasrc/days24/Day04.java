package days24;

import java.io.IOException;

import util.ReadInput;

public class Day04 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started Dec 4, 2024,  Finished on Dec 5, 2024.
     * <p>Took ~xx hour.  Runtime 0.226 S.
    */
    private Day04(){}

    public static void update() throws IOException {
        String fNum = "04";//Part1- 2297   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 04- 2297   041- 18
        question2();    //Confirmed: 04- ???   041- 9
    }

    /**
     * Question 1: How many times does XMAS appear in the word search
     * in any order?
     */
    private static void question1() {
        int xCnt = cntX('X');
        int[][] x = new int[xCnt][2];
        getXYCoor(x, 'X');
        int xmasCnt = cntXmasL2R(x);
        xmasCnt += cntXmasUpDn(x);
        xmasCnt += cntXmasDiag(x);
        //Track ,  Confirmed: 04- 2297  Lo-2271  041- 18
        System.out.println("\nPart 1: XMAS appears " + xmasCnt + " times");
    }
    
    /**
     * Question 2: How many times does an X-MAS appear?
     */
    private static void question2() {
        int xCnt = cntX('A');
        int[][] x = new int[xCnt][2];
        getXYCoor(x, 'A');
        int xmasCnt = cntXMas(x);

        //Track ,  Confirmed: 04- 1745   041- 9
        System.out.println("\nPart 2: X-MAS appears: " + xmasCnt + " times");
    }

    private static int cntX(char ch){
        int cnt = 0;
        for(String s : fileInfo){
            for(int i = 0; i < s.length(); i++){
                if(s.charAt(i) == ch) cnt++;
            }
        } 
        return cnt;
    }

    private static void getXYCoor(int[][] xCoor, char ch){
        int idx = 0;
        for(int x = 0; x < fileInfo.length; x++){
            for(int y = 0; y < fileInfo[0].length(); y++){
                if(fileInfo[x].charAt(y) == ch){
                    xCoor[idx][0] = x;
                    xCoor[idx][1] = y;
                    idx++;
                }
            }
        }
    }

    private static int cntXmasL2R(int[][] xInfo){
        int cnt = 0;
        String tstStr;
        int rLmt = fileInfo[0].length() - 3;
        int lLmt = 2;
        //Counting right
        for(int i = 0; i < xInfo.length; i++){
            if(xInfo[i][1] < rLmt){
                tstStr = fileInfo[xInfo[i][0]].substring(xInfo[i][1], xInfo[i][1] + 4);
                if(tstStr.equals("XMAS")) cnt++;
            }
            //Counting left
            if(xInfo[i][1] > lLmt){
                tstStr = fileInfo[xInfo[i][0]].substring(xInfo[i][1] - 3, xInfo[i][1] + 1);
                if(tstStr.equals("SAMX")) cnt++;
            }
        }
        return cnt;
    }

    private static int cntXmasUpDn(int[][] xInfo){
        int cnt = 0;
        String tstStr;
        int btmLmt = fileInfo.length - 3;
        int topLmt = 2;
        //Counting dn
        for(int i = 0; i < xInfo.length; i++){
            tstStr = "";
            if(xInfo[i][0] < btmLmt){
                for(int s = xInfo[i][0]; s < xInfo[i][0] + 4; s++){
                    tstStr = tstStr + fileInfo[s].charAt(xInfo[i][1]);
                }
                if(tstStr.equals("XMAS")) cnt++;
            }
            //Counting up
            tstStr = "";
            if(xInfo[i][0] > topLmt){
                for(int s = xInfo[i][0]; s > xInfo[i][0] - 4; s--){
                    tstStr = tstStr + fileInfo[s].charAt(xInfo[i][1]);
                }
                if(tstStr.equals("XMAS")) cnt++;
            }
        }
        return cnt;
    }

    private static int cntXmasDiag(int[][] xInfo){
        int cnt = 0;
        String tstStr;

        int lLmt = 2;
        int rLmt = fileInfo[0].length() - 3; 
        int topLmt = 2;
        int btmLmt = fileInfo.length - 3;
        for(int i = 0; i < xInfo.length; i++){
            //Testing for dn to the right
            tstStr = "";
            if(xInfo[i][0] < btmLmt && xInfo[i][1] < rLmt){
                for(int j = 0; j < 4; j++){
                    tstStr = tstStr + fileInfo[xInfo[i][0] + j].charAt(xInfo[i][1] + j);
                }
                if(tstStr.equals("XMAS")) cnt++;
            }
            //Testing dn to the left
            tstStr = "";
            if(xInfo[i][0] < btmLmt && xInfo[i][1] > lLmt){
                for(int j = 0; j < 4; j++){
                    tstStr = tstStr + fileInfo[xInfo[i][0] + j].charAt(xInfo[i][1] - j);
                }
                if(tstStr.equals("XMAS")) cnt++;
            }
            //Testing for up to the right
            tstStr = "";
            if(xInfo[i][0] > topLmt && xInfo[i][1] < rLmt){
                for(int j = 0; j < 4; j++){
                    tstStr = tstStr + fileInfo[xInfo[i][0] - j].charAt(xInfo[i][1] + j);
                }
                if(tstStr.equals("XMAS")) cnt++;
            }
            //Testing up to the left
            tstStr = "";
            if(xInfo[i][0] > topLmt && xInfo[i][1] > lLmt){
                for(int j = 0; j < 4; j++){
                    tstStr = tstStr + fileInfo[xInfo[i][0] - j].charAt(xInfo[i][1] - j);
                }
                if(tstStr.equals("XMAS")) cnt++;
            }
        }
        return cnt;
    }

    private static int cntXMas(int[][] xInfo){
        int cnt = 0;
        String[] tstStr = new String[3];

        int lLmt = 0;
        int rLmt = fileInfo[0].length() - 1; 
        int topLmt = 0;
        int btmLmt = fileInfo.length - 1;
        for(int i = 0; i < xInfo.length; i++){
            if(xInfo[i][0] > topLmt && xInfo[i][0] < btmLmt &&
               xInfo[i][1] > lLmt && xInfo[i][1] < rLmt){
                for(int j = -1; j < 2; j++){
                    tstStr[j + 1] = fileInfo[xInfo[i][0] + j].substring(xInfo[i][1] - 1, xInfo[i][1] + 2);
                }
                cnt += tstXMas(tstStr);
            }
        }
        return cnt;
    }

    private static int tstXMas(String[] xStr){
        //Testing for dn to the right
        String tstStr = "" + xStr[0].charAt(0) + xStr[0].charAt(2) 
                            + xStr[2].charAt(0) + xStr[2].charAt(2);

        /* M.M   S.S   M.S   S.M
         * .A.   .A.   .A.   .A.
         * S.S   M.M   M.S   S.M
         */
        if(tstStr.equals("MMSS")) return 1;  //Testing for M_M & S_S
        if(tstStr.equals("SSMM")) return 1;  //Testing for S_S & M_M
        if(tstStr.equals("MSMS")) return 1;  //Testing for M_S & M_S
        if(tstStr.equals("SMSM")) return 1;  //Testing for S_M & S_M

        return 0;
    }

}