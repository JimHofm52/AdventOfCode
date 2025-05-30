package type;

import java.util.Arrays;

public class Trailhead {
    private static int th_IDCntr = 0;
    private int th_ID;  //Unique ID, JIC
    private int th_rc;  //Compressed TH location, row * 100 + col
    private int[] trailtails;
    private int totTrails = 0;

    /**
     * Constructor for a trailhead.  Will be assigned an incremental TrailheadID 
     * in the order created.
     * @param row Row of TH location.
     * @param col Col of TH location.
     */
    public Trailhead(int row, int col){
        th_ID = th_IDCntr++;
        th_rc = row * 100 + col;
    }

    /** @return decmpressed row */
    public int getRow(){ return th_rc / 100; }
    /** @return decmpressed col */
    public int getCol(){ return th_rc % 100; }
    /**  @return compressed trailhead location, rc */
    public int getRC(){ return th_rc; }
    /**  @return Trailtail count, unique end of trails, NOT trails to TTs */
    public int getTrailCnt(){ return trailtails.length; }
    /** @return Total trails, number of trails to the end of the trail */
    public int getTotTrails(){ return totTrails; }
    /**
     * Set the total trail count to number.
     * @param num
     */
    public void setTotTeails(int num){ totTrails = num; }

    /**
     * Add a trailtail if it doesn't already exists.  Public facing.
     * @param loc
     */
    public void addTrailtail(int[] loc){
        trailtails = addTrailtail(loc[0] * 100 + loc[1], trailtails);
    }

    /**
     * Helper file to add trailtails
     * @param rc
     * @param tts
     * @return
     */
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
