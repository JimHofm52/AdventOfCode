package days;

import java.io.IOException;
import util.*;

public class Day14 {

    public static void update() throws IOException {
        int fileNum = 14;                   //File number of input
        String readIn[] = ReadInput.GetInputStr(fileNum);// Get input in an array for 14(141)
        int len = readIn.length;            //number of masks + adrs
        long inData[][] = new long[len][3]; //Mask/Mem, [0]=0's/mem, [1]=1's/val, [2]=X's/isAdr=-1
        ParceIn(readIn, inData);            //Convert string in to coded numeric data

        for(int i = 0; i < inData.length; i++) if(!(inData[i][2] < 0)) len--;   //reduce len by mask cnt
        // ---- Part 1 ------
        long adrVal[][] = new long[len][2];     //All adrs, [][0]=adr, [][1]=value
        ApplyMasks(inData, adrVal, true);       //Apply masks to values
        long ans = FindUnqAndSum(adrVal);       //Find all ubique adrs and fill with last value
        System.out.println("\nPart 1 - " + ans);// Confirmed - Part 1 - 14925946402938(141 - 165)

        // ----- Part 2 ------
        adrVal = new long[CntPoss(inData)][2];  //Array for all Possible adrs
        ApplyMasks(inData, adrVal, false);      //Apply masks to adrs
        ans = FindUnqAndSum(adrVal);            //Find all ubique adrs and fill with last value
        System.out.println("\nPart 2 - " + ans);// Confirmed - Part 2 - 3706820676200(142 - 208)
                                                //Failed, 3535826301242 - Lo //3888663388352 - Hi
    }

    // ----------------------------------- Parce Input ----------------------------------

    /**
     * Convert String type to numbers
     * 
     * @param readIn
     * @param inData
     */
    private static void ParceIn(String readIn[], long inData[][]){
        int beg = 0;

        for(int i = 0; i < readIn.length; i++) {
            beg = readIn[i].indexOf("mask");
            if( beg > -1){
                ParceMask(readIn[i], inData, i);
            }else{
                ParceMemVal(readIn[i], inData, i);
            }
        }
    }

    /**
     * Parce string value as mask.  "mask = XX001001X10X110X0001111001110X110101"
     * '0', set bits in inData[][0]
     * '1', set bits in inData[][1]
     * 'X', set bits in inData[][2]
     * 
     * @param readIn
     * @param inData
     * @param ndx
     */
    private static void ParceMask(String readIn, long inData[][], int ndx){
        String str = readIn.substring(7);   //Get val after "mask = "
        long bit = (1L << 35);
        char ch = '0';
        for(int i = 0; i < str.length(); i++){
            ch = str.charAt(i);
            if(ch =='0') inData[ndx][0] |= bit;    //Used to AND mem Value if Part 1
            if(ch =='1') inData[ndx][1] |= bit;    //Used to OR mem value part 1. adr in part 2
            if(ch =='X') inData[ndx][2] |= bit;    //Used to mod mem adr in Part 2
            bit = bit >> 1;
        }
    }

    /**
     * Parce string value as mem/value and mem flag.  "mem[3250] = 4436"
     * mem = inData[0] = 3250
     * Val = inData[1] = 4436
     * isMem = inData[2] = -1
     * @param readIn
     * @param inData
     * @param ndx
     */
    private static void ParceMemVal(String readIn, long inData[][], int ndx){
        int beg = readIn.indexOf("[") + 1;
        int end = readIn.indexOf("]");
        inData[ndx][0] = Long.parseLong(readIn.substring(beg, end));    //Save adr
        inData[ndx][1] = Long.parseLong(readIn.substring(end + 4));     //Save value
        inData[ndx][2] = -1;                                            //Flag as mem
    }

    // ----------------------------------- Part 1 & 2 ----------------------------------

    /**
     * Apply masks to all respective values
     * part1 true, apply to values.
     * part1 false, apply to addresses.
     * 
     * @param inData
     * @param adrVal
     * @param part1
     */
    private static void ApplyMasks(long inData[][], long adrVal[][], boolean part1){
        int maskNdx = 0;
        int mvNdx = 0;
        for(int adrNdx = 0; adrNdx < inData.length; adrNdx++){
            if(!(inData[adrNdx][2] < 0L)){   //Chk if mask
                    maskNdx = adrNdx;        //active mask, index to mask inData
            }else{
                if(part1){
                    adrVal[mvNdx][0] = inData[adrNdx][0];                       //Save adr
                    adrVal[mvNdx][1] = inData[adrNdx][1] & ~inData[maskNdx][0]; //AND val with 0's in mask (complement)
                    adrVal[mvNdx][1] |= inData[maskNdx][1];                     //OR with 1's in mask
                    mvNdx++;
                }else{
                    adrVal = EvalMem(inData, maskNdx, adrNdx, adrVal); //Apply mask ndx to adr
                }
            }
        }
    }

    /**
     * Find matching adrs and zero value for earilest then sum what's left.
     * 
     * @param adrVal
     * @return
     */
    private static long FindUnqAndSum(long adrVal[][]){
        for(int i = 0; i < adrVal.length; i++){
            for(int j = i + 1; j < adrVal.length; j++){
                if(adrVal[i][0] == adrVal[j][0]) adrVal[i][1] = 0;   //same mem adr, set value to 0
            }
        }

        //Sum all values
        long sum = 0L;
        for(int i = 0; i < adrVal.length; i++) sum += adrVal[i][1];

        return sum;
    }

    // ----------------------------------- Part 2 ----------------------------------

    /**
     * Apply mask to a mem location.  Expand adr by 2^XCnt.
     * 
     * @param inData
     * @param maskNdx
     * @param memNdx
     * @param memVal
     * @return
     */
    private static long[][] EvalMem(long inData[][], int maskNdx, int adrNdx, long adrVal[][]){
        int ndx = 0;    //Index for memVal
        for( ; ndx < adrVal.length; ndx++ ) if(adrVal[ndx][0] == 0) break;  //Find next open adr

        long baseAdr = inData[adrNdx][0];   //Base adr
        baseAdr |= inData[maskNdx][1];      //OR adr with 1's in mask
        baseAdr &= ~inData[maskNdx][2];     //AND with X's in mask (complement)

        int cntX = CntXs(inData[maskNdx][2]);   //count X's in mask
        cntX = 1 << cntX;                       //new adrs = 2 ^ cntX

        long xs = 0;
        do{                                         //Create an adr for each X combo
            adrVal[ndx][0] = baseAdr | xs;          //OR in X binary for new adr
            adrVal[ndx][1] = inData[adrNdx][1];     //Copy Value
            ndx++;                                  //Next mem index
            xs = incrXs(xs, inData[maskNdx][2]);    //Incr next X combo
        }while(xs != 0);
        return adrVal;
    }

    /**
     * Simple count of X's in 36 bit mask.
     * 
     * @param mask
     * @return
     */
    private static int CntXs(long mask){
        int cnt = 0;
        long bitMask = 1L;
        for(int i = 0; i < 36; i++){
            if((mask & bitMask) > 0) cnt++;
            bitMask = bitMask << 1;
        }
        return cnt;
    }

    /**
     * Increment thru the X's which are seperated.  Add 1 to lowest X and carry,  Repeat thru X's.
     *  
     * @param xs
     * @param xMask
     * @return  incremented mask
     */
    private static long incrXs(long xs, long xMask){
        boolean carry = true;
        int i = 0;
        long bit = 1L;
        do{
            if((bit & xMask) > 0){      //If mask bit a 1 incr Xs
                if((bit & xs) > 0){     //and bit is a 1
                    xs &= ~(bit);           //zero that Xs bit (comp bit mask)
                    carry = true;           //flag need to chk next
                }else{                  //else bit is 0
                    xs |= (bit);        //just set bit
                    carry = false;      //and stop doing more
                }
            }
            bit = bit << 1;
            i++;    //Counter
        }while( i < 36 && carry);   //Do all 36 bit or carry
        return xs;
    }

    /**
     * Count all possible adrs for part 2.  2^X's * grp of adrs
     * 
     * @param inData
     * @return
     */
    private static int CntPoss(long inData[][]){
        int xCnt = 0;
        int memCnt = 0;
        int totCnt = 0;
        for(int i = 0; i < inData.length; i++){
            if(!(inData[i][2] < 0)){
                totCnt += (1 << xCnt) * memCnt;
                xCnt = CntXs(inData[i][2]);
                memCnt = 0;
            }else{
                memCnt++;
            }
        }
        totCnt += (1 << xCnt) * memCnt;
        return totCnt;
    }
}