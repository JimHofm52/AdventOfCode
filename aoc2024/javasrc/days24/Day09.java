package days24;

import java.io.IOException;

import javax.swing.text.StyledEditorKit.BoldAction;

import type.Block;
import util.ReadWriteFiles;

public class Day09 {
    private static String fileInfo;
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started May 26, 2025,  Finished on May 26, 2025.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day09(){}

    public static void update() throws IOException {
        String fNum = "09";//Part1- 6201130364722   Part2- ???
        fileInfo = ReadWriteFiles.getInputStrOnly(fNum);   //Get input in an array for 1
        len = fileInfo.length();          //Length of input array
        int width = fileInfo.length();
        Block[] diskMap = getBlocks(fileInfo);
        // prtBlks(diskMap);

        question1(diskMap);    //Confirmed: 09- 6201130364722  lo: 2417264176946  091- 1928
        // prtBlks(diskMap);
        question2();    //Confirmed: 09- ???   091- ???
    }

    /**
     * Question 1: Compact the amphipod's hard drive using the process he requested. 
     * What is the resulting filesystem checksum?
     */
    private static void question1(Block[] blk) {
        blk = cmprBlocks(blk);
        long chksum = calcChksum(blk);

        //Track ,  Confirmed: 09- 6201130364722  lo: 2417264176946  091- 1928
        System.out.println("\nPart 1: The checksum for the defrag/compressed disk is: " + chksum);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 09- ???   091- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }

    private static Block[] getBlocks(String strIn){
        int diskLen = strIn.length() + strIn.length() % 2;
        Block[] tmpBlk = new Block[diskLen / 2];
        int tmpFileSz, tmpFreeSz;
        for(int i = 0; i < strIn.length(); i++){
            tmpFileSz = strIn.charAt(i) - 48;
            tmpFreeSz = ++i < len ? strIn.charAt(i) - 48 : 0;
            tmpBlk[i / 2] = new Block(tmpFileSz, tmpFreeSz);
        }
        return tmpBlk;
    }

    private static Block[] cmprBlocks(Block[] blkIn){
        int fileIdx = findLastFile(blkIn);
        int freeIdx = findNextFreeSp(blkIn);
        while(fileIdx != freeIdx){
            moveFile(blkIn, fileIdx, freeIdx);
            fileIdx = findLastFile(blkIn);
            freeIdx = findNextFreeSp(blkIn);
        }
        return blkIn;
    }

    private static int findLastFile(Block[] blkIn){
        int idx = blkIn.length - 1;
        for( ; idx > 0; idx--) if(blkIn[idx].fileSz() != 0) return idx;
        return idx;
    }

    private static int findNextFreeSp(Block[] blkIn){
        int idx = 0;
        for( ; idx < blkIn.length; idx++) if(blkIn[idx].freeSpSz() != 0) return idx;
        return idx;
    }

    private static void moveFile(Block[] blkIn, int from, int to){
        blkIn[to].addFileID(blkIn[from].blockID());
        blkIn[from].clearFileID();
    }

    private static long calcChksum(Block[] blkIn){
        long chksum = 0;
        int fileIdx = 0;
        for(Block blk : blkIn){
            chksum += blk.getBlkChkSum(fileIdx);
            fileIdx += blk.totSz();
        }
        return chksum;
    }

    private static void prtBlks(Block[] blkIn){
        String str = "";
        for(Block blk : blkIn){
            for(int i : blk.fileIDs()){
                str += (i > -1 ?  i : "." );
            }
        }
        System.out.println(str);
    }
}