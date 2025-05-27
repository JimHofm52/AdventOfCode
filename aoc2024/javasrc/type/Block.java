package type;

public class Block {
    private static int blockIDCntr = 0;
    private int blockID;
    private int[] fileID;
    private int fileSz;
    private int freeSpSz;
    private int totBlkSz;

    public Block(int fileSize, int freeSpaceSize){
        fileSz = fileSize;
        freeSpSz = freeSpaceSize;
        totBlkSz = fileSz + freeSpSz;
        fileID = new int[fileSz + freeSpSz];
        int i = 0;
        for( ; i < fileSize; i++) fileID[i] = blockIDCntr;
        for( ; i < totBlkSz; i++) fileID[i] = -1;
        blockID = blockIDCntr++;
    }

    public int freeSpSz(){ return freeSpSz; }
    public int fileSz(){ return fileSz; }
    public int totSz(){ return totBlkSz; }
    public int blockID(){ return blockID; }
    public int fileID(int idx){ return fileID[idx]; }

    public boolean addFileID(int newFileID){
        if(freeSpSz == 0) return false;

        fileID[fileSz] = newFileID;
        fileSz++;
        freeSpSz--;
        return true;
    }

    public void setFileID(int idx, int newFileID){
        fileID[idx] = newFileID;
        fileSz++;
        freeSpSz--;
    }

    public void clearFileID(){
        fileID[fileSz - 1] = -1;
        fileSz--;
        freeSpSz++;
    }

    public int[] fileIDs(){
        return fileID;
    }

    public long getBlkChkSum(int startFilePosition){
        long chksum = 0;
        for(int i = 0; i < totBlkSz; i++){
            chksum += fileID[i] > -1 ? fileID[i] * (startFilePosition + i) : 0;
        }
        return chksum;
    }
}
