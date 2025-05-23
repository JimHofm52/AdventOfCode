package days21;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day11 {
    private static String fileInfo[];
    private static int len;
    private static int[][] bdoELvl;  //Array of bioluminescent dumbo octopuses
    private static int totFlashCnt;

    /** Constructor, not needed but used for standards. */
    private Day11(){}

    public static void update() throws IOException {
        String fNum = "11";
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        bdoELvl = parceInput();

        question1();    //Confirmed: 11-1675   111-1656
        question2();    //Confirmed: 11-???   111-195
    }

    /**
     * Question 1: Count the number of flashes after 100 steps per the following rules:
     * <p>1.  All increment by 1
     * <p>2.  Any .GT. 9 flash which cause any adjcent to increment, including digonally.
     * Any .GT. 9 then also flash.  This continues until no more flash.
     * <p>3.  Any that flash now have a power level of 0.
     */
    private static void question1() {
        totFlashCnt = 0;
        for(int cnt = 0; cnt < 100; cnt++){
            applyRule1(bdoELvl);   //Incr all by 1
            do{
                applyRule2(bdoELvl);
            }while(cntEQ10(bdoELvl) != 0);
            totFlashCnt += cntGT9(bdoELvl, true);;
        }
        //Track ,  Confirmed: 11-1675   111-1656
        System.out.println("\nPart 1: Total flashed after 100 steps: " + totFlashCnt);
    }
    
    /**
     * Question 2: Count the number steps before ALL flash on the same step.
     */
    private static void question2() {
        int flashCntThisStep = 0;
        int cntStep = 100;      //bdoELvl global, start where part 1 left off.
        do{
            applyRule1(bdoELvl);   //Incr all by 1
            do{
                applyRule2(bdoELvl);
            }while(cntEQ10(bdoELvl) != 0);
            flashCntThisStep = cntGT9(bdoELvl, true);
            totFlashCnt += flashCntThisStep;
            cntStep++;
        }while(flashCntThisStep != 100);

        //Track ,  Confirmed: 11-515   111-195
        System.out.println("\nPart 2: Total flashes: " + totFlashCnt);
        System.out.println("Part 2: All flashed after step: " + cntStep);
    }

    /**
     * @return Parce of input into row/column values
     */
    private static int[][] parceInput(){
        int[][] bdoIn = new int[len][fileInfo[0].length()];
        for(int r = 0; r < len; r++){
            for(int c = 0; c < fileInfo[0].length(); c++){
                bdoIn[r][c] = fileInfo[r].charAt(c) - 48;
            }
        }
        return bdoIn;
    }

    /**
     * Increment all values by 1.
     * @param bdoIn
     */
    private static void applyRule1(int[][] bdoIn){
        for(int r = 0; r < bdoIn.length; r++){
            for(int c = 0; c < bdoIn[0].length; c++){
                bdoIn[r][c]++;
            }
        }
    }

    /**
     * Increment all cells around any value GT 9, 10.  But not if the ajacent 
     * cell IS 10.  Wait until that cell is evaluated to increment.
     * @param bdoIn
     */
    private static void applyRule2(int[][] bdoIn){
        for(int r = 0; r < bdoIn.length; r++){
            for(int c = 0; c < bdoIn[0].length; c++){
                if(bdoIn[r][c] == 10) chargeNear(r, c, bdoIn);
            }
        }
    }

    /**
     * Increment adjacent cells.
     * @param r
     * @param c
     * @param bdoIn
     */
    private static void chargeNear(int r, int c, int[][] bdoIn){
        int rwChk, colChk;
        for(int rw = 0; rw < 3; rw++){          //Chk 3 rows
            for(int col = 0; col < 3; col++){   //and 3 col
                rwChk = r - 1 + rw;             //1 row ahaed
                colChk = c - 1 + col;           //and 1 col up
                if(rwChk >= 0 && rwChk <= bdoIn.length - 1 &&       //If row valid
                   colChk >= 0 && colChk <= bdoIn[0].length - 1 &&  //if col valid
                   bdoIn[rwChk][colChk] != 10){ //and cells is NOT 10, wait and eval later
                        bdoIn[rwChk][colChk]++; //Increment
                }
            }
        }
        bdoIn[r][c] = 11;   //Since self is 10 didn't update, update now.
    }

    /**
     * Count values in array that are GT 9.  Zero if requested.
     * @param bdoIn
     * @param zero Zero if GT 9
     * @return
     */
    private static int cntGT9(int[][] bdoIn, boolean zero){
        int cnt = 0;
        for(int r = 0; r < bdoIn.length; r++){
            for(int c = 0; c < bdoIn[0].length; c++){
                if(bdoIn[r][c] > 9){
                    cnt++;
                    if(zero) bdoIn[r][c] = 0;
                }
            }
        }
        return cnt;
    }

    /**
     * Count values EQ 10
     * @param bdoIn
     * @return
     */
    private static int cntEQ10(int[][] bdoIn){
        int cnt = 0;
        for(int r = 0; r < bdoIn.length; r++){
            for(int c = 0; c < bdoIn[0].length; c++){
                if(bdoIn[r][c] == 10){
                    cnt++;
                }
            }
        }
        return cnt;
    }

}