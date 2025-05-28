package type;

public class Block {
    private static int blockIDCntr = 0;
    private int blockID;
    private int[] fileID;
    private int orgFileSz;
    private int fileSz;
    private int freeSpSz;
    private int totBlkSz;

    /**
     * Constructor for a disk block.  Will be assigned an incremental BlockID 
     * in the order created.
     * @param fileSize number of files to add with the blockID
     * @param freeSpaceSize free spaces for other fles.
     */
    public Block(int fileSize, int freeSpaceSize){
        fileSz = fileSize;
        orgFileSz = fileSize;
        freeSpSz = freeSpaceSize;
        totBlkSz = fileSz + freeSpSz;
        fileID = new int[fileSz + freeSpSz];
        int i = 0;
        for( ; i < fileSize; i++) fileID[i] = blockIDCntr;
        for( ; i < totBlkSz; i++) fileID[i] = -1;
        blockID = blockIDCntr++;
    }

    /** @return the free space size */
    public int freeSpSz(){ return freeSpSz; }
    /** @return the free file size (count) */
    public int fileSz(){ return fileSz; }
    /** @return the free file size (count) of original files from mapping. */
    public int orgFileSz(){ return orgFileSz; }
    /** @return the total space size, file + free */
    public int totSz(){ return totBlkSz; }
    /** @return the block ID */
    public int blockID(){ return blockID; }
    /** @return the original file ID */
    public int fileID(int idx){ return fileID[idx]; }
    /** @return the array of all the file IDs in this block. */
    public int[] fileIDs(){ return fileID; }


    /**
     * Add multiple files to this block using the fileID passed
     * @param newFileID fileID to add
     * @param addCnt number of files to add
     * @return false if free space is less then requested add.
     */
    public boolean addFileID(int newFileID, int addCnt){
        if(freeSpSz < addCnt) return false;

        for(int i = 0; i < addCnt; i++){
            fileID[fileSz] = newFileID;
            fileSz++;
            freeSpSz--;
        }
        return true;
    }

    /**
     * Set the file ID for the index passed.
     * @param idx
     * @param newFileID
     */
    public void setFileID(int idx, int newFileID){
        fileID[idx] = newFileID;
        fileSz++;
        freeSpSz--;
    }

    /** Clear the last file in arrary, set to -1. */
    public void clearFileID(){
        fileID[fileSz - 1] = -1;
        fileSz--;
        freeSpSz++;
    }

    /** Clear all the fileIDs that were part of the original mapping. */
    public void clearAllFileID(){
        for(int i = 0; i < orgFileSz; i++){
            fileID[i] = -1;
            fileSz--;
            freeSpSz++;
        }
    }

    /**
     * Calc chekcsum for this block using the starting file position.
     * Add the file position multipled by the file ID.
     * @param startFilePosition
     * @return
     */
    public long getBlkChkSum(int startFilePosition){
        long chksum = 0;
        for(int i = 0; i < totBlkSz; i++){
            chksum += fileID[i] > -1 ? fileID[i] * (startFilePosition + i) : 0;
        }
        return chksum;
    }

    /**
     * Set the blockID cnounter to 0.  Needed to run mapping a second time.
     */
    public static void resetBlockIDCntr(){
        blockIDCntr = 0;
    }
}
