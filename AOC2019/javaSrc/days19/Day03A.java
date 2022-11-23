package days19;

import java.io.IOException;

import util.AryUtil;
import util.ReadInput;

/**
 * Help the elves find the short between 2 wires.
 * <p>Cleanup of Day03.
 */
public class Day03A {
    private static String[] fileInfo;   //Define array for input type data
    private static int len;             //Length of data

    /**Constructor, not needed but used for standards. */
    private Day03A(){}

    /**
     * Use a Manhatten address (X+Y) to ID a short between 2 wires.
     * @throws IOException - ??
     */
    public static void update() throws IOException {
        fileInfo = ReadInput.getInputStr("03");   //Get input in an array for 3
        len = fileInfo.length;                  //Length of input array
        int[][] w1Nodes, w2Nodes;               //Wire nodes as [][X=0,Y=1,LenToOrg=2]
        w1Nodes = parceWire2XY(fileInfo[0]);    //Wire 1 as XY coor & len to node
        w2Nodes = parceWire2XY(fileInfo[1]);    //Wire 2

        /* Find all XOvers and return all info.  Xover X[0], Y[1] & MAdr ([2]), 
         * the lag(2nd) node indexes for wire 1[3], & 2[4], length to xover of wires 1[5] & 2[6]
         * and the combined wire length to xover. */
        int[][] wXOvr = findXOvrs(w1Nodes, w2Nodes);  //W1 & W2 idx's at intersection.
        
        question1(wXOvr);   //Confirmed: 03-260.  031-6, 032-159, 033-135
        question2(wXOvr);   //Confirmed: 03-15612  031-30, 032-610, 033-410
    }

    /**
     * Question 1: Find the Manhattan distance from the central port
     * to the closest intersection (where the wires cross).
     */
    private static void question1(int[][] xOvrs) {
        int minManhatAdr = 32000;   //Start with a large MAdr.
        //Go thru the list of xOvers and find the smallest MAdr.
        for(int idx = 0; idx < xOvrs.length; idx++){
            if( xOvrs[idx][MADR] < minManhatAdr){
                minManhatAdr = xOvrs[idx][MADR];
            }
        }
        //Track ,  Confirmed 260.  031-6, 032-159, 033-135
        System.out.println("\nQ1: Manhatten Adr: " + minManhatAdr);
    }

    /*
     * Question 2: Find the shortist COMBINED path to ANY intersection.
     * We need to find ALL intersections then go back and count distances
     * for each wire to each intersection.
     * <p>Count intersections in setup, save and create an array for all 
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
     * @param w1XY Wire 1 XY coordinates & wire len to origin
     * @param w2XY Wire 2 XY coordinates & wire len to origin
     */
    private static void question2(int[][] xOvr) {
        int tmp = 99999;    //Start with a large combined wire length
        for(int[] i : xOvr){
            if(i[CWXOL] < tmp){
                tmp = i[CWXOL]; //Find minimum steps
            }
        }

        //Track ,  Confirmed: 15612  031-30, 032-610, 033-410
        System.out.println("\nQ2: Min sum of wires 1 & 2 value: " + tmp + "\n");
    }

    /**
     * Parce wire path into XY coors. U/R pos. & L/D neg. distance.
     * @param wire wire input L/R=Left/Right, D/U=Down/UP.  -/+.
     * @return 2d array of coors & len to origin, X(0), Y(1), Len(2).
     */
    private static int[][] parceWire2XY(String wire){
        int wireTurns = 2;  //Count wire turns, input, (commas) + 1 origin + 1 last one.
        for(int i = 0; i < wire.length(); i++) if(wire.charAt(i) == ',') wireTurns++;
        //Allocate for wire turns. X=[0], Y=[1], len=[2].  node[0] is {0,0}
        int[][] nodes = new int[wireTurns][3];   //X (L/R), Y (U/D). //Allocate wire nodes

        int[] xyCoor = new int[2];  //Arrays init as 0.  Bad form, lol.
        int endStr;
        for(int i = 1; i < wireTurns - 1; i++){
            endStr = wire.indexOf(',');                         //Find next comma
            applyDirection(wire.substring(0, endStr), xyCoor);  //Updates X or Y
            nodes[i][0] = xyCoor[0];    //Save X
            nodes[i][1] = xyCoor[1];    //Save Y
            nodes[i][2] = nodes[i - 1][2] + calcDiff(nodes[i], nodes[i - 1]);   //Total length to node

            wire = wire.substring(endStr + 1);  //Shift to next input string
        }
        applyDirection(wire, xyCoor);   //Do last input.
        nodes[wireTurns - 1][0] = xyCoor[0];
        nodes[wireTurns - 1][1] = xyCoor[1];
        return nodes;
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
        int tmpI = Integer.parseInt(move.substring(1)); //Get distance, not first char.
        switch(move.charAt(0)){     //Apply direction, first char.
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

    // public enum Xovr {NODE1X, NODE1Y, NODE2X, NODE2Y, MADR}
    private static final int X=0, Y=1, MADR=2, W1IDX=3, W2IDX=4, W1XOL=5, W2XOL=6, CWXOL=7;  //XO array

    /**
     * Find all XOvers and save the lag(2nd) node indexes for wire 1 ([0]) & 2 ([1]), 
     * the X ([2]) & Y ([3]) coor of the XOver and the MAdr ([4]) of XOver.
     * @param w1XY - array of XY coor & len for wire1 to origin
     * @param w2XY - array of XY coor & len for wire2 to origin
     * @return - Array of all XOver information.
     * <p>X=[0], Y=[1], MADR=[2], W1IDX=[3], W2IDX=[4], W1XOL=[5], W2XOL=[6], CWXOL=[7]
     */
    private static int[][] findXOvrs(int[][] w1XY, int[][] w2XY){
        int tmpI;
        /**X=[0], Y=[1], MADR=[2], W1IDX=[3], W2IDX=[4], W1XOL=[5], W2XOL=[6], CWXOL=[7] */
        int[][] xOvr = new int[1][8];   //Idx of wires 1&2 at XO, X & Y coor at XO, XO manhat adr, W1&2 Len at XO
        xOvr[0][0] = -1;    //Init for later
        for(int idx1 = 1; idx1 < w1XY.length; idx1++){
            int[] w1Info = getSegInfo(w1XY, idx1);  //wire  axis[0] horz(0) vert(1), segment len[1], Lo[2], Hi[3] end pts
            for(int idx2 = 1; idx2 < w2XY.length; idx2++){
                int[] w2Info = getSegInfo(w2XY, idx2);  //wire  axis[0] horz(0) vert(1), segment len[1], Lo[2], Hi[3] end pts
                if(doWiresCross(w1Info, w2Info)){   //If wires cross
                    if(xOvr[0][0] > 0){ //Increase array AFTER the first time thru.
                        xOvr = AryUtil.copyInt2By(xOvr, xOvr.length + 1);
                    }
                    //Capture ALL info incase we need it later.
                    tmpI = xOvr.length - 1;     //Last array entry
                    xOvr[tmpI][W1IDX] = idx1;   //XOver lag node index of of wire 1
                    xOvr[tmpI][W2IDX] = idx2;   //XOver lag node index of of wire 2
                                                //Capture the XY coor of the XOver.
                    xOvr[tmpI][X] = (w1Info[VERT] == 0) ? w1Info[SLOC] : w2Info[SLOC];
                    xOvr[tmpI][Y] = (w2Info[VERT] == 1) ? w2Info[SLOC] : w1Info[SLOC];
                    //Calc Manhatten Adr from Origin & save
                    xOvr[tmpI][MADR] = Math.abs(xOvr[tmpI][X]) + Math.abs(xOvr[tmpI][Y]);
                    //Calc W1/2 Len to XOvr.  Pseudo code.
                    //if W1 horz
                    //  W1XOL = W1L - abs(W1X - XOY)
                    //  W2XOL = W2L - abs(W1Y - XOX)
                    //else
                    //  W1XOL = W1L - abs(W1Y - XOX)
                    //  W2XOL = W2L - abs(W1X - XOY)
                    if(w1Info[VERT] == 0 ){
                        xOvr[tmpI][W1XOL] = w1XY[idx1][2] - Math.abs(w1Info[HIEPT] - xOvr[tmpI][Y]);
                        xOvr[tmpI][W2XOL] = w2XY[idx2][2] - Math.abs(w2Info[HIEPT] - xOvr[tmpI][X]);
                    }else{
                        xOvr[tmpI][W1XOL] = w1XY[idx1][2] - Math.abs(w1Info[HIEPT] - xOvr[tmpI][X]);
                        xOvr[tmpI][W2XOL] = w2XY[idx2][2] - Math.abs(w2Info[HIEPT] - xOvr[tmpI][Y]);
                    }
                    xOvr[tmpI][CWXOL] = xOvr[tmpI][W1XOL] + xOvr[tmpI][W2XOL];
                }
            }
        }
        return xOvr;
    }

    // private enum WInfo {VERT, SLOC, LOEPT, HIEPT}
    private static final int VERT=0, SLOC=1, LOEPT=2, HIEPT=3;

    /**
     * Get info for a segment of a wire.
     * @param wire array to eval. X[0], Y[1], Len to origin[3]
     * @param idx wire index of 2nd node, fartherest from origin.  Prv node form segment.
     * @return int array - VERT=[0] - horz=0/vert=1, SLOC=[1] - 1d of horz/vert segment, 
     * LOEPT=[2] - Lo endpt., HIEPT=[3] - Hi endpt in other direction.
     */
    private static int[] getSegInfo(int[][] wire, int idx){
        int[] wireInfo = new int[4];    //[0]=horz(0),vert(1) / [1]=hort/vert loc / [2]=lo endpt / [3]=hi endpt
        int node0 = Math.max(idx - 1, 0);   //Node closet to origin
        int node1 = idx;                    //Node next in wire

        wireInfo[VERT] = (wire[node1][0] - wire[node0][0]) == 0 ? 0 : 1; //Wire seg is horz(0) or vert(1)

        wireInfo[SLOC] = wire[node1][wireInfo[VERT]];   //Wire segment horz/vert position
        int tmpI = wireInfo[VERT] == 0 ? 1 : 0;    //Other axis
        wireInfo[LOEPT] = wire[node1][tmpI];    //Wire Lo value end point
        wireInfo[HIEPT] = wire[node0][tmpI];    //Wire Hi value end point
        if(wireInfo[LOEPT] > wireInfo[HIEPT]){  //Swap Lo/Hi values if needed
            tmpI = wireInfo[LOEPT];
            wireInfo[LOEPT] = wireInfo[HIEPT];
            wireInfo[HIEPT] = tmpI;
        }
        return wireInfo;
    }

    /**
     * Check to see if 2 wire segments cross
     * @param seg1 info VERT[0], seg loc[1], Lo end pt[2], hi end pt[3]
     * @param seg2 info
     * @return true if segments cross
     */
    private static boolean doWiresCross(int[] seg1, int[] seg2){
        //Check a wire is horz(0) or vert(1) and the other is the opposite
        return( (seg1[VERT] != seg2[VERT]) &&
        //AND Chk if wire 2 is between wire 1 end pts
        isBetween(seg2[SLOC], seg1[LOEPT], seg1[HIEPT]) &&
        //AND Chk if wire 1 is between wire 2 end pts.
        isBetween(seg1[SLOC], seg2[LOEPT], seg2[HIEPT]) );
    }

    /**
     * Is the check value between lo & hi
     * @param chk - value to check
     * @param lo value
     * @param hi value
     * @return true if chk is between lo & hi.
     */
    private static boolean isBetween(int chk, int lo, int hi){
        return (chk >= lo && chk <= hi);
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

}