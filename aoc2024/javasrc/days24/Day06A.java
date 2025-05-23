package days24;

import java.io.IOException;
import java.util.Arrays;

import util.AryUtil;
import util.ReadWriteFiles;

/**
 * Create a char[row][col] map of the input
 */
public class Day06A {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started Dec 6, 2024,  Finished on May 22, 2025..
     * <p>Took Too many hour.  Runtime 18.645 S.
    */
    private Day06A(){}

    public static void update() throws IOException {
        String fNum = "06";//Part1- 4602   Part2- 1703
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        char[][] map = makeMap();       //[0]=row, [1]=col
        int[][] grdSteps = getGrdSteps(map);        //grd starts @ 79,87

        question1(grdSteps);        //Confirmed: 06- 4602   061- 41
        question2(map, grdSteps);   //Confirmed: 06- 1703  hi-1704  061- 6
    }

    /**
     * Question 1: How many distinct positions will the guard 
     * visit before leaving the mapped area?
     */
    private static void question1(int[][] grdSteps) {
        //Track ,  Confirmed: 06- 4602   061- 41
        System.out.println("\nPart 1: Total path of guard: " + grdSteps.length);
    }
    
    /**
     * Question 2: You need to get the guard stuck in a loop by adding 
     * a single new obstruction. How many different positions could you 
     * choose for this obstruction?
     */
    private static void question2(char[][] map, int[][] grdSteps) {
        char[][] map1;
        int totLoops = 0;
        int[][] loopChk;
        int[][] stepNow = new int[0][2];
        int[] grdNow = new int[2];

        for(int i = 2; i < grdSteps.length; i++){           //For each step of the original path
            map1 = AryUtil.copyChar2By(map);                //Start with a clean map
            if(map1[grdSteps[i][0]][grdSteps[i][1]] != '^'){//If not crossing the starting point
            // if(grdSteps[i][0] != 79 || grdSteps[i][1] != 87){//If not crossing the starting point
                map1[grdSteps[i][0]][grdSteps[i][1]] ='o';  //Set new block, same as '#'
                grdNow[0] = grdSteps[i][0];
                grdNow[1] = grdSteps[i][1];
                loopChk = walkTheGuard2(map1);              //Walk the guard
                if(loopChk[0][0] < 0){      //If -1 guard is walking in circles, looping.
                    stepNow = addStep(stepNow, grdNow);
                    ++totLoops;      //Check < 0 looping else no loop
                }
            }
        }
        int a = 0;
        stepNow = delDups(stepNow);

        for(int r = 0; r < stepNow.length; r++){
            if(stepNow[r][0] == 79 && stepNow[r][1] == 87){
                int b = 0;
            }
        }
        //Track ,  Confirmed: 06- 1703  hi-1704  061- 6
        System.out.println("\nPart 2: Total number of circular Blocked paths of guard: " + totLoops);
    }

    //========================  Working Files  ==========================

    /**
     * Use fileInfo[] as input to make a map of the room.  
     * <p>'^' is the starting point for the gaurd and '#' indicates a fixed item.
     *  
     * @return a char[][] of map, using row & column as coordinates.
     */
    private static char[][] makeMap(){
        char[][] tmpMap = new char[len][fileInfo[0].length()];
        for(int row = 0; row< len; row++){
            for(int col = 0; col < fileInfo[0].length(); col++){
                tmpMap[row][col] = fileInfo[row].charAt(col);
            }
        }
        return tmpMap;
    }

    /**
     * Find the '^', guard's original location.
     * @param map of the room
     * @return [row,col] of guards starting location
     */
    private static int[] findGuard(char[][] map){
        int[] loc = new int[2];
        for(int row = 0; row< len; row++){
            for(int col = 0; col < fileInfo[0].length(); col++){
                if(map[row][col] == '^'){
                    loc[0] = row;
                    loc[1] = col;
                    return loc;
                }
            }
        }
        return loc;
    }

    /**
     * Make an array of all locations the guard visits
     * 
     * @param map of the room with starting position and blocks
     * @return an array of all locations the guard visits
     */
    private static int[][] getGrdSteps(char[][] map){
        char[][] mapCopy = AryUtil.copyChar2By(map);    
        // char[][] mapCopy = Arrays.copyOf(map, map.length);   //Copies as ref(?).
        int[][] grdSteps = new int[0][2];
        grdSteps = addStep(grdSteps, findGuard(mapCopy));
        grdSteps = walkTheGuard2(mapCopy);
        grdSteps = delDups(grdSteps);
        return grdSteps;
    }
    
    /**
     * Delete any duplicate row/col locations of where the guard has been.
     * 
     * @param ary array of row/col locations of where the guard has been
     * @return cleaned array
     */
    private static int[][] delDups(int[][] ary){
        int rNum;
        int cNum;

        for(int r = 0; r < ary.length; r++){
            rNum = ary[r][0];
            cNum = ary[r][1];
            if(rNum > -1){
                for(int rs = r + 1; rs < ary.length; rs++){
                    if(rNum == ary[rs][0] && cNum == ary[rs][1]) ary[rs][0] = -1;
                }
            }
        }

        int[][] saveAry = new int[0][2];
        for(int r = 0; r < ary.length; r++){
            if(ary[r][0] > -1){
                saveAry = addStep(saveAry, ary[r]);
            }
        }
        return saveAry;
    }

    //--------------------  Part 1 & 2  --------------------
    /**
     * Walk the guard and track the path they take.  Until it is out or starts repeating.
     * 
     * @param mapIn
     * @return
     */
    private static int[][] walkTheGuard2(char[][] mapIn){
        int[] grdLoc = findGuard(mapIn);    //Guard present loc, [0]=row  [1]=col
        int grdDir = 0;                     //Direction guard is facing, 0 = Up, 1 =  right, 2 = down, 3 = left
        int[] nxtLoc = addDirMoveToGrdLoc(grdLoc, grdDir);  //Next Location guard is to goto
        char nxtCh;                         //Char at the next loc
        int[][] grdPath = new int[0][2];    // Array to track where the guard has been.
        grdPath = addStep(grdPath, grdLoc);
        int rptCnt = 0;

        do{
            nxtCh = mapIn[nxtLoc[0]][nxtLoc[1]];  //Get the char for the next loc
            switch(nxtCh){
                case '#':   //Blocked turn right 90
                case 'o':   //same Added Block (part 2)
                grdDir = (grdDir + 1) % 4;  //Turn right
                break;
                case '.':       //Open position, set to x
                case '^':       //Crossing start position, set to x
                rptCnt = 0;
                mapIn[nxtLoc[0]][nxtLoc[1]] = 'x';
                grdPath = addStep(grdPath, nxtLoc);
                grdLoc = nxtLoc;
                break;
                case 'x':       //Been here once
                case 'y':       //Been here twice.  Might be a crossover.
                mapIn[nxtLoc[0]][nxtLoc[1]]++;      //x=>y, y=>z
                grdPath = addStep(grdPath, nxtLoc); //Add this RC to path walked
                grdLoc[0] = nxtLoc[0];    //Set guard loc to next, row
                grdLoc[1] = nxtLoc[1];    //Set guard loc to next, col
                rptCnt = 0;         //Not repeating yet
                break;
                case 'z':           //Been here 3 time, no longer a xover.
                rptCnt++;           //Count z's
                grdLoc = nxtLoc;    //Set guard loc to next
                if(rptCnt > 5){     //5 in a row, definitely not a xover.
                    mapIn[nxtLoc[0]][nxtLoc[1]] = 'E';  //The End of a repeat
                    grdPath[0][0] = -1; //Flag done with repeating
                    return grdPath;     //Return all other path loc's
                }
                break;
                default:
                System.out.println("WTF, char: " + nxtCh);
            }
            nxtLoc = addDirMoveToGrdLoc(grdLoc, grdDir);
            //Keep trying while in map boundry: 0 to length, row & column
        }while(nxtLoc[0] > -1 && nxtLoc[0] < mapIn[0].length &&
               nxtLoc[1] > -1 && nxtLoc[1] < mapIn.length);
        return grdPath; //Return all path loc's w/o flag of repating, [0][0] = -1
    }

    /**
     * Add the present location to the array that keeps track of where the guard has been.
     * @param path array of where the guard has been
     * @param loc new location to add to the end of the path array
     * @return new path the guard has walked
     */
    private static int[][] addStep(int[][] path, int[] loc){
        int[][] p = new int[1][loc.length];
        if(path != null && path.length > 0){
            p = Arrays.copyOf(path, path.length + 1);
            p[p.length - 1] = new int[2];
        }
        p[path.length][0] = loc[0];
        p[path.length][1] = loc[1];
        return p;
    }

    /**
     * Move the guard's location.  Add to thier loc the direction pointed.
     * 
     * @param loc present [0]=row & [1]=col location
     * @param dir values to add to move:
     * <p>0 = up, 1 = right, 2 = down, 3 - left
     * @return next location
     */
    private static int[] addDirMoveToGrdLoc(int[] loc, int dir){
        switch(dir){
            case 0: //up
            return new int[] {loc[0] - 1, loc[1]};
            case 1: //right
            return new int[] {loc[0], loc[1] + 1};
            case 2: //down
            return new int[] {loc[0] + 1, loc[1]};
            case 3: //left
            return new int[] {loc[0], loc[1] - 1};
            default:
            System.out.println("Bad guard direction to add - " + dir);
            return loc;
        }
    }
}