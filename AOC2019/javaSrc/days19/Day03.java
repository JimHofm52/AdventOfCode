package days19;

import java.io.IOException;

import util.AryUtil;
import util.ReadInput;

public class Day03 {
    private static String[] fileInfo;   //Define array for input type data
    private static int len;             //Length of data

    /**Constructor, not needed but used for standards. */
    private Day03(){}

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputStr("03");   //Get input in an array for 3
        len = fileInfo.length;                    //Length of input array
        int[][] wire1, wire2;
        wire1 = parceWire2XY(fileInfo[0]);  //Wire 1 as XY coor
        wire2 = parceWire2XY(fileInfo[1]);  //Wire 2

        int[][] wXOvr;  //W1 & W2 idx's at intersection.  W1 * 1000 + W2
        wXOvr = question1(wire1, wire2);     //Confirmed: 03-260.  031-6, 032-159, 033-135
        question2(wXOvr, wire1, wire2);      //Confirmed: 03-15612  031-30, 032-610, 033-410
    }

    /**
     * Question 1: Find where the wires ccross.
     */
    private static int[][] question1(int[][] w1XY, int[][] w2XY) {
        int tmpI;
        int manhatAdrMin = 32000;  //Initialize with a large number.
        int[] wAxis = new int[2];   int[] wSegLoc = new int[2];
        int[] wLo = new int[2];     int[] wHi = new int[2];
        int[][] xOvr = {{-1, -1, 0, 0}};   //Indexes of wires & XY coor at XOvers

        for(int idx1 = 1; idx1 < w1XY.length; idx1++){
            getWireInfo(idx1, w1XY, 0, wAxis, wSegLoc, wLo, wHi);  //w1/2, axis horz(0) vert(1), segment len, Lo, Hi end pts
            for(int idx2 = 1; idx2 < w2XY.length; idx2++){
                getWireInfo(idx2, w2XY, 1, wAxis, wSegLoc, wLo, wHi);  //w1/2, axis horz(0) vert(1), segment len, Lo, Hi end pts
                if(wAxis[0] != wAxis[1]){ //A wire is horz(0) or vert(1) and the other is opposite.
                    //Chk if wire 2 is between wire 1 end pts AND wire 1 is between wire 2 end pts.
                    if((wSegLoc[1] > wLo[0] && wSegLoc[1] < wHi[0]) && (wSegLoc[0] > wLo[1] && wSegLoc[0] < wHi[1])){
                        tmpI = Math.abs(wSegLoc[0]) + Math.abs(wSegLoc[1]); //Calc Manhatten Adr from Origin
                        if(tmpI < manhatAdrMin) manhatAdrMin = tmpI;        //Update if less then existing.
    
                        if(xOvr[0][0] > 0){
                            xOvr = AryUtil.copyInt2By(xOvr, xOvr.length + 1);
                        }
                        xOvr[xOvr.length - 1][0] = idx1;    //XOver index of of wire 1
                        xOvr[xOvr.length - 1][1] = idx2;    //XOver index of of wire 2
                                                            //Capture the XY coor of the XOver.
                        xOvr[xOvr.length - 1][2] = (wAxis[0] == 0) ? wSegLoc[0] : wSegLoc[1];
                        xOvr[xOvr.length - 1][3] = (wAxis[1] == 1) ? wSegLoc[1] : wSegLoc[0];
                    }
                }
            }
        }
        //Track ,  Confirmed 260.  031-6, 032-159, 033-135
        System.out.println("\nQ1: Manhatten Adr: " + manhatAdrMin);
        return xOvr;
    }

    /**
     * [w1/2][0=axis, 1=line, 2=Lo, 3=Hi]
     * @param idx
     * @param wire
     * @param wInfo
     */
    private static void getWireInfo(int idx, int[][] wire, int wNum,
                        int[] wAxis, int[] wSegLoc, int[] wLo, int[] wHi){
        int tmpI;
        wAxis[wNum] = wire[idx][0] - wire[idx - 1][0] == 0 ? 0 : 1; //Wire 1 horz(0) or vert(1)
        wSegLoc[wNum] = wire[idx][wAxis[wNum]];             //Wire segment horz/vert position
        tmpI = wAxis[wNum] == 0 ? 1 : 0;                    //Other axis
        wLo[wNum] = wire[idx][tmpI];       //Wire Lo value end point
        wHi[wNum] = wire[idx - 1][tmpI];   //Wire Hi value end point
        if(wLo[wNum] > wHi[wNum]){ tmpI = wLo[wNum]; wLo[wNum] = wHi[wNum]; wHi[wNum] = tmpI;}  //Swap Lo/Hi values if needed
    }

    /*
     * Question 2: Find the shortist COMBINED path to ANY intersection.
     * We need to find ALL intersections then go back and count distances
     * for each wire to each intersection.
     * <p>Maybe count intersections in part 1, save and create an array for all 
     * or expand an array(booo) and add wire 2 values on first pass then 
     * go back and add wire 1.
     * <p>Things we need to eval.  idx of w1 & w2 at intersection.
     * <p>Expanding an array is 1 line, 2d is a PITA.  Maybe use mux 1000 * w1 + w2?
     * <p>
     * <p>OK, in part 1 I save the indexs of wires 1 & 2 xOvers.
     * <p>Also, in part 1, I captured the XY coor of the XOver.
     */
    /**
     * Question 2: Find the shortist COMBINED path to ANY intersection.
     * @param xOvr Passed info collected in part 1 about any XOver [0]=W1Idx, [1]=w2Idx, [2]=X vakue, [3]=Y value
     * @param w1XY Wire 1 XY coordinates
     * @param w2XY Wire 2 XY coordinates
     */
    private static void question2(int[][] xOvr, int[][] w1XY, int[][] w2XY) {
        int xoCnt = xOvr.length;
        int idx1, idx2;
        int tmp;
        int[] lastXY = new int[2];
        int[][] xoLen = new int[xoCnt][3];    //[0]=w1 len, [1]=w2 len, [2]=tot w1+w2
        for(int xo = 0; xo < xoCnt; xo++){
            lastXY[0] = xOvr[xo][2];
            lastXY[1] = xOvr[xo][3];

            tmp = 0;
            idx1 = xOvr[xo][0];     //W1 index before XOver
            for(int idx = 1; idx < idx1; idx++){
                tmp += calcDiff(w1XY[idx], w1XY[idx - 1]);  //Cnt steps in each full segment
            }
            tmp += calcDiff(w1XY[idx1 - 1], lastXY);    //Cnt partial segment to XOver
            xoLen[xo][0] = tmp; //Save wire 1 Steps

            tmp = 0;
            idx2 = xOvr[xo][1];     //W2 index before XOver
            for(int idx = 1; idx < idx2; idx++){
                tmp += calcDiff(w2XY[idx], w2XY[idx - 1]);
            }
            tmp += calcDiff(w2XY[idx2 - 1], lastXY);
            xoLen[xo][1] = tmp; //Save wire 2 steps
            xoLen[xo][2] = xoLen[xo][0] + tmp;  //Save total
        }
        tmp = 99999;
        for(int[] i : xoLen) if(i[2] < tmp) tmp = i[2]; //Find minimum steps
            
        //Track ,  Confirmed: 15612  031-30, 032-610, 033-410
        System.out.println("\nQ2: Min sum of wires 1 & 2 value: " + tmp + "\n");
    }

    /**
     * Calc c1 - c2.
     * @param c1 XY coor
     * @param c2 XY coor
     * @return absolute steps
     */
    private static int calcDiff(int[] c1, int[] c2){
        int tmp = 0;
        tmp = c1[0] - c2[0];    //One of these is 0
        tmp += c1[1] - c2[1];   //other has value we want
        tmp = Math.abs(tmp);
        return tmp;
    }

    /**
     * Parce wire path into XY coors. L/D neg. distance.
     * @param wire wire input L/R=Left/Right, D/U=Down/UP.  -/+.
     * @return 2d array of coors, XY.
     */
    private static int[][] parceWire2XY(String wire){
        int wireTurns = 2;  //Count wire turns, input, (commas) + 1 origin + 1 last one.
        for(int i = 0; i < wire.length(); i++) if(wire.charAt(i) == ',') wireTurns++;
        int[][] coor = new int[wireTurns][2];   //X (L/R), Y (U/D).

        int[] xyCoor = new int[2];
        int endS;
        for(int i = 1; i < wireTurns - 1; i++){
            endS = wire.indexOf(',');
            applyDirection(wire.substring(0, endS), xyCoor);
            wire = wire.substring(endS + 1);
            coor[i][0] = xyCoor[0];
            coor[i][1] = xyCoor[1];
        }
        applyDirection(wire, xyCoor);   //Do last input.
        coor[wireTurns - 1][0] = xyCoor[0];
        coor[wireTurns - 1][1] = xyCoor[1];
    return coor;
    }

    /**
     * Applies sign to the move direction, Lxx is Left(X axis) -xx distance.
     * Rxx is Right(Xaxis) +xx, Dxx is Down(Y axis) -xx, & Uxx is Up(Y axis) +xx.
     * <p>Since we move at right angles we only need to add to
     * [0]horz(X axis) or [1]vert(Y axis).
     * @param move Value to evaluate.  Dxx or Lxx or Uxx or Rxx
     * @param xyCoor to return value in 2 elemant array
     */
    private static void applyDirection(String move, int[] xyCoor){
        int tmpI = Integer.parseInt(move.substring(1));
        switch(move.charAt(0)){
            case 'L':
            xyCoor[0] -= tmpI;
            break;
            case 'R':
            xyCoor[0] += tmpI;
            break;
            case 'D':
            xyCoor[1] -= tmpI;
            break;
            case 'U':
            xyCoor[1] += tmpI;
            break;
        }
    }

    //----------------- Shtuff I created but didm't want to throw away, yet.  -----------
    /**
     * Chk end point pair for horz(0) or vert(1) or the same point(-98)
     * or at at an angle(-99) (not 90 deg) or unknown(--100)
     * @param idx
     * @param wire
     * @return
     */
    private static int chkPair(int idx, int[][]wire){
        int chkPair =  (wire[idx][0] - wire[idx][0] - 1) == 0 ? 1 :
                       (wire[idx][1] - wire[idx][1] - 1) == 0 ? 2 : 0;
        switch(chkPair){
            case 0:     //No difference, the same point.
            System.out.println("Wire point the same for idx & idx - 1" + idx);
            return -98; //Same points.  Chk or should throw index error
            case 1:     //X different, Y the same
            return 0;   //Found Horz line, need to find Vert.
            case 2:     //X the same, Y different
            return 1;   //Found Vert line, need to find Horz.
            case 3:     //Neither the same.  Points are at an angle
            System.out.println("Wire points at angle for idx & idx - 1" + idx);
            return -99; //Angle points.  Chk or should throw index error
            default:
            System.out.println("Wire points error unknown" + idx);
            return -100;    //Unkown.  Chk or should throw index error
        }
    }

    private static void swap(int[] c1, int[] c2){
        int tmp;
        tmp = c1[0];  c1[0] = c2[0];  c2[0] = tmp;
        tmp = c1[1];  c1[1] = c2[1];  c2[1] = tmp;
    }
}