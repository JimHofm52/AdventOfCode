package days21;

import java.io.IOException;
import java.util.Arrays;

import util.ReadInput;

public class Day08 {
    private static String fileInfo[];
    private static int len;
    private static int[][] input;
    private static int[][] output;

    /** Constructor, not needed but used for standards. */
    private Day08(){}

    public static void update() throws IOException {
        String fNum = "082";
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        input = parceIn();
        output = parceOut();

        question1();    //Confirmed: 08-514   081-26
        question2();    //Confirmed: 08-1012272   081-???  082-61229
    }

    /**
     * Question 1: Count how many times digits 1, 4, 7, or 8 appear
     *  in the output values (after the | ).
     */
    private static void question1() {
        int tot = 0;
        int tmp = 0;
        int bits = 0;
        for(int x = 0; x < len; x++){
            for(int w = 0; w < 4; w++){
                tmp = output[x][w];
                bits = cntBits(tmp);
                if(bits == 2 || bits == 4 || bits == 3 || bits == 7) tot++;
            }
        }
        //Track ,  Confirmed: 08-514   081-0   082-26
        System.out.println("\nPart 1: Total for 1, 4, 7 & 8: " + tot);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        int[] segVals;  //List of found values
        boolean allFnd; //Check for all found
        int tryCnt;     //Safety counter, stop looping
        int outNum;     //Decoded output number
        int totNum = 0; //Total of all outputs

        for(int i = 0; i < input.length; i++){  //For each input ex.
            segVals = new int[10];              //Clear values
            tryCnt = 0;                         //Safety jic
            do{                                 //Do until all values found
                for(int n = 0; n < 10; n++){    //For each input
                    updNums(segVals, input[i][n]);  //Update value list
                }
                allFnd = true;                  //Check if all found
                for(int j = 0; j < 10; j++) if(segVals[j] == 0) allFnd = false;
                tryCnt++;
            }while(!allFnd && tryCnt < 10);     //Until all found (or we give up)
            if(tryCnt > 9) System.out.println("Too many input tries!"); //We gave up

            //Decode the outputs and totalize
            outNum = 0;
            for(int n = 0; n < output[0].length; n++){
                for(int j = 0; j < segVals.length; j++){
                    if(output[i][3 - n] == segVals[j]){
                        outNum += Math.pow(10, n) * j;
                    }
                }
            }
            totNum += outNum;
            System.out.println("Ex. " + i + " - " + outNum);
        }
        //Track ,  Confirmed: 08-1012272   082-61229
        System.out.println("\nPart 2: Sum of all examples: " + totNum);
    }

    /**
     * Parce 10 inputs as ints.  a=0, b=1, c=4, d=8, e=16, f=32, g=64
     * <p>First 10 are inputs, up to '|' then 4 outputs to be decoded.
     * <p>acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf
     * @return list of 10 integers
     */
    private static int[][] parceIn(){
        int[][] in = new int[len][10];
        int end;
        String str;
        for(int s = 0; s < len; s++){
            str = fileInfo[s];
            for(int w = 0; w < 10; w++){
                end = str.indexOf(" ");
                for(int c = 0; c < end; c++){
                    in[s][w] += 1 << str.charAt(c) - 'a';
                }
                str = str.substring(end + 1);
            }
            Arrays.sort(in[s]);
        }
        return in;
    }

    /**
     * Parce 4 outputs to be decoded as ints.
     * @return list of 4 integers
     */
    private static int[][] parceOut(){
        int[][] out = new int[len][4];
        int end;
        String str;
        for(int s = 0; s < len; s++){
            str = fileInfo[s];
            end = str.indexOf("|");
            str = str.substring(end + 1).trim();
            for(int w = 0; w < 4; w++){
                end = w < 3 ? str.indexOf(" ") : str.length();
                for(int c = 0; c < end; c++){
                    out[s][w] += 1 << str.charAt(c) - 'a';
                }
                if(w < 3) str = str.substring(end + 1);
            }
        }
        return out;
    }

    /**
     * Decode the input based on unique patterns or combination of patterns 
     * already found.  Grouped by bit count.
     * <p>0b & 1b is n/a.  2b is 1, 3b is 7, 4b is 4, 5b is 2, 3 & 5, 6b is 0, 6, 9
     * <p>If 5b val or'ed w/ 1 is the same val.  Other 2 5b's values change.
     * @param nums List of found numbers
     * @param num The number to be found
     */
    private static void updNums(int[] nums, int num){
        int tmp = cntBits(num);
        switch(tmp){
            case 0:
            case 1:
            System.out.println("Illegal bit count for num " + num);
            break;
            case 2:         //2b, Num 1, std ce, 20
            nums[1] = num;  //Only 2bit number
            break;
            case 3:         //3b, Num 7, std acf, 21
            nums[7] = num;  //Only 3 bit number
            break;
            case 4:         //4b, Num 4, std bcdf. 46
            nums[4] = num;  //Only 4 bit number
            break;
            case 5:         //5b, Num 2, 3, 5
            if(chk3(nums, num)){
                nums[3] = num;
            }else if(chk5(nums, num)){
                nums[5] = num;
            }else{
                if(chk2(nums, num)) nums[2] = num;  //Last 5 bit number
            }
            break;
            case 6:     //6b, Num 0, 6, 9
            if(chk0(nums, num)){
                nums[0] = num;
            }else if(chk6(nums, num)){
                nums[6] = num;
            }else{
                if(chk9(nums, num)) nums[9] = num;
            }
            break;
            case 7:     //7b, Num 8, std abcdefg, 127
            nums[8] = num;
            break;
            default:
            System.out.println("Illegal bit count for num " + num);
        }
    }

    /**
     * Match pattern for 3.  Check other, if matched this must be 2.
     * @param vals List of found values.
     * @param val Value to match
     * @return
     */
    private static boolean chk2(int[] vals, int val){
        if(vals[2] == val) return true;         //Already found
        return (vals[3] != 0 && vals[5] != 0);  //Other 5 bit found, last one
    }

    /**Match pattern for 9 using found 1 segments */
    private static boolean chk3(int[] vals, int val){
        if(vals[3] == val) return true;         //Already found
        return ((val | vals[1]) == val);        //Only 5 bit val or'ed w/ 1 still .EQ. val
    }

    /**Match pattern for 9 using found 0 & 4 segments */
    private static boolean chk5(int[] vals, int val){
        if(vals[5] == val) return true;         //Already found
        if((vals[1] == 0) || (vals[4] == 0)) return false;  //Need 1 & 4 to eval 5
        int tmp = vals[4] & ~vals[1];       //Mask out 1, remaining left segments of 4
        return ((tmp | val) == val);        //Only 5 bit that matches left segments of 4
    }

    /**Match pattern for 0 using found 5 segments */
    private static boolean chk0(int[] vals, int val){
        if(vals[0] == val) return true;     //Already found
        if(vals[5] == 0) return false;      //Need 5 to eval 0
        return ((vals[5] | val) == 127);    //0 or'ed w/5 is 8
    }

    /**Match pattern for 6 using found 1 segments */
    private static boolean chk6(int[] vals, int val){
        if(vals[6] == val) return true;     //Already found
        if(vals[1] == 0) return false;      //Need 1 to eval 0
        return ((vals[1] | val) == 127);    //6 or'ed w/1 is 8
    }

    /**Match pattern for 9 using found 1 & 5 segments */
    private static boolean chk9(int[] vals, int val){
        if(vals[9] == val) return true;     //Already found
        if((vals[5] == 0) || (vals[1] == 0)) return false;  //Need 5 & 1 to eval 9
        int tmp = vals[5] | vals[1];        //Or segments for 1 & 5, forms a 9
        return (tmp == val);                //6 bit that matches segments for 1 & 5
    }

    /**
     * @param num To evaluate
     * @return Count the bits in an integer.
     */
    private static int cntBits(int num){
        int bits = 0;
        for(int b = 0; b < 7; b++){
            if(num % 2 == 1) bits++;
            num = num >> 1;
        }
        return bits;
    }

}