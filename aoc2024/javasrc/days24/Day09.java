package days24;

import java.io.IOException;
import java.util.Arrays;

import type.Block;
import util.ReadWriteFiles;

public class Day09 {
    private static String fileInfo;
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started May 26, 2025,  Finished on May 27, 2025.
     * <p>Took ~14 hour.  Runtime 0.413 S.
    */
    private Day09(){}

    public static void update() throws IOException {
        String fNum = "09";//Part1- 6201130364722   Part2- 6221662795602
        fileInfo = ReadWriteFiles.getInputStrOnly(fNum);   //Get input in an array for 1
        len = fileInfo.length();          //Length of input array

        Block[] diskMap1 = getBlocks(fileInfo);
        Block.resetBlockIDCntr();
        Block[] diskMap2 = getBlocks(fileInfo); //Array.copyof no working.

        question1(diskMap1);    //Confirmed: 09- 6201130364722  lo: 2417264176946  091- 1928
        // prtBlks(diskMap2);
        question2(diskMap2);    //Confirmed: 09- 6221662795602   091- 2858
        // prtBlks(diskMap2);
    }

    /**
     * Question 1: Compact the amphipod's hard drive using the process he requested. 
     * What is the resulting filesystem checksum?
     */
    private static void question1(Block[] blkIn) {
        cmprBlocks1(blkIn);
        long chksum = calcChksum(blkIn);

        //Track ,  Confirmed: 09- 6201130364722  lo: 2417264176946  091- 1928
        System.out.println("\nPart 1: The checksum for the defrag/compressed disk is: " + chksum);
    }
    
    /**
     * Question 2: Start over, now compacting the amphipod's hard drive using this new method instead. 
     * What is the resulting filesystem checksum?
     */
    private static void question2(Block[] blkIn) {
        Block[] blk = cmprBlocks2(blkIn);
        long chksum = calcChksum(blk);

        //Track ,  Confirmed: 09- 6221662795602   091- 2858
        System.out.println("\nPart 2: The checksum for the defrag/compressed disk is: " + chksum);
    }

    /**
     * Parce the input in to Blocks
     * @param strIn
     * @return array of disk Blocks
     */
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

    /**
     * Compress, defrag, the disk per instructions in part 1
     * @param blkIn Disk block info
     * @return defraged disk block info
     */
    private static Block[] cmprBlocks1(Block[] blkIn){
        int fileIdx = findLastFile(blkIn);
        int freeIdx = findNextFreeSp(blkIn);
        while(fileIdx != freeIdx){
            moveFile(blkIn, fileIdx, freeIdx, 1);
            fileIdx = findLastFile(blkIn);
            freeIdx = findNextFreeSp(blkIn);
        }
        return blkIn;
    }

    /**
     * Compress, defrag, the disk per instructions in part 2
     * @param blkIn Disk block info
     * @return defraged disk block info
     */
    private static Block[] cmprBlocks2(Block[] blkIn){
        int fileIdx;
        int fileSz;
        int freeIdx;
        for(int i = blkIn.length - 1; i > 0; i--){
            fileIdx = blkIn[i].blockID();
            fileSz = blkIn[i].orgFileSz();
            freeIdx = findNextBigFreeSp(blkIn, fileSz, i);
            if(freeIdx >= 0){
                moveAllFile(blkIn, fileIdx, freeIdx, fileSz);
                // prtBlks(blkIn);

            }
        }
        return blkIn;
    }

    /**
     * Find the last block with files.
     * @param blkIn
     * @return index of last block with files
     */
    private static int findLastFile(Block[] blkIn){
        int idx = blkIn.length - 1;
        for( ; idx > 0; idx--) if(blkIn[idx].fileSz() != 0) return idx;
        return idx;
    }

    /**
     * Find the first block with free space.
     * @param blkIn
     * @return the index
     */
    private static int findNextFreeSp(Block[] blkIn){
        int idx = 0;
        for( ; idx < blkIn.length; idx++) if(blkIn[idx].freeSpSz() != 0) return idx;
        return idx;
    }

    /**
     * Find the first block with free space to hold the files requested.
     * @param blkIn
     * @param fileSzNeeded
     * @param mxIdx
     * @return index
     */
    private static int findNextBigFreeSp(Block[] blkIn, int fileSzNeeded, int mxIdx){
        int idx = 0;
        for( ; idx < mxIdx; idx++){
            if(blkIn[idx].freeSpSz() >= fileSzNeeded) return idx;
        }
        return -1;
    }

    /**
     * Move 1 file
     * @param blkIn
     * @param from
     * @param to
     * @param cnt
     */
    private static void moveFile(Block[] blkIn, int from, int to, int cnt){
        blkIn[to].addFileID(blkIn[from].blockID(), 1);
        blkIn[from].clearFileID();
    }

    /**
     * Move all files
     * @param blkIn
     * @param from
     * @param to
     * @param cnt
     */
    private static void moveAllFile(Block[] blkIn, int from, int to, int cnt){
        blkIn[to].addFileID(blkIn[from].blockID(), cnt);
        blkIn[from].clearAllFileID();
    }

    /**
     * Calc checksum by adding the file original ID with its' present
     * disk position.
     * @param blkIn
     * @return
     */
    private static long calcChksum(Block[] blkIn){
        long chksum = 0;
        int fileIdx = 0;
        for(Block blk : blkIn){
            chksum += blk.getBlkChkSum(fileIdx);
            fileIdx += blk.totSz();
        }
        return chksum;
    }

    /**
     * Print the blocks for troubleshooting.
     * @param blkIn
     */
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