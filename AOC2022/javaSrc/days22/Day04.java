package days22;

import java.io.IOException;

import type.T_Areas;
import util.ReadInput;

public class Day04 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day04(){}

    public static void update() throws IOException {
        String fNum = "04";     //Part1-644   Part2-926
        // String fNum = "041";    //Part1-2     Part2-4
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 4
        len = fileInfo.length;          //Length of input array
        T_Areas[] elfGrp = new T_Areas[len];
        for(int i = 0; i < len; i++) elfGrp[i] = new T_Areas(fileInfo[i]);

        question1(elfGrp);  //Confirmed: 04-644   041-2
        question2(elfGrp);  //Confirmed: 04-926   041-4
    }

    /**
     * Question 1: Find how many Elf groups have areas defined that are within the other.
     */
    private static void question1(T_Areas[] eGrp) {
        int within = 0;
        for(T_Areas g: eGrp) if(chkWithin(g)) within++;
        //Track ,  Confirmed: 04-644   041-2
        System.out.println("\nPart 1: Group count within the other: " + within);
    }

    /**
     * Check for area within the other.
     * @param grp
     * @return true if one is within the other
     */
    private static boolean chkWithin(T_Areas grp){
        boolean chk = false;
        //Chk A in B  23 / 1234
        chk = (grp.elfA[0] >= grp.elfB[0] && grp.elfA[1] <= grp.elfB[1]);
        //OR Chk B in A  1234 / 23
        chk |= (grp.elfB[0] >= grp.elfA[0] && grp.elfB[1] <= grp.elfA[1]);
        return chk;
    }
    
    /**
     * Question 2: Find how many Elf groups have areas defined that overlap each other.
     */
    private static void question2(T_Areas[] eGrp) {
        int ovrLap = 0;
        for(T_Areas g: eGrp) if(chkOvrLap(g)) ovrLap++;
        //Track 961 hi,  Confirmed: 04-926   041-2
        System.out.println("\nPart 2: Group count with overlap: " + ovrLap);
    }

    /**
     * Check for overlap
     * @param grp
     * @return boolean true if overlapped.
     */
    private static boolean chkOvrLap(T_Areas grp){
        boolean chk = false;
        //Chk A in B  45 / 456(lo2lo) 34 / 456, (Hi2Lo) 67 / 456 (Lo2Hi), 56 / 456 (Hi2Hi)
        chk =  (grp.elfA[0] >= grp.elfB[0] && grp.elfA[0] <= grp.elfB[1]);
        chk |= (grp.elfA[1] >= grp.elfB[0] && grp.elfA[1] <= grp.elfB[1]);
        //OR Chk B in A
        chk |= (grp.elfB[0] >= grp.elfA[0] && grp.elfB[0] <= grp.elfA[1]);
        chk |= (grp.elfB[1] >= grp.elfA[0] && grp.elfB[1] <= grp.elfA[1]);
        return chk;
    }
    
}