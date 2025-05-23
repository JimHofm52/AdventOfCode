package days24;

import java.io.IOException;

import type.Equation;
import util.ReadWriteFiles;

public class Day07 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started May 23, 2025,  Finished on May 23, 2025.
     * <p>Took ~12 hour.  Runtime 1.646 S.
    */
    private Day07(){}

    public static void update() throws IOException {
        String fNum = "07";//Part1- 1708857123053   Part2- 189207836795655
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        Equation[] eq = new Equation[len];
        for(int i = 0; i < len; i++){
            eq[i] = new Equation(fileInfo[i]);
        }

        question1(eq);    //Confirmed: 07- 1708857123053 lo-28954329702   071- 3749
        question2(eq);    //Confirmed: 07- 189207836795655   071- 11387
    }

    /**
     * Question 1: Using your new knowledge of elephant hiding spots, 
     * determine which equations could possibly be true using the new 
     * concoctenation op, 12 || 345 = 12345.  What is their total calibration result?
     */
    private static void question1(Equation[] eqIn) {
        long totEq = 0;
        for(Equation eq : eqIn){
            if(eq.isEqOK1()) totEq += eq.getAnswer();
        }
        //Track ,  Confirmed: 07- 1708857123053  lo-28954329702  071- 3749
        System.out.println("\nPart 1: Total of OK equations: " + totEq);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2(Equation[] eqIn) {
        long totEq = 0;
        for(Equation eq : eqIn){
            if(eq.isEqOK2()) totEq += eq.getAnswer();
        }
        //Track ,  Confirmed: 07- 189207836795655   071- 11387
        System.out.println("\nPart 2: Total of OK equations: " + totEq);
    }
}