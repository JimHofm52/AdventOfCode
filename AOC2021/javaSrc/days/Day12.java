package days;

import java.io.IOException;

import util.*;

public class Day12 {

    public static void update() throws IOException {
        int fileNum = 12;       //File number of input
        String dirIn[] = ReadInput.GetInputStr(fileNum);// Get input in an array for 12(121)
        int len = dirIn.length; // Length of input array
        // ---- Part 1 ------
        char hdg = 'E'; // Start heading is East
        int coorNE[][] = { { 0, 0 }, { 0, 1 } }; // [0=ship,1=waypt][0=N,1=E]
        hdg = EvalIn(dirIn, hdg, coorNE, true);
        int a = (Math.abs(coorNE[0][0]) + Math.abs(coorNE[0][1]));
        System.out.println("\nPart 1 Manhattan adr - " + a); // Confirmed - Part 1 Manhattan adr - 319(25)

        // ----- Part 2 ------
        dirIn = ReadInput.GetInputStr(fileNum); // Get input in an array for 12(121)
        hdg = 'E'; // Start heading is East
        coorNE = new int[][] { { 0, 0 }, { 1, 10 } }; // [0=ship,1=waypt][0=N,1=E]
        hdg = EvalIn(dirIn, hdg, coorNE, false);
        a = (Math.abs(coorNE[0][0]) + Math.abs(coorNE[0][1]));
        System.out.println("\nPart 2 Manhattan adr - " + a); // Confirmed - Part 2 Manhattan adr - 50157(286)
    }

    // ----------------------------------- Part 1 & 2 ----------------------------------

    /**
     * Step thru all cmds and associated values
     * 
     * @param inData
     * @param hdg
     * @param coorNE
     * @param part1
     * @return
     */
    private static char EvalIn(String inData[], char hdg, int coorNE[][], boolean part1) {
        char cmd = 'F'; //NESW-Move ship or waypt, F-Fwd ship, RL=Rotate Right/Left ship
        int val = 0;    //Value to Move, go Fwd or Rotate.
        int shpwpt = part1 ? 0 : 1; //Move ship, coorNE[0], else waypt coorNE[1]
        ;
        for (int i = 0; i < inData.length; i++) {
            cmd = inData[i].charAt(0);
            val = Integer.parseInt(inData[i].substring(1));
            switch (cmd) {
                case 'N':
                    coorNE[shpwpt][0] += val; break;
                case 'E':
                    coorNE[shpwpt][1] += val; break;
                case 'S':
                    coorNE[shpwpt][0] -= val; break;
                case 'W':
                    coorNE[shpwpt][1] -= val; break;
                case 'F':
                    coorNE[0][0] += val * coorNE[1][0];
                    coorNE[0][1] += val * coorNE[1][1];
                    break;
                case 'R':
                    hdg = EvalRot(hdg, val, coorNE[1]); break;
                case 'L':
                    hdg = EvalRot(hdg, -val, coorNE[1]); break;
                default:
                    System.out.println("Error for parcing \"" + inData[i] + "\" line " + i);
            }
        }
        return hdg;
    }

    /**
     * Rotate the heading of the ship and translaye the waypoint.
     * 
     * @param hdg
     * @param rot
     * @param wayptNE
     * @return
     */
    private static char EvalRot(char hdg, int rot, final int wayptNE[]) {
        int iTmp = 0;
        boolean right = rot > 0;    //If +rot=Right, -rot=Left rotation.
        do {                        //Rotate ship...
            switch (hdg) {  //Chg ship hdg indicator
                case 'N':
                    hdg = right ? 'E' : 'W'; break;
                case 'E':
                    hdg = right ? 'S' : 'N'; break;
                case 'S':
                    hdg = right ? 'W' : 'E'; break;
                case 'W':
                    hdg = right ? 'N' : 'S'; break;
                default:
                    System.out.println("Error for EvalHdg \"" + hdg + "\"");
            }
            // Translate waypt by +/-90
            iTmp = wayptNE[0]; // save N wp
            wayptNE[0] = right ? -wayptNE[1] : wayptNE[1]; // move E to N, -E if Right
            wayptNE[1] = right ? iTmp : -iTmp; // move N to E, -N if Left

            rot += right ? -90 : 90;    //Reduce by 90
        } while (rot != 0);             //... until 0
        return hdg;
    }
}