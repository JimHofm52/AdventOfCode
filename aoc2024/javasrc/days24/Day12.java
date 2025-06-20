package days24;

import java.io.IOException;
import java.util.Arrays;

import type.Region;
import util.ReadWriteFiles;

public class Day12 {
    private static String[] fileInfo;
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started June 14, 2025,  Finished on June XX, 2025.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day12(){}

    public static void update() throws IOException {
        String fNum = "12";//Part1- 1375476   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        Region[] region = parceGrid(fileInfo);

        question1(region);    //Confirmed: 12- 1375476   Lo-1271306  121- 1930
        question2(region);    //Confirmed: 12- ???   121- 1206
    }

    /**
     * Question 1: What is the total price of fencing all regions on your map?
     *<p> Example 121: price = area * perimeter
     *<p> R plants with price 12 * 18 = 216.
     *<p> I plants with price 4 * 8 = 32.
     *<p> C plants with price 14 * 28 = 392.
     *<p> F plants with price 10 * 18 = 180.
     *<p> V plants with price 13 * 20 = 260.
     *<p> J plants with price 11 * 20 = 220.
     *<p> C plants with price 1 * 4 = 4.
     *<p> E plants with price 13 * 18 = 234.
     *<p> I plants with price 14 * 22 = 308.
     *<p> M plants with price 5 * 12 = 60.
     *<p> S plants with price 3 * 8 = 24.
     *<p> So, it has a total price of 1930.
     */
    private static void question1(Region[] region) {
        int plotArea;
        int plotPerim;
        int totPrice = 0;
        for(Region reg : region){
            plotArea = reg.getArea();
            plotPerim = reg.getPerimeter();
            System.out.println("Region: " + (char)reg.getRegFood() + 
                                "\tSeed: [" + reg.getSeed()[0] + ", " + reg.getSeed()[1] +
                                "]  \tArea: " + plotArea + 
                                " \tPerimeter: " + plotPerim + "\tPrice: " + (plotArea * plotPerim));
            totPrice += plotArea * plotPerim;
        }

        //Track ,  Confirmed: 12- ???  Lo-1271306  121- 1930
        System.out.println("\nPart 1 - Total Price: " + totPrice + "\n");
    }
    
    /**
     * Question 2: What is the new total price of fencing all regions on your map?
     *<p> Example 121: price = area * sides (contiguous edges)
     *<p> R plants with price 12 * 10 = 120.
     *<p> I plants with price 4 * 4 = 16.
     *<p> C plants with price 14 * 22 = 308.
     *<p> F plants with price 10 * 12 = 120.
     *<p> V plants with price 13 * 10 = 130.
     *<p> J plants with price 11 * 12 = 132.
     *<p> C plants with price 1 * 4 = 4.
     *<p> E plants with price 13 * 8 = 104.
     *<p> I plants with price 14 * 16 = 224.
     *<p> M plants with price 5 * 6 = 30.
     *<p> S plants with price 3 * 6 = 18.
     *<p> Adding these together produces its new total price of 1206.
     */
    private static void question2(Region[] region) {
        int plotArea;
        int plotSides;
        int totPrice = 0;
        for(Region reg : region){
            plotArea = reg.getArea();
            plotSides = reg.getPerimeter();
            System.out.println("Region: " + (char)reg.getRegFood() + 
                                "\tSeed: [" + reg.getSeed()[0] + ", " + reg.getSeed()[1] +
                                "]  \tArea: " + plotArea + 
                                " \tSides: " + plotSides + "\tPrice: " + (plotArea * plotSides));
            totPrice += plotArea * plotSides;
        }

        //Track ,  Confirmed: 12- ???   121- 1206
        System.out.println("\nPart 2 - Total Price: " + totPrice + "\n");
    }

    /**
     * Parce the plots, input, to food Region.
     * @param gridIn String array of food plots
     * @return an array of regions.
     */
    private static Region[] parceGrid(String[] gridIn){
        Region[] region = new Region[0];
        Region.setRowMx(gridIn.length);
        Region.setColMx(gridIn[0].length());
        int[] tmpRC = new int[2];
        int tmpStat = 0;
        char tmpFood;

        for(int r = 0; r < gridIn.length; r++){
            for(int c = 0; c < gridIn[r].length(); c++){
                tmpRC[0] = r;    tmpRC[1] = c;
                tmpFood = gridIn[r].charAt(c);
                tmpStat = tryAddPlot(region, tmpFood, tmpRC);
                if(tmpStat > 1){    //0=added, 1=exists, 2=no region (add), 4=not adj (add)
                    region = addRegion(region, tmpFood, tmpRC);
                    int regIdx = region.length - 1;
                    buildRegion(gridIn, region[regIdx], tmpRC[0], tmpRC[1]);
                }
            }
        }
        return region;
    }

    /**
     * Find all plots attached to the seed region.
     * @param gridIn Plot grid
     * @param reg Seed region (firsst region found)
     * @param plotR Row of seed region
     * @param plotC Column of seed region
     */
    private static void buildRegion(String[] gridIn, Region reg, int plotR, int plotC){
        char tmpFood;
        
        if(--plotR > -1){   //Try adding plot in new region above
            tmpFood = gridIn[plotR].charAt(plotC);
            if(reg.addPlot(tmpFood, plotR, plotC) == 0)
                buildRegion(gridIn, reg, plotR, plotC);
        }
        if((plotR += 2) < gridIn.length){   //Try adding plot in new region below
            tmpFood = gridIn[plotR].charAt(plotC);
            if(reg.addPlot(tmpFood, plotR, plotC) == 0)
                buildRegion(gridIn, reg, plotR, plotC);
        }
        plotR--;    //Back up to original Row.
        if((plotC--) > 0){      //Try adding plot in new region Left
            tmpFood = gridIn[plotR].charAt(plotC);
            if(reg.addPlot(tmpFood, plotR, plotC) == 0)
                buildRegion(gridIn, reg, plotR, plotC);
        }
        if((plotC += 2) < gridIn[0].length()){  //Try adding plot in new region right
            tmpFood = gridIn[plotR].charAt(plotC);
            if(reg.addPlot(tmpFood, plotR, plotC) == 0)
                buildRegion(gridIn, reg, plotR, plotC);
        }
    }

    /**
     * Check all existing regions to see if 
     * <p>(0) region exists with food type and is adjacent.  Plot added
     * <p>(1) Already added to region.
     * <p>(2) Food type not found or
     * <p>(4) plot not adjacent.  Add Region.
     * @param region arrary of existing regions
     * @param food passed plot's food type
     * @param tmpRC passed array of plot's row, col coordinates
     * @return 0 if plot added.  1 if plot already added.  2 If food type not found.  4 if Plot isn't adjacent
     */
    private static int tryAddPlot(Region[] region, char food, int[] tmpRC){
        int tmpStat = 1;
        for(Region reg : region){
            tmpStat = reg.addPlot(food, tmpRC);
            if(tmpStat == 0) return 0;  //Region exists and plot added
            if(tmpStat == 1) return 1;  //food exists and already exists in region
        }
        return 4;  //(2) No region exists or (4) plot not adjcent.  Add region.
    }

    /**
     * Add a new region with food type and coordinates of first plot (seed).
     * @param region
     * @param food
     * @param tmpRC
     * @return new region array.
     */
    private static Region[] addRegion(Region[] region, char food, int[] tmpRC){
        region = Arrays.copyOf(region, region.length + 1);
        region[region.length - 1] = new Region(food, tmpRC);
        return region;
    }
}