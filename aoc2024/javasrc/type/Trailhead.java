package type;

import java.util.Arrays;

public class Trailhead {
    private static int th_IDCntr = 0;
    private int th_ID;
    private int th_rc;
    private int trail;
    private int trailCnt = 0;
    private int[] trailtails;

    /**
     * Constructor for a trailhead.  Will be assigned an incremental BlockID 
     * in the order created.
     * @param fileSize number of files to add with the blockID
     * @param freeSpaceSize free spaces for other fles.
     */
    public Trailhead(int row, int col){
        th_ID = th_IDCntr++;
        th_rc = row * 100 + col;
    }

    public int getRow(){ return th_rc / 100; }
    public int getCol(){ return th_rc % 100; }
    public int getRC(){ return th_rc; }
    public int getTrailCnt(){ return trailtails.length; }

    public void addTrailtail(int[] loc){
        trailtails = addTrailtail(loc[0] * 100 + loc[1], trailtails);
    }

    private static int[] addTrailtail(int rc, int[] tts){
        boolean exists = false;
        if(tts == null){
            tts = new int[] {rc};
        }else{
            for(int tt : tts){
                exists |= tt == rc;
            }
            if(!exists){
                tts = Arrays.copyOf(tts, tts.length + 1);
                tts[tts.length - 1] = rc;
            }
        }
        return tts;
    }

}
