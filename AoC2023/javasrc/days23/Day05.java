package days23;

import java.io.IOException;
import java.util.Arrays;

import type.Seed;
import type.SeedMap;
import util.ReadInput;

public class Day05 {
    private static String fileInfo[];
    private static int len;
    private static Seed seeds;
    private static SeedMap[] dest2src = new SeedMap[0];

    /** Constructor, not needed but used for standards. */
    private Day05(){}

    public static void update() throws IOException {
        String fNum = "05"; //Part1- 621354867   Part2- 15880236
        // String fNum = "051";//Part1- 35   Part2- 46
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        parceMaps(fileInfo);    //maps returned in dest2src[]
 
        question1();    //Confirmed: 05- 621354867   051- 35
        question2();    //Confirmed: 05- 15880236   051- 46
    }

    /**
     * Question 1: Find the lowest location number that corresponds to any of the initial seeds.
     */
    private static void question1() {
        seeds = new Seed(fileInfo[0], 1);
        getSeedLoc(seeds, dest2src);
        int tmps = findLowLoc();

        //Track ,  Confirmed: 05- 621354867   051- 35
        System.out.println("\nPart 1: Lowest Loc is for seed: " + seeds.seedNum[tmps] +
                            " (#" + (tmps + 1) + "), location " + seeds.seedLoc[tmps]);
    }

    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        seeds = new Seed(fileInfo[0], 2);
        getSeedLoc(seeds, dest2src);
        int tmps = findLowLoc();

        //Track ,  Confirmed: 05- 15880236   051- 46
        System.out.println("\nPart 2: Lowest Loc is for seed: " + seeds.seedNum[tmps] +
                            " (#" + (tmps + 1) + "), location " + seeds.seedLoc[tmps]);
    }

    private static void getSeedLoc(Seed sGrp, SeedMap[] maps){
        long seedBeg = 0;
        long seedLo = -1L;
        long tmp;
        //  79=>82, 14=>43, 55=>86, 13=>35
        for(int i = 0; i < sGrp.cnt; i++){              //for each seed group
            seedBeg = sGrp.seedNum[i];
            seedLo = -1L;
            for(int j = 0; j < sGrp.seedLen[i]; j++){   //for each seed in group
                tmp = seedBeg + j;
                for(int k = 0; k < 7; k++){             //find Loc
                    tmp = maps[k].getDest(tmp);
                }
                if(seedLo == -1L) seedLo = tmp;
                if(tmp < seedLo){                       //save if new lo
                    seedLo = tmp;
                }
            }
            sGrp.seedLoc[i] = seedLo;
        }
    }
    
    private static void parceMaps(String[] inFile){
        String str;
        int strIdx = 3;
        for(int map = 0; map < 7; map++){                   //get 7 maps
            dest2src = Arrays.copyOf(dest2src, map + 1);    //create map
            dest2src[map] = new SeedMap(inFile[strIdx++]);  //init map w/ "dest src rng"
            do{                                             //read in all map data
                dest2src[map].addDest2Src(inFile[strIdx]);
                str = ++strIdx < inFile.length ? inFile[strIdx] : "";   //kludge to catch EOF
            }while(!str.equals(""));    //until end of map or end of file
            strIdx += 2;
        }
    }

    private static int findLowLoc(){
        long tmp = seeds.seedLoc[0];
        int tmps = 0;
        for(int s = 1; s < seeds.cnt; s++){
            if(seeds.seedLoc[s] < tmp){
                tmp = seeds.seedLoc[s];
                tmps = s;
            }
        }
        return tmps;
    }

}