package days;

import java.io.IOException;
import util.ReadInput;

public class Day9 {

    public static void update() throws IOException {
        long numList[] = ReadInput.GetInputLong(9); //Get input in an array for 9(91)
        int len = numList.length;                   //Length of input array

        //------------------------------ part 1 -----------------------------------
        int preLen = 25;        //Preamble length 25(5)
        boolean numOK = true;   //Is number OK
        int startNdx = -1;      //Where to start the search
        do{
            startNdx++;
            numOK = ChkNum( numList, preLen, startNdx);
        }while( numOK && startNdx < (len - preLen));    //continue until num bad

        System.out.println("\nFirst Bad Number -\t" + numList[preLen + startNdx] +
                             " at " + (startNdx + preLen)); //confirmed 36845998 at 533

        //------------------------------ part 2 -----------------------------------
        int ndxLoHi[] = new int[2];     //low[0] & hi[1] index of numList
        FindRng(numList, startNdx + preLen, ndxLoHi);   //find continuous range that adds to bad num
        System.out.println("\nRange index low -\t" + ndxLoHi[0] + "\thigh - " + ndxLoHi[1]);
        //Find the sum of the lowest & highest values in the range.
        System.out.println("\nBad Number Correction -\t" + FindLoHiSum(numList, ndxLoHi));
        //confirmed range index low - 418 to high - 434. Sum of lo + hi values in range is 4830226
    }

    //------------------------------ Part 1 --------------------------------

    private static boolean ChkNum( long numList[], int preLen, int startNdx){
        boolean chkNum = false;     //Check number status
        long num = numList[preLen + startNdx];  //num to chk if 2 values add to it
        long num1, num2;            //2 numbers

        int i = startNdx - 1, j;
        do{                         //Loop thru first num
            i++;
            num1 = numList[i];
            j = i;
            do{                     //plus all other nums
                j++;
                num2 = numList[j];
                if( num == (num1 + num2)) chkNum = true;    //Chk num == sum
            }while(!chkNum && j < preLen + startNdx);   //continue until pair found or EOL
        }while(!chkNum && i < preLen + startNdx);   //try starting with next until pair found or EOL

        return chkNum;  //return status, false = no pair found, true = pair found
    }

    //------------------------------ Part 2 --------------------------------

    private static boolean FindRng( long numList[], int findNdx, int ndxLoHi[]){
        boolean chkNum = false;
        long num = numList[findNdx];
        long sum;

        int i = -1, j;
        do{
            i++;
            sum = numList[i];
            j = i;
            do{
                j++;
                sum += numList[j];
                if( sum == num ) chkNum = true;
            }while(!chkNum && sum < num && j < findNdx);
        }while(!chkNum && i < findNdx);
        ndxLoHi[0] = i;
        ndxLoHi[1] = j;
        return chkNum;
    }
    
    private static long FindLoHiSum(long numList[], int ndxLoHi[]){
        long numLo = numList[ndxLoHi[0]];
        long numHi = numLo;

        for(int i = ndxLoHi[0]; i < ndxLoHi[1] + 1; i++){
            if(numList[i] < numLo ) numLo = numList[i];
            if(numList[i] > numHi ) numHi = numList[i];
        }

        return numHi + numLo;
    }
}