package days19;

import java.io.IOException;

import type.Orbit;
import util.ReadInput;

public class Day06 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    public Day06() {
    }

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputStr("06"); // Get input in an array for 1
        len = fileInfo.length; // Length of input array
        Orbit[] orbs = parceInput(fileInfo);
        question1(orbs);
        question2(orbs);
    }

    /**
     * Question 1: Count direct & indirect orbits.
     * <p>COM)B & B)C, C orbits B & both orbit COM, center of mass. 
     */
    private static void question1(Orbit[] orbs) {
        for(int i = 0; i < orbs.length; i++){
            incrCtrCntToCOM(orbs, i);
        }
        //Track, Confirmed: 061-42, 06-314702
        System.out.println("COM orbits: " + orbs[findCtrIdx2(orbs, "COM")].cnt);
    }

    /**
     * Question 2: Count min orbits between YOU & SAN
     */
    private static void question2(Orbit[] orbs) {
        int y = findOrbIdx(orbs, "YOU");
        int s = findOrbIdx(orbs, "SAN");
        for(int i = 0; i < orbs.length; i++) orbs[i].cnt = 0;   //Clear all counts
        incrCtrCntToCOM(orbs, y);   //Count orbits for YOU (1st time is just orbits)
        int intersectIdx = incrCtrCntToCOM(orbs, s);    //Count and return intersection
        // Track , Confirmed: 061-4 , 06-439
        //Subtract 2 for both SAN & YOU.
        System.out.println("\nPart 2: Transfers from YOU to SAN: " + (orbs[intersectIdx].cnt - 4));
    }

    //------------ Parcing input -----------------------------
    /**
     * Parce input ctr)orb, orb orbits the center,ctr.  ctr may obit another down to COM.
     * Grabs the index of the ctr to speed up counting.
     * @param inStr - text of ctr)orb's
     * @return an array of Orbit objects
     */
    private static Orbit[] parceInput(String[] inStr) {
        int len = inStr.length;
        String ctr;
        String orb;
        int sepIdx;

        Orbit[] orbitFnd = new Orbit[len];          //Allocate obbit array
        for (int idx = 0; idx < len; idx++) {
            orbitFnd[idx] = new Orbit();            //initialize array vars
            sepIdx = inStr[idx].indexOf(')');    //Find separator
            ctr = inStr[idx].substring(0, sepIdx);   //Get center of orbit ID
            orb = inStr[idx].substring(sepIdx + 1);             //Get planet ID
            orbitFnd[idx].ctr = ctr;                //Store them
            orbitFnd[idx].orb = orb;
        }
        cmpltCtrIdx(orbitFnd);  //To speed lookup, get index for center of
        return orbitFnd;        //this orbit as the planet of the next.
    }

    /**
     * Complete all the indexs for ctr ref of this orbit as planet of next.
     * @param orb, this orbit
     */
    private static void cmpltCtrIdx(Orbit[] orb){
        for(int i = 0; i < orb.length; i++){
            orb[i].ctrIdx = findOrbIdx(orb, orb[i].ctr);
        }
    }

    /**
     * Find the index for the Center of this orbit, ctr.
     * 
     * @param inStr, array of direct orbits
     * @param ctr, center being obited
     * @return index of center of this orbit as a planet of the next else -1, not found, COM
     */
    private static int findOrbIdx(Orbit[] inStr, String ctr) {
        for (int i = 0; i < inStr.length; i++) {
            if (inStr[i].orb.equals(ctr)) return i;
        }
        return -1;
    }

    /**
     * @param orbs - array of orbits
     * @return the index of COM as the center
     */
    private static int findCtrIdx2(Orbit[] orbs, String srch){
        int i = 0;
        while(!orbs[i++].ctr.equals(srch));
        return i - 1;
    }

    //------------------  Question 1  -----------------------------
    /**
     * Totalize orbits as we move towards COM adding total to existing orbit's count. 
     * @param orbs array of orbits
     * @param cIdx orbit index to starting point
     */
    private static int incrCtrCntToCOM(Orbit[] orbs, int cIdx){
        int subTot = 0;
        int lastZeroIdx = 0;    //For question 2
        while(!orbs[cIdx].ctr.equals("COM")){
            if(orbs[cIdx].cnt != 0 && lastZeroIdx == 0) lastZeroIdx = cIdx;
            orbs[cIdx].cnt += ++subTot;
            cIdx = orbs[cIdx].ctrIdx;
        };
        orbs[cIdx].cnt += ++subTot;
        return lastZeroIdx; //For question 2
    }

    //------------------  Question 2  -----------------------------

}