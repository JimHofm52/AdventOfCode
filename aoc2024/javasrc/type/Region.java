package type;

import java.util.Arrays;

public class Region {
    private static int regIDCntr = 0;   //Increments for each new region
    private static int rowMx;           //Max rows of all plots
    private static int colMx;           //Max cols of all plots
    private int regID;                  //Unique region ID
    private char regFood;               //Food type grown in this region
    private int[][] plotsRC = new int[0][2];    //Array of plots with [0]row, [1]col
    private int area;                   //Total area of this region
    private int perimeter;              //Total perimeter of this region
    private int sides;                  //Total sides, contiguoous straight sides.

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
            plotsRC[plotsRC.length - 1] = new int[] {plotRC[0], plotRC[1]};
            return plotsRC;
    }

    /**
     * Calculates the perimeter of all the plots in this region.
     * @param plotsRC
     * @return total perieter
     */
    private static int calcPerimeter(int[][] plotsRC){
        int totPer = 0;
        for(int[] rc : plotsRC){
            totPer += cntPlotOpenSides(plotsRC, rc);
        }
        return totPer;
    }

    /**
     * Count a plots open sides.  Used to calculate perimeter.
     * @param plotsRC
     * @param plotRC
     * @return Number of sides that are not adjacent to another plot.
     */
    private static int cntPlotOpenSides(int[][] plotsRC, int[] plotRC){
        int r = plotRC[0];
        int c = plotRC[1];
        int adjPerim = 0;
        for(int[] rc : plotsRC){
            if((rc[0] == r && (rc[1] - 1 == c || rc[1] + 1 == c))) adjPerim++;
            if((rc[1] == c && (rc[0] - 1 == r || rc[0] + 1 == r))) adjPerim++;
        }
        return 4 - adjPerim;
    }

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
    /**@return the perimeter of this region. */
    public int getPerimeter(){
        perimeter = calcPerimeter(plotsRC);
        return perimeter;
    }
}
