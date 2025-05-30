package days24;

import java.io.IOException;
import java.util.Arrays;

import type.Trailhead;
import util.ReadWriteFiles;

public class Day10 {
    private static String fileInfo[];
    private static int len;
    private static enum Dir {R, D, L, U; }

    /** Constructor, not needed but used for standards. 
     * <p>Started May 28, 2025,  Finished on May 29, 2025.
     * <p>Took ~xx hour.  Runtime 0.yyy S.
    */
    private Day10(){}

    public static void update() throws IOException {
        String fNum = "10";//Part1- 667   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        Trailhead[] myTHs = findTrailHead(fileInfo);

        question1(myTHs, fileInfo);    //Confirmed: 10- 667   101- 36
        question2();    //Confirmed: 10- ???   101- ???
    }

    /**
     * Question 1: What is the sum of the scores of all trailheads on your topographic map?
     * A trailhead is any position that have height 0.
     * Score is the number of 9 heights that can be reached from a trailhead moving 1 height
     * at a time, moving left, right, up or down.
     */
    private static void question1(Trailhead[] thIn, String[] map) {
        int[] thLoc = new int[2];
        int trailCnt = 0;
        for(Trailhead th : thIn){
            thLoc[0] = th.getRow();
            thLoc[1] = th.getCol();
            findTrails(map, th, thLoc, '0');
            trailCnt += th.getTrailCnt();
        }
        //Track ,  Confirmed: 10- 667   101- 36 = 5, 6, 5, 3, 1, 3, 5, 3, and 5
        System.out.println("\nPart 1: Total number of trails: " + trailCnt);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 10- ???   101- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }

    private static Trailhead[] findTrailHead(String[] strIn){
        Trailhead[] thIn = new Trailhead[0];
        for(int r = 0; r < strIn.length; r++){
            for(int c = 0; c < strIn[0].length(); c++){
                if(strIn[r].charAt(c) == '0'){
                    thIn = Arrays.copyOf(thIn, thIn.length + 1);
                    thIn[thIn.length - 1] = new Trailhead(r, c);
                }
            }
        }
        return thIn;
    }

    private static void findTrails(String[] mapIn, Trailhead th, int[] prvLoc, int prvHeight){
        int nxtHeight = prvHeight++;
        int[] nxtLoc;

        for(int dir = 0; dir < 4; dir++){
            nxtLoc = addDir(prvLoc, dir);
            if(isOnMap(nxtLoc, mapIn)){
                nxtHeight = mapIn[nxtLoc[0]].charAt(nxtLoc[1]);
                if(nxtHeight == prvHeight){
                    if(nxtHeight == '9'){
                        th.addTrailtail(nxtLoc);
                    }else{
                        findTrails(mapIn, th, nxtLoc, nxtHeight);
                    }
                }
            }
        }
    }

    private static int[] addDir(int[] loc, int dir){
        switch(dir){
            case 0: return new int[] {loc[0], loc[1] + 1};
            case 1: return new int[] {loc[0] + 1, loc[1]};
            case 2: return new int[] {loc[0], loc[1] - 1};
            case 3: return new int[] {loc[0] - 1, loc[1]};
            default:
            System.out.println("Bad trail direction to add - " + dir);
            return loc;
        }
    }

    private static boolean isOnMap(int[] loc, String[] map){
        return (loc[0] >= 0 && loc[0] < map[0].length() &&
                loc[1] >= 0 && loc[1] < map[0].length());
    }
}