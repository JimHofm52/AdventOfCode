package days;

import java.io.IOException;
import java.util.Arrays;

import util.*;

public class Day11 {

    public static void update() throws IOException {
        int fileNum = 11;   //File number to use
        String prstSeats[] = ReadInput.GetInputStr(fileNum);//Get input in an array for 11(111)
        int lenRow = prstSeats.length;                 //Length of input array
        int lenCol = prstSeats[0].length();
        int a = 0;
        //---- Part 1 ------
        int seatPass = 1;
        while(!ChgSeats(prstSeats, true)){
            seatPass++;
        }   //Keep changing seats until no seat changes
        a = CntAllOccd(prstSeats);    //Confirmed - Seat cnt after 80(6) passes is - 2247(37)
        System.out.println("\nPart 1 seat cnt after " + seatPass + " passes is - " + a);

        //----- Part 2 ------
        prstSeats = ReadInput.GetInputStr(fileNum);//Get input in an array for 10(101)
        seatPass = 0;
        while(!ChgSeats(prstSeats, false)){
            // System.out.println();
            // for(String s : prstSeats) System.out.println(s);
            seatPass++;
        }   //Keep changing seats until no seat changes
        a = CntAllOccd(prstSeats);    //Confirmed - Seat cnt after 84(6) passes is - 2011(26)
        System.out.println("\nPart 2 seat cnt after " + seatPass + " passes is - " + a);
    }

    /**
     * Calc new seat change
     * 
     * @param prstSeats
     * @param part1
     * @return
     */
    private static boolean ChgSeats(String prstSeats[], boolean part1){
        String copySeats[] = new String[prstSeats.length]; //copy for testing
        copySeats = AryUtil.CopyStr(prstSeats);
        for(int r = 0; r < prstSeats.length; r++ ){
            AnalRules(prstSeats, copySeats, r, part1);
        }
        return CmprNoChg(prstSeats, copySeats);
    }

    /**
     * Analyze rules for seats
     * 
     * @param prstSeats
     * @param copySeats
     * @param r
     * @param part1
     */
    private static void AnalRules(String prstSeats[], String copySeats[], int r, boolean part1){
        char stat = '.';   //0 unocc, 1 occ, 2 flr
        int cnt = 0;    //cnt of adj occ seats
        for(int c = 0; c < prstSeats[0].length(); c++ ){
            stat = (prstSeats[r].charAt(c));         //Get prst status
            if(stat != '.'){      //Not floor then cnt adj (part1) or inline (part2) occd seats
                cnt = part1 ? CntAdjOcc(copySeats, r, c) : CntOpnOcc(copySeats, r, c);
                if(cnt > 4 ) stat = 'L';     //If occupied & 4(5) or more adj(inline) are occupied then empty
                if(cnt == 0 ) stat = '#';    //If empty & no occupied seats adjacent then occ
            }
            String tmp = prstSeats[r].substring(0, c) + stat + prstSeats[r].substring(c+1);
            prstSeats[r] = tmp;
        }
    }

    /**
     * Count adjacent occupied seats for part 1
     * 
     * @param copySeats
     * @param r
     * @param c
     * @return
     */
    private static int CntAdjOcc(String copySeats[], int r, int c){
        int cnt = 0;    //Count if Occupied(#) for 8 space aroung seat
        for(int ri = r-1; ri < r+2; ri++){
            if(ri >= 0 && ri < copySeats.length){
                for(int ci = c-1; ci < c+2; ci++){
                    if(ci >= 0 && ci < copySeats[0].length()){
                        if((copySeats[ri].charAt(ci) == '#')) cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    /**
     * Compare present and previous seating for no change.
     * 
     * @param prstSeats
     * @param copySeats
     * @return
     */
    private static boolean CmprNoChg(String prstSeats[], String copySeats[]){
        for(int r = 0; r < prstSeats.length; r++ ){
            if(prstSeats[r].compareTo(copySeats[r]) != 0) return false;
        }
        return true;    //No chg since last pass
    }

    /**
     * Count all occupied seats
     * 
     * @param prstSeats
     * @return
     */
    private static int CntAllOccd(String prstSeats[]){
        int occSeat = 0;
        for(int r = 0; r < prstSeats.length; r++ ){
            for(int c = 0; c < prstSeats[0].length(); c++ ) {
                if(prstSeats[r].charAt(c) == '#') occSeat++;
            }
        }
        return occSeat;
    }

    //---------------------------- Part 2 --------------------------------------
    /**
     * Count occupied olong line of site from seats.
     * 
     * @param copySeats
     * @param r
     * @param c
     * @return
     */
    private static int CntOpnOcc(String copySeats[], int r, int c){
        int cntOcc = 0;         //Count if Occupied(1)

        cntOcc += CntAlong(copySeats, r, c, 1, 0);    //Row/Col Up r+ c_ 
        cntOcc += CntAlong(copySeats, r, c, -1, 0);   //Row/Col Dn r- c_ 
        cntOcc += CntAlong(copySeats, r, c, 0, 1);    //Row/Col Right r_ c+ 
        cntOcc += CntAlong(copySeats, r, c, 0, -1);   //Row/Col Left r_ c- 
        cntOcc += CntAlong(copySeats, r, c, 1, 1);    //Row/Col UpRight r+ c+ 
        cntOcc += CntAlong(copySeats, r, c, -1, 1);   //Row/Col DnRight r- c+ 
        cntOcc += CntAlong(copySeats, r, c, 1, -1);   //Row/Col UpLeft r+ c- 
        cntOcc += CntAlong(copySeats, r, c, -1, -1);  //Row/Col DnLeft r- c-

        return cntOcc;
    }

    /**
     * Count along line of sight for a seat.
     * 
     * @param copySeats
     * @param r
     * @param c
     * @param rDir
     * @param cDir
     * @return
     */
    private static int CntAlong(String copySeats[], int r, int c, int rDir, int cDir){
        int rLen = copySeats.length, cLen = copySeats[0].length();    //Row/Col len
        int cntOcc = 0;
        int rp = rDir, cp = cDir;

        while(r + rp < rLen && c + cp < cLen && r + rp > -1 && c + cp > -1){  //while in range
            if(copySeats[r + rp].charAt(c + cp) == 'L') break; //Found unocc stop
            if(copySeats[r + rp].charAt(c + cp) == '#'){       //Found Occ add and stop
                cntOcc++;
                break;
            }
            rp += rDir; cp += cDir; 
        }
        return cntOcc;
    }
}