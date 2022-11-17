package javasrc.days;

import java.io.IOException;

import javasrc.util.IntCode;
import javasrc.util.ReadInput;

public class Day05 {
    private static int[] fileInfo;  //Define array for input type data
    private static int len;         //Length of data

    /**Constructor, not needed but used for standards. */
    private Day05(){}

    public static void update() throws IOException {
        String fNum = "051";
        fileInfo = ReadInput.getInputIntCS(fNum);   //Get input in an array for 2
        len = fileInfo.length;                      //Length of input array
        question1(10);      //Confirmed: 05-???   051-returns input
        question2();
    }

    /**
     * Question 1: Find the results of running code after setting
     * memory[1] & [2] to 12 & 2 respectfully.
     */
    private static void question1(int input) {
        // int[] memory = fileInfo.clone();
        int[] memory = {1, 3, 2, 2, 99};
        int[] info = {input, 0};    //[0]=input, [1]=output
        IntCode.runIntCode(memory, info);

        //Track ,  Confirmed: 05-???   051-returns input
        System.out.println("\nQ1: Value at memory location 0: " + info[1]);
    }

    /**
     * Question 2: Find noun/verb seed pair that produce a result at 
     * memory[0] of 19690720.  Prints answer as noun * 100 + verb.
     * <p>Noun & verb limited to 0 - 99 inclusive.
     */
    private static void question2() {
        //Track ,  Confirmed: 05-???   051-???
        // System.out.println("\nQ2: NounVerb value: " + nounVerb + "\n");
    }

}