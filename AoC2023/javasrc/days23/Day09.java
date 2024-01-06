package days23;

import java.io.IOException;

import type.SandRpt;
import util.ReadInput;

public class Day09 {
    private static String fileInfo[];
    private static int len;
    private static SandRpt[] envRpts;

    /** Constructor, not needed but used for standards. */
    private Day09(){}

    public static void update() throws IOException {
        String fNum = "09"; //Part1- 1882395907   Part2- 1005
        // String fNum = "091";//Part1- 114   Part2- 2
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        envRpts = new SandRpt[len];
        parceInput();

        question1();    //Confirmed: 09- 1882395907   091- 114
        question2();    //Confirmed: 09- 1005   091- 2
    }

    /**
     * Question 1: Analyze your OASIS report and extrapolate the 
     * next value for each history. What is the sum of these extrapolated values?
     */
    private static void question1() {
        int tmp = 0;
        for(int i = 0; i < len; i++) tmp += envRpts[i].extValFwd;
        //Track ,  Confirmed: 09- 1882395907   091- 114
        System.out.println("\nPart 1: Extrapolated forward value: " + tmp);
    }
    
    /**
     * Question 2: Analyze your OASIS report again, this time extrapolating 
     * the previous value for each history. What is the sum of these extrapolated values?
     */
    private static void question2() {
        int tmp = 0;
        for(int i = 0; i < len; i++) tmp += envRpts[i].extValBkwd;
        //Track ,  Confirmed: 09- 1005   091- 2
        System.out.println("\nPart 2: Extrapolated backward value: " + tmp);
    }

    /**
     * Parce the input into an array of SandRpt enviromental data. 
     * <p?Included extrapolated forward and backward values.
     */
    private static void parceInput(){
        for(int i = 0; i < len; i++){
            envRpts[i] = new SandRpt(fileInfo[i]);
        }
    }

}