package days;

import java.io.IOException;

import util.*;

public class Day13 {

    public static void update() throws IOException {
        int fileNum = 13;       //File number of input
        String readIn[] = ReadInput.GetInputStr(fileNum);// Get input in an array for 13(131)
        int dTime = Integer.parseInt(readIn[0]);
        int schd[][] = ParceIn(readIn[1]);    //Schdules
        int len = schd.length;              // Length of input schd
        int a = 0;
        // ---- Part 1 ------
        int ans[] = new int[2];
        FindClosest(dTime, schd[0], ans);
        a = schd[0][ans[0]] * ans[1];
        System.out.println("\nPart 1 - " + a); // Confirmed - Part 1 Manhattan adr - 2095(295)

        // ----- Part 2 ------
        long time = MinMatch(schd);
        System.out.println("\nPart 2 Time - " + time); // Confirmed - Part 2 Time - 598411311431841(1068781)
    }

    // ----------------------------------- Part 1 & 2 ----------------------------------

    private static int[][] ParceIn(String readIn){
        int len = 0;    // Length of input schd
        for(int i = 0; i < readIn.length(); i++) if(readIn.charAt(i) == ',') len++;
        len++;
        int schd[] = new int[len];
        String str;
        int beg = 0, end = 0;
        for(int i = 0; i < len; i++){
            end = readIn.indexOf(',', end);
            if(end == -1) end = readIn.length();
            str = readIn.substring(beg, end).trim();
            if(!str.equals("x")){
                schd[i] = Integer.parseInt(str);
            }
            beg = ++end;
        }
        // return schd;
        // Compress schd array but preserve schd ndx (leave time),  "Should" speed up search.
        int len2 = 0;
        for(int i : schd) if(i > 0) len2++;
        int schd2[][] = new int[2][len2];
        int busNdx = 0;
        for(int i = 0; i < len; i++){
            if( schd[i] > 1){
                schd2[0][busNdx] = schd[i];
                schd2[1][busNdx] = i;
                busNdx++;
            }
        }
        return schd2;
    }

    // ----------------------------------- Part 1 ----------------------------------

    /**
     * Find the departure time of the list of schedule bus number (cycle time) and 
     * bus time offset (time from initial start).  Start with initial departure time ossible.
     * 
     * @param dTime
     * @param schd
     * @param ans
     */
    private static void FindClosest(int dTime, int schd[], int ans[]){
        int len = schd.length;     // Length of input schd
        int maxNdx = 0;
        int psntTmp = 0, minTmp = 1000;
        int i = 0;
        for(; i < len; i++){
            if(schd[i] != 0){
                psntTmp = schd[i] - (dTime % schd[i]);
                if(psntTmp < minTmp){
                    maxNdx = i;
                    minTmp = psntTmp;
                }
            }
        }
        ans[0] = maxNdx;
        ans[1] = minTmp;
    }

    // ----------------------------------- Part 2 ----------------------------------

    /**
     * Find the minimum time that ALL again match start again.
     * Since ALL need to match use the max bus num to increment search.  Then find a starting
     * point that is a 0 modulo of that max from the seed value and subtract maxs' leave time. 
     * Then check all by adding it's leave time and check for 0 modulo of bus number.
     * 
     * @param schd
     * @return
     */
    private static long MinMatch(int schd[][]){
        int mxBusNum = 0;   //Max bus number (cycle time)
        int mxBusLvg = 0;   //Max bus's leaving (offset [index]) time
        for(int i = 0; i < schd[0].length; i++){    //Find max busNum and it's index
            if(mxBusNum < schd[0][i]){
                mxBusNum = schd[0][i];
                mxBusLvg = schd[1][i];
            }
        }

        // long busTime = 1068600L;             // Seed for 131
        // long busTime = 100000000000000L;     // Seed from AoC
        // long busTime = 449345386952322L;     // Lo, 640061773188771L Hi from prv failures
        long busTime = 536447854456581L;        // Seed from last try (failure, exceeded hi)

        busTime = busTime - (busTime % mxBusNum);   //Use seed to find a 0 modulo of the max bus number
        boolean fnd = false;

        do{
            // busTime++;
            busTime += mxBusNum;    //Increment to next max value
            fnd = true;
            for(int i = 0; i < schd[0].length; i++){    //Chk all values.  Subtract max leaving time
                if(!ChkBus(busTime - mxBusLvg, schd[0][i], schd[1][i])) fnd = false;
                if(!fnd) break;     //If no match found, don't bother with the rest.
            }
        }while(!fnd);   //Until ALL are matched
        return busTime - mxBusLvg;  //Earliest time ALL match
    }

    /**
     * Chk if time is a 0 modulo of time + leaving
     * 
     * @param time
     * @param busNum
     * @param busLvg
     * @return
     */
    private static boolean ChkBus(long time, int busNum, int busLvg){
        boolean tmp = (0 == (int)((time + busLvg) % busNum));
        return tmp;
    }

}