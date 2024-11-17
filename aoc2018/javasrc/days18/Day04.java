package days18;

import java.io.IOException;
import java.util.Arrays;

import type.DTGuard;
import type.GuardInfo;
import util.ReadInput;

public class Day04 {
    private static String fileInfo[];
    private static int len;
    private static DTGuard[] dtGrdData;
    private static GuardInfo[] grdInfo;

    /** Constructor, not needed but used for standards. */
    private Day04(){}

    public static void update() throws IOException {
        String fNum = "04";//Part1- 118599   Part2- 33949
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        dtGrdData = new DTGuard[len];
        for(int i = 0; i < fileInfo.length; i++) dtGrdData[i] = new DTGuard(fileInfo[i]); 
        sortDT();
        countGuards();

        question1();    //Confirmed: 04- 118599   041- 240
        question2();    //Confirmed: 04- 33949    041- 4455
    }

    /**
     * Question 1: Strategy 1: Find the guard that has the most 
     * minutes asleep. What minute does that guard spend asleep the most?
     * 
     * What is the ID of the guard you chose multiplied by the minute 
     * you chose? (In the above example, the answer would be 10 * 24 = 240.)
     */
    private static void question1() {
        int idx = 0;
        int totNapMin = 0;
        findMaxNapTimeAll();
        for(int i = 0; i < grdInfo.length; i++){
            if(grdInfo[i].getTotalNapTime() > totNapMin){
                totNapMin = grdInfo[i].getTotalNapTime();
                idx = i;
            }
        }

        int cnm = grdInfo[idx].findCNM();
        //Track ,  Confirmed: 04- 118599 Lo - 43471  041- 240
        System.out.println("\nPart 1: Guard " + grdInfo[idx].getID()
        /*             */ + " napped a total of " + grdInfo[idx].getTotalNapTime() + " minutes.");
        System.out.println("The common nap minute, CNM, was 00:" + cnm); 
        System.out.println("The answer is: " + grdInfo[idx].getID() * cnm + ". \n");
    }
    
    /**
     * Question 2: Strategy 2: Of all guards, which guard is most 
     * frequently asleep on the same minute?
     * 
     * What is the ID of the guard you chose multiplied by the minute 
     * you chose? (In the above example, the answer would be 99 * 45 = 4455.)
     */
    private static void question2() {
        int idx = 0;
        int totNapDays = 0;
        int tmpCND = 0;
        findMaxNapTimeAll();
        for(int i = 0; i < grdInfo.length; i++){
            tmpCND = grdInfo[i].findCNM_Days();
            if(tmpCND > totNapDays){
                totNapDays = tmpCND;
                idx = i;
            }
        }

        int cnm = grdInfo[idx].findCNM();
        //Track ,  Confirmed: 04- 33949    041- 4455
        System.out.println("\nPart 2: Guard " + grdInfo[idx].getID()
        /*             */ + " napped " + totNapDays + " days ");
        System.out.println("The common nap minute, CNM, was 00:" + cnm); 
        System.out.println("The answer is: " + grdInfo[idx].getID() * cnm + ". \n");
    }

    private static void sortDT(){
        DTGuard tmpDT;
        boolean swapped = false;
        do{
            swapped = false;
            for(int i = 0; i < len - 1; i++){
                if(dtGrdData[i].getDT() > dtGrdData[i + 1].getDT()){
                    tmpDT = dtGrdData[i + 1];
                    dtGrdData[i + 1] = dtGrdData[i];
                    dtGrdData[i] = tmpDT;
                    swapped = true;
                }
            }
        }while(swapped);
    }

    private static void countGuards(){
        int tmpID;
        for(int idx = 0; idx < dtGrdData.length; idx++){
            tmpID = dtGrdData[idx].getID();
            if(tmpID > 0 && chkIsNew(tmpID)){
                grdInfo = addGuard(tmpID, grdInfo);
                int a = 0;
            }
        }
    }

    private static boolean chkIsNew(int grdID){
        if(grdInfo == null){
            grdInfo = new GuardInfo[0];
            return true;
        }else{
            for(GuardInfo gInfo : grdInfo){
                if( gInfo.getID() == grdID) return false;
            }
        }
        return true;
    }

    private static GuardInfo[] addGuard(int idx, GuardInfo[] gi){
        GuardInfo[] tmpGI = Arrays.copyOf(gi, gi.length + 1);
        tmpGI[gi.length] = new GuardInfo(idx);
        return tmpGI;
    }

    private static void findMaxNapTimeAll(){
        int id;
        int begNap = 0;
        int endNap = 0;
        int giIdx = -1;
        for (DTGuard dtg : dtGrdData) {
            if(dtg.getID() > 0){
                id = dtg.getID();
                giIdx = findIdIdx(id);
            }else if(!dtg.getIsAwake()){
                begNap = dtg.getTime();
            }else{
                endNap = dtg.getTime();
                grdInfo[giIdx].addCMN(begNap, endNap);
            }
        }
    }

    private static int findIdIdx(int id){
        for(int i = 0; i < grdInfo.length; i++){
            if(grdInfo[i].getID() == id) return i;
        }
        return -1;
    }
}