package days19;

import java.io.IOException;
import util.ReadInput;

public class Day06 {
    private static String fileInfo[];
    private static int len;

    /**Constructor, not needed but used for standards. */
    public Day06(){}

    /** Type Orbit */
    public class TOrbit{
        public String ctr;
        public String orb;
        public int cnt;
        
        public TOrbit(){
            this.ctr = "z";
            this.orb = "z";
            this.cnt = 0;
        }

        public void init(){
            this.ctr = "z";
            this.orb = "z";
            this.cnt = 0;
        }
    }

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputStr("061");   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        Orbit[] orbs = parceInput(fileInfo);
        question1(orbs);
    }

    /**
     * Question 1: Count direct & indirect orbits
     */
    private static void question1(Orbit[] o) {
        String srch = "COM";    //Start w/ Center of Mass, COM
        int ctrIdx;
        boolean done = false;
        do{
            cntOrbits(o, srch);     //Cnt orbits for orb then it's orbs then ...
            ctrIdx = findCtr(o);
            done = ctrIdx <= 0;
            if(!done){
                srch = o[ctrIdx].ctr;
            }
        }while(!done);

        // ctrIdx = findCtr(o);
        // srch = o[ctrIdx].ctr;
        // val = o[findOrb(o, srch)].cnt;
        // do{
        //     srch = o[ctrIdx].orb;
        //     o[ctrIdx].cnt = val;
        //     val++;
        //     ctrIdx = findOrb(o, srch);
        // }while(ctrIdx > 0);
        
        // o[ctrIdx].cnt = val;

        int a = 0;
        //Track ,  Confirmed: 04-931
        // System.out.println("\nPart 1: Number of possible PWs: " + pwOKCnt);
    }
    
    /**
     * Count orbit cnts for the orb around this center.
     * @param o Orbit array of centers, orbs & count of orbits
     * @param srchCtr center designation,string.
     */
    private static void cntOrbits(Orbit[] o, String srchCtr){
        int ctrIdx = findOrb(o, srchCtr);
        int val = srchCtr.equals("COM") ? 1 : findCtr(o);  //
        do{
            srchCtr = o[ctrIdx].orb;
            o[ctrIdx].cnt = val;
            val++;
            ctrIdx = findOrb(o, srchCtr);
        }while(ctrIdx > 0);
    }

    /**
     * Check for next uncounted orbit.
     * @param inStr Orbits.
     * @return The idx of the first Orbit cnt < 0, not counted.  
     * Rtn -1 if all have been counted.
     */
    private static int findCtr(Orbit[] inStr){
        for(int i = 0; i < inStr.length; i++){
            if(inStr[i].cnt < 0) return i;
        }
        return -1;
    }

    /**Find the index for the Center of Mass, COM.  Starting point.
     * @param inStr, array of direct orbits. IE, COM orbited by B (direct orbit count = 1)
     * or B orbited by C (dirct count = 1 plus C indirectly orbits COM, indirect count = 1)
     * therefore it's orbit count = 2.
     */
    private static int findOrb(Orbit[] inStr, String srch){
        for(int i = 0; i < inStr.length; i++){
            if(inStr[i].ctr.equals(srch)) return i;
        }
        return -1;
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 04-609
        // System.out.println("\nPart 2: Number of possible PWs: " + pwOKCnt);
    }

    private static Orbit[] parceInput(String[] inStr){
        int len = inStr.length;
        String ctr;
        String orb;
        int sepIdx;

        Orbit[] orbitFnd = new Orbit[len];
        for(int i = 0; i < len; i++){
            orbitFnd[i] = new Orbit();
        }

        for(int idx = 0; idx < len; idx++){
            sepIdx = inStr[idx].indexOf(')');
            ctr = inStr[idx].substring(0, sepIdx);
            orb = inStr[idx].substring(sepIdx + 1);
            orbitFnd[idx].ctr = ctr;
            orbitFnd[idx].orb = orb;
        }
        return orbitFnd;
    }

    private static void sortOrbit(Orbit[] obt){
        String srch = "COM";    //The start
        int ctrIdx = findCtr(obt);
        boolean allFnd = false;
        do{
            
        }while(!allFnd);
    }

}