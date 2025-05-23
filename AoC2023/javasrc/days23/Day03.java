package days23;

import java.io.IOException;
import java.util.Arrays;

import type.Engine;
import util.ReadWriteFiles;

public class Day03 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day03(){}

    public static void update() throws IOException {
        String fNum = "03";     //Part1- 538046   Part2- 81709807
        // String fNum = "032";    //Part1- xxx    Part2- 467835
        // String fNum = "031";    //Part1- 4361   Part2- xxx
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        Engine engList[] = new Engine[len];
        for(int e = 0; e < len; e++) engList[e] = new Engine(fileInfo[e]);
        parceInput(engList);

        question1(engList);    //Confirmed: 03- 538046     031- 4361    033-7253 <=from reddit
        question2(engList);    //Confirmed: 03- 81709807   032- 467835
    }

    /**
     * Question 1: What is the sum of all of the part numbers in the engine schematic?
     */
    private static void question1(Engine[] eng) {
        int tot = 0;
        int tot2 = 0;
        for(int i = 0; i < len; i++){
            tot += eng[i].partSum;
            tot2 += eng[i].partCnt;
        }
        //Track ,  Confirmed: 03-542698 hi, 537066 Lo    031- 4361
        System.out.println("\nPart 1: ???: " + tot + "\t" + tot2);
    }



    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2(Engine[] eng) {
        int gears = findGears(eng);     //mod tagIsAst with just 2 nums touching.
        //Track ,  Confirmed: 03-63487989 Lo, 81709807   032- 467835
        System.out.println("\nPart 2: ???: " + gears);
    }

    private static int findGears(Engine[] eng){
        int gearTot = 0;
        for(int i = 0; i < eng.length; i++){    //for each eng line
            for(int a = 0; a < eng[i].tagIsAst.length; a++){    //for each * fnd
                if(eng[i].tagIsAst[a]){
                    gearTot += chk2Nums(eng, i, a);
                }
            }
        }
        return gearTot;
    }

    private static int chk2Nums(Engine[] eng, int eIdx, int aIdx){
        int partProd = 1;
        int partCnt = 0;
        int begIdx = Math.max(eIdx - 1, 0);
        int endIdx = Math.min(eIdx + 2, eng.length);
        int aLoLim = aIdx - 1;
        int aHiLim = aIdx + 1;
        boolean tmpB = false;
        for(int i = begIdx; i < endIdx; i++){
            for(int n = 0; n < eng[i].num.length; n++){
                if(((eng[i].numBegIdx[n] >= aLoLim) &&
                    (eng[i].numBegIdx[n] <= aHiLim)) ||
                   ((eng[i].numEndIdx[n] >= aLoLim) && 
                    (eng[i].numEndIdx[n] <= aHiLim))){
                    partProd *= eng[i].num[n];
                    partCnt++;
                    if(partCnt > 2) return 0;
                }
            }
        }
        if(partCnt == 2){
            eng[eIdx].astCnt++;
            return partProd;
        }
        return 0;
    }


    private static void parceInput(Engine[] eng){
        int tot = 0;
        int tot2 = 0;
        for(int i = 0; i < len; i++){                   //for each line
            combineTagsForLine(eng, i);
            findParts(eng, i);
            sumParts(eng, i);
            tot += eng[i].partSum;
            tot2 += eng[i].partCnt;
            System.out.println("eng: " + i + "\t" + eng[i].partCnt + "\t"  + eng[i].partSum + "\t" + tot);
        }
    }

    private static void combineTagsForLine(Engine[] eng, int idx){
        int tLen = eng[idx].tags.length;            //set length of line
        boolean[] tmpB = new boolean[tLen];         //clear tmp bool
        int line1 = Math.max(idx - 1, 0);
        int line2 = idx;
        int line3 = Math.min(idx + 1, len - 1);
        for(int t = 0; t < tLen; t++){             //combine tags
            tmpB[t] |= (eng[line1].tags[t]);        //before
            tmpB[t] |= (eng[line2].tags[t]);        //on
            tmpB[t] |= (eng[line3].tags[t]);        //& after
        }
        eng[idx].tagsAll = Arrays.copyOf(tmpB, tLen);
        for(int c = 0; c < tLen; c++) if(tmpB[c]) eng[idx].tagsAllCnt++;
    }


    private static void findParts(Engine[] eng, int idx){
        for(int n = 0; n < eng[idx].num.length; n++){   //for each num on line
            boolean[] tmpB = eng[idx].tagsAll;

            // set the beg & end of tags to check
            int numLoc = eng[idx].numBegIdx[n];
            int numLen = eng[idx].numLen[n];
            int begIdx = numLoc - 1;            //one before the location of the first digit
            int endIdx = numLoc + numLen + 1;   //one after the first digit + length
            if(begIdx < 0) begIdx++;            //if -1 don't look
            if(endIdx >= tmpB.length) endIdx = tmpB.length; //if passed the end

            for(int p = begIdx; p < endIdx; p++){
                eng[idx].numIsPart[n] |= tmpB[p];     //set isPart for this line & num.
            }
        }
    }

    private static void sumParts(Engine[] eng, int idx){
        for(int n = 0; n < eng[idx].num.length; n++){   //for each num on line
            if(eng[idx].numIsPart[n]){
                eng[idx].partSum += eng[idx].num[n];
                eng[idx].partCnt++;
            }
        }
    }

}