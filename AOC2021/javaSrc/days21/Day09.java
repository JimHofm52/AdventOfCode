package days21;

import java.io.IOException;
import java.util.Arrays;

import util.ReadWriteFiles;

public class Day09 {
    private static String fileInfo[];
    private static int len;
    private static short[][] nums;    //Row/Col of fileInfo

    /** Constructor, not needed but used for standards. */
    private Day09(){}

    public static void update() throws IOException {
        String fNum = "09";
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 09-504   091-15
        question2();    //Confirmed: 09-1558722   091-1134
    }

    /**
     * Question 1: Find the risk rating for navigating around
     * the underwater smokers by using the low points.
     */
    private static void question1() {
        parceIn();
        int[] riskRC = findLow();   //r*1000 + c
        int tmp;
        int myRisk = 0;
        for(int r = 0; r < riskRC.length; r++){
            myRisk += nums[riskRC[r] / 1000][riskRC[r] % 1000] + 1;
        }
        //Track ,  Confirmed: 09-504   091-15
        System.out.println("\nPart 1: Low risk rating: " + myRisk);
    }
    
    /**
     * Question 2: Find the size of the smoker basins as areas outlined by 9's.
     */
    private static void question2() {
        int[] bsnLo = findLow();            //Find all low points again.  Could transfer.
        int[] bsnCnt = findBasinSz(bsnLo);  //Find area of all basins.
        Arrays.sort(bsnCnt);                //Sort low to high
        int risk = 1;
        int bsnLen = bsnCnt.length;         //Multipy largest 3 areas (last 3).
        for(int i = bsnLen - 1; i > bsnLen - 4; i--) risk *= bsnCnt[i];
        //Track ,  Confirmed: 09-1558722   091-1134
        System.out.println("\nPart 2: Basin risk: " + risk);
    }

    /**
     * Parce a several rows of strings from a series of numbers, 0 - 9.
     */
    private static void parceIn(){
        int row = fileInfo.length;
        int col = fileInfo[0].length();
        nums = new short[row][col];
        for(int r = 0; r < row; r++){
            for(int c = 0; c < col; c++){
                nums[r][c] = Short.parseShort(String.valueOf(fileInfo[r].charAt(c)));
            }
        }
    }

    /**
     * Find the lowest points in the any area.
     * @return array of lowest point row/col coordinates, in the form
     * 1000 * row + col.  Easier to adjust a single dim array.
     */
    private static int[] findLow(){
        short[] vals = new short[5];    //[0]=org, [1]=left, [2]=top, [3]=right, [4]=btm
        int minCnt = 0;
        int[] lowRC = new int[minCnt + 1];
        int rLen = nums.length;
        int cLen = nums[0].length;
        for(int r = 0; r < rLen; r++){
            for(int c = 0; c < cLen; c++){
                //Store center point & surrounding in an array
                vals[0] = nums[r][c];                           //Center
                vals[1] = c < 1 ? 10 : nums[r][c - 1];          //Left
                vals[2] = r < 1 ? 10 : nums[r - 1][c];          //Top
                vals[3] = c > cLen - 2 ? 10 : nums[r][c + 1];   //Right
                vals[4] = r > rLen - 2 ? 10 : nums[r + 1][c];   //Bottom
                if(chkMin(vals)){
                    lowRC[minCnt] = r * 1000 + c;
                    minCnt++;
                    lowRC = Arrays.copyOf(lowRC, minCnt + 1);
                }
            }
        }
        lowRC = Arrays.copyOf(lowRC, minCnt);
        return lowRC;
    }

    /**
     * For each low point find the area of the basin as outlined ny 9's.
     * @param bsnLo
     * @return
     */
    private static int[] findBasinSz(int[] bsnLo){
        int[] bsnSz = new int[bsnLo.length];
        int rLo, cLo, bsnCnt;
        for(int bl = 0; bl < bsnLo.length; bl++){
            rLo = bsnLo[bl] / 1000;     //Row low coordinate
            cLo = bsnLo[bl] % 1000;     //Col low coordinate
            bsnSz[bl] = cntBasinSz(rLo, cLo);   //Start counting
        }
        return bsnSz;
    }

    /**
     * This is a recursive method to count all numbers in an area outlined by 9's.
     * 10 is added to the existing number to flag it has been counted.
     * <p>Check the point < 9, increment count and add 10 to point to flag as counted.
     * Then check Up, Down, Left & Right by calling this method until 9 or greater is 
     * found.
     * @param rCoor
     * @param cCoor
     * @return Array of basin sizes.
     */
    private static int cntBasinSz(int rCoor, int cCoor){
        int bsnCnt = 0;

        if(nums[rCoor][cCoor] < 9){         //If not a 9 (or 10)
            bsnCnt++;                       //add 1
            nums[rCoor][cCoor] += 10;       //Flag counted
            //If not at the edge of the mapped area check number to the:
            if(rCoor - 1 >= 0) bsnCnt += cntBasinSz(rCoor - 1, cCoor);              //Up
            if(rCoor + 1 < nums.length) bsnCnt += cntBasinSz(rCoor + 1, cCoor);     //Down
            if(cCoor + 1 < nums[0].length) bsnCnt += cntBasinSz(rCoor, cCoor + 1);  //Right
            if(cCoor - 1 >= 0) bsnCnt += cntBasinSz(rCoor, cCoor - 1);              //Left
        }
        return bsnCnt;
    }

    /**
     * Check to see if the first value in the array is the lowest.
     * @param vals
     * @return true if lowest else false
     */
    private static boolean chkMin(short[] vals){
        for(int i = 1; i < vals.length; i++){
            if(vals[i] <= vals[0]) return false;
        }
        return true;
    }
}