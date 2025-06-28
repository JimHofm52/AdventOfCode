package type;

import java.util.Arrays;

public class Region {
    private static int regIDCntr = 0;   //Increments for each new region
    private static int rowMx;           //Max rows of all plots
    private static int colMx;           //Max cols of all plots
    private int regID;                  //Unique region ID
    private char regFood;               //Food type grown in this region
    /**Array of plots with [0]row, [1]col, 
     * [2]Mux open edges of the plots. 0=none, 1=top, 2=right, 4=btm, 8=left */
    private int[][] plotsRC = new int[0][3];
    private int[] totPerCnr = new int[3];   //[0]=>Perimeter, [1]=>outter corner, [2]=>inner corner
    private int[][] insideCorners;      //[0]=>Row, [1]=>Col coor., [2]=>Corner Edges
    private int[] rowMnMx = new int[2]; //Row [0]=>Min, [1]=>Max
    private int[] colMnMx = new int[2]; //Col [0]=>Min, [1]=>Max
    private int area;                   //Total area of this region
    private int perimeter;              //Total perimeter of this region
    private int sides;                  //Total sides, contiguoous straight sides.

    public enum EDGE {N, T, R, B, L};   //None, Top, Right, Bottom, Left
    public enum DIR {R, D, L, U};       //Right, Down, Left, Up.

    /**
     * Create a new region with food type passed and add new plot, seed plot.
     * @param foodType Type of food grow in this region's adjacent plots
     * @param plotRC array of Row/Column coordinates of the plot to be added.
     */
    public Region(char foodType, int[] plotRC){
        regID = regIDCntr++;
        regFood = foodType;
        plotsRC = appendPlot(plotsRC, plotRC);
    }

    /*--------------------  Public calls  -------------------------------*/

    /**@param rowMax set the maximum number of rows.  Not used, kept it anyway.  */
    public static void setRowMx(int rowMax){ rowMx = rowMax; }
    /**@param rowCol set the maximum number of columns.  Not used, kept it anyway.  */
    public static void setColMx(int colMax){ colMx = colMax; }
    /**@return the region index.  Thought we might needed it since there were multiple
     * regions with the same food type.  Not used, kept it anyway */
    public int getRegID(){ return regID; }

    /**@return the region's food type. */
    public char getRegFood(){ return regFood; }

    /**@return the first plot added to the region. */
    public int[] getSeed(){ return plotsRC[0]; }

    /**@return the area of this region's plots. */
    public int getArea(){
        area = plotsRC.length;
        return area;
    }

    /**@return the stored perimeter of this region. Use calcPerimeter() to recalc perimeter */
    public int getPerimeter(){ return perimeter; }

    /**@return the side count of this region. <p>Use calcSides to re-calc sides first. */
    public int getSides(){ return sides; }

    /**
     * Do all the calulations for Perimeter & Sides.
     * Called after input file is read in and Regions & plots are added.
     */
    public void doAllCalcs(){
        muxPlotOpenEdges(plotsRC);
        cntPlotOpenSides(plotsRC, totPerCnr, rowMnMx, colMnMx);   //[0]=>tot perim, [1]=>tot corners
        insideCorners = mapInsideCorners(plotsRC, rowMnMx, colMnMx);
        totPerCnr[2] = insideCorners.length;
        area = plotsRC.length;
        perimeter = totPerCnr[0];
        sides = totPerCnr[1] + totPerCnr[2];
    }
    //-----------------------  Calcs for Perimeter, Inside/Outside corners for sides  --------------

    /**
     * Mux each plot; 0=No open sides, 1=top open, 2=right, 4=bottom & 8=left.
     * <p>IE. 3, top & right open.  15, all open as a stand alone plot.
     * @param plotsRC
     * @return a value 0 to 15.
     */
    private static void muxPlotOpenEdges(int[][] plotsRC){
        int[] plotRC = new int[2];
        for(int i = 0; i < plotsRC.length; i++){
            plotRC[0] = plotsRC[i][0] - 1;   //Chk top
            plotRC[1] = plotsRC[i][1];
            plotsRC[i][2] = rcExists(plotsRC, plotRC) ? 0 : 1;
            plotRC[0]++;  plotRC[1]++;      //Chk Right
            plotsRC[i][2] += rcExists(plotsRC, plotRC) ? 0 : 2;
            plotRC[0]++;  plotRC[1]--;      //Chk Btm
            plotsRC[i][2] += rcExists(plotsRC, plotRC) ? 0 : 4;
            plotRC[0]--;  plotRC[1]--;      //Chk Left
            plotsRC[i][2] += rcExists(plotsRC, plotRC) ? 0 : 8;
        }
    }

    /**
     * Count a plots open sides, used to calculate perimeter.
     * <p>And outside corners, used to calculate sides.
     * @param plotsRC all existing plots
     * @param retrnPC used to return [0]Perimeter count & [1]outside corner count
     * @param retrnRM used to return Row [0]Min & [1]Max
     * @param retrnCM used to return Col [0]Min & [1]Max
     */
    private static void cntPlotOpenSides(int[][] plotsRC, int[] retrnPC, int[] retrnRM, int[] retrnCM){
        retrnPC[0] = 0;   //Return total perimeter
        retrnPC[1] = 0;   //Return total corners
        retrnRM[0] = rowMx;   //Return Min Row
        retrnRM[1] = 0;   //Return Max Row
        retrnCM[0] = colMx;   //Return Min Col
        retrnCM[1] = 0;   //Return Max Col
        for (int p[] : plotsRC) {
            retrnRM[0] = Math.min(retrnRM[0], p[0]);
            retrnRM[1] = Math.max(retrnRM[1], p[0]);
            retrnCM[0] = Math.min(retrnCM[0], p[1]);
            retrnCM[1] = Math.max(retrnCM[1], p[1]);

            switch (p[2]) {
                case 0:                             //no edges or corners.  Internal plot
                break;
                case 1: case 2: case 4: case 8:     //1-edge, no corners
                retrnPC[0] += 1;
                break;
                case 3: case 6: case 9: case 12:    //2-edges, 1-corner.  Adjacent Edges
                retrnPC[0] += 2;
                retrnPC[1] += 1;
                break;
                case 5: case 10:                    //2-edges, 0-corners.  Opposing edges
                retrnPC[0] += 2;
                break;
                case 7: case 11: case 13: case 14:  //3-edges, 2-corners.  Plot sticking out
                retrnPC[0] += 3;
                retrnPC[1] += 2;
                break;
                case 15:                            //4-edges, 4 corners.  Single block
                retrnPC[0] += 4;
                retrnPC[1] += 4;
                break;
                default:
                System.out.println("Illegal edge mux: " + p[2]);
                break;
            }
        }
    }

    /**
     * Builds an array of all inside corners.  Used an array to avoid duplicates.
     * Just not sure.  :)
     * @param plotsRC   All existing plots
     * @param mnmxR [0]min & [1] max row
     * @param mnmxC [0]min & [1] max col
     * @return Array of all inside corners
     */
    private static int[][] mapInsideCorners(int[][] plotsRC, int[] mnmxR, int[] mnmxC){
        int[][] outCrnrRC = new int[0][3];
        int[] tmpPlot = new int[3];
        for(int i = 0; i < plotsRC.length; i++) {
            switch (plotsRC[i][2]) {
                case 0:                         //no open edges.  Internal plot
                tmpPlot[0] = plotsRC[i][0] - 1; //Chk Upper
                tmpPlot[1] = plotsRC[i][1] - 1; //& Left first. Is plot empty & in bounds append
                tmpPlot[2] = 6;                 //Tag corner as opposite Lower Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                tmpPlot[0] += 2;                //Chk Down & Left
                tmpPlot[2] = 3;                 //Tag corner as opposite Upper Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                tmpPlot[1] += 2;                //Chk Down & Right
                tmpPlot[2] = 9;                 //Tag corner as opposite Upper Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                tmpPlot[0] -= 2;                //Chk Upper & Right
                tmpPlot[2] = 12;                //Tag corner as opposite Lower Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 1:                         //Top open edge, chk lower 2 corners
                tmpPlot[0] = plotsRC[i][0] + 1; //Chk Down
                tmpPlot[1] = plotsRC[i][1] - 1; //& Left first
                tmpPlot[2] = 3;                 //Tag corner as opposite Upper Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                tmpPlot[1] += 2;                //Down & Right
                tmpPlot[2] = 9;                 //Tag corner as opposite Upper Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 2:                         //Right open edge, chk Left 2 corners
                tmpPlot[0] = plotsRC[i][0] - 1; //Chk Up
                tmpPlot[1] = plotsRC[i][1] - 1; //& Left first
                tmpPlot[2] = 6;                 //Tag corner as opposite Lower Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                tmpPlot[0] += 2;                //Down & Left
                tmpPlot[2] = 3;                 //Tag corner as opposite Upper Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 3:                         //Top & Right open edge, chk Lower Left corners
                tmpPlot[0] = plotsRC[i][0] + 1; //Chk Lower
                tmpPlot[1] = plotsRC[i][1] - 1; //& Left
                tmpPlot[2] = 3;                 //Tag corner as opposite Upper Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 4:                         //Down open edge, chk Right 2 corners
                tmpPlot[0] = plotsRC[i][0] - 1; //Chk Up
                tmpPlot[1] = plotsRC[i][1] - 1; //& Left first
                tmpPlot[2] = 6;                 //Tag corner as opposite Lower Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                tmpPlot[1] += 2;                //Up & Right
                tmpPlot[2] = 12;                //Tag corner as opposite Lower Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 5:                         //Top & bottom open edge, No corners
                break;
                case 6:                         //Bottom & Right open edge, chk Upper Left corner
                tmpPlot[0] = plotsRC[i][0] - 1; //Chk Up
                tmpPlot[1] = plotsRC[i][1] - 1; //& Left
                tmpPlot[2] = 6;                 //Tag corner as opposite Lower Right
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 7:                         //Top, right & bottom open edge, No corners
                break;
                case 8:                         //Left open edge, chk Right 2 corners
                tmpPlot[0] = plotsRC[i][0] - 1; //Chk Up
                tmpPlot[1] = plotsRC[i][1] + 1; //& Right first
                tmpPlot[2] = 12;                //Tag corner as opposite Lower Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                tmpPlot[0] += 2;                //Down & Right
                tmpPlot[2] = 9;                 //Tag corner as opposite Upper Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 9:                        //Top & Left open edge, chk Lower Right corner
                tmpPlot[0] = plotsRC[i][0] + 1; //Chk Down
                tmpPlot[1] = plotsRC[i][1] + 1; //& Right
                tmpPlot[2] = 9;                 //Tag corner as opposite Upper Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 10:                         //Left & right open edge, No corners
                break;
                case 11:                         //Top, Left & right open edge, No corners
                break;
                case 12:                        //Lower & Left open edge, chk Upper Right corners
                tmpPlot[0] = plotsRC[i][0] - 1; //Chk Up
                tmpPlot[1] = plotsRC[i][1] + 1; //& Right
                tmpPlot[2] = 12;                //Tag corner as opposite Lower Left
                if(rcExists(plotsRC, tmpPlot, mnmxR, mnmxC) == 0) outCrnrRC = appendPlotChk(outCrnrRC, tmpPlot);
                break;
                case 13:                         //Top, Bottom & Left open edge, No corners
                break;
                case 14:                         //Bottom, Right & Left open edge, No corners
                break;
                case 15:                         //All open edge, No corners.  Single plot.
                // System.out.print("Single stand alone plot?: " + plotsRC[i][2]);
                // System.out.println("\tRC: [" + plotsRC[i][0] + ", " + plotsRC[i][1] + "] ");
                break;
                default:
                System.out.println("Illegal edge mux: " + plotsRC[i][2]);
                break;
            }
        }
        return outCrnrRC;
    }

    /*----------------  Methods to add a plot with checks for food type,
                        doesn't already exists & is adjacent.              ---------------- */
    /**
     * Try to add plot to this region.
     * @param foodType
     * @param plotRC array of plot row, column
     * @return 0, successfully added.  1, already in region. OR (need to add a region) 
     * 2, wrong food type.  4, is not adjacent.
     */
    public int addPlot(char foodType, int[] plotRC){
        if(regFood != foodType) return 2;           //Not the same food.  Add region?
        if(rcExists(plotsRC, plotRC)) return 1;     //Same food but already exists
        if(!isAdjacent(plotsRC, plotRC)) return 4;  //Same food but not adjcent.  Add region?
        plotsRC = appendPlot(plotsRC, plotRC);      //Same food, adj, doesn't exist: add plot
        area = plotsRC.length;
        return 0;                                   //Plot added
    }

    /**
     * Try to add plot to this region.
     * @param foodType
     * @param plotR plot row
     * @param plotC plot column
     * @return 0, successfully added.  1, already in region. OR (need to add a region) 
     * 2, wrong food type.  4, is not adjacent.
     */
    public int addPlot(char foodType, int plotR, int plotC){
        return addPlot(foodType, new int[] {plotR, plotC});
    }

    /**
     * Check plot row/col already exists in this region.
     * @param plotRC array existing of existing plots
     * @param plot array of new plot row/col to check
     * @return return true if it alreay exists else false.
     */
    private static boolean rcExists(int[][] plotRC, int[]plot){
        for(int[] rc : plotRC){
            if(rc[0] == plot[0] && rc[1] == plot[1]){
                return true;
            }
        }
        return false;
    }

    /**
     * Check plot row/col already exists in this region. And is in bounds
     * @param plotRC array existing of existing plots
     * @param plot array of new plot row/col to check
     * @return return -2 if row outside of plot area, -1 if col outside of plot area,
     * <p>0 if doesn't exist AND 1 if plot exists
     */
    private static int rcExists(int[][] plotRC, int[]plot, int[] mnmxR, int[] mnmxC){
        if(plot[0] < mnmxR[0] || plot[0]  > mnmxR[1]) return -2;
        if(plot[1] < mnmxC[0] || plot[1]  > mnmxC[1]) return -1;
        for(int[] rc : plotRC){
            if(rc[0] == plot[0] && rc[1] == plot[1]){
                return 1;
            }
        }
        return 0;
    }

    /**
     * Check if plot is vert/horz adjacent to any of the existing plots
     * @param plotRC
     * @param plot
     * @return true if adjacent else false.
     */
    private static boolean isAdjacent(int[][] plotRC, int[]plot){
        for(int[] rc : plotRC){
            if((rc[0] == plot[0] && (Math.abs(rc[1] - plot[1]) == 1)) ||
               (rc[1] == plot[1] && (Math.abs(rc[0] - plot[0]) == 1))){
                return true;
            }
        }
        return false;
    }

    /**
     * Append the plot to this region. Does not check for duplicate.
     * @param plotsRC
     * @param plotRC
     * @return return the new region array.
     */
    private static int[][] appendPlot(int[][] plotsRC, int[] plotRC){
            plotsRC = Arrays.copyOf(plotsRC, plotsRC.length + 1);
            plotsRC[plotsRC.length - 1] = new int[] {plotRC[0], plotRC[1], 0};
            return plotsRC;
    }

    /**
     * Append the plot to this region. Checks for duplicate.
     * @param plotsRC
     * @param plotRC
     * @return return the new region array.
     */
    private static int[][] appendPlotChk(int[][] plotsRC, int[] plotRC){
        for(int[] plot : plotsRC){
            if(plot[0] == plotRC[0] && plot[1] == plotRC[1] && plot[2] == plotRC[2]) return plotsRC;
        }
        plotsRC = Arrays.copyOf(plotsRC, plotsRC.length + 1);
        plotsRC[plotsRC.length - 1] = new int[] {plotRC[0], plotRC[1], plotRC[2]};
        return plotsRC;
    }
}
