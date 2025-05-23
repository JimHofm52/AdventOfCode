package days19;

import java.io.IOException;

import util.IntCode;
import util.ReadWriteFiles;

public class Day05 {
    private static int[] fileInfo;  //Define array for input type data
    private static int len;         //Length of data

    /**Constructor, not needed but used for standards. */
    private Day05(){}

    public static void update() throws IOException {
        // String fNum = "051";        //Test-Rtn input, Q1 only
        // String fNum = "052";        //Test-Rtn 999 if < 8, 1000 if = 8, & 1001 > 8, Q2 only
        String fNum = "05";
        fileInfo = ReadWriteFiles.getInputIntCS(fNum);   //Get input in an array for 2
        len = fileInfo.length;                      //Length of input array
        question1(1);       //Confirmed: 05-15426686   051-returns input
        question2(5);       //Confirmed: 05-11430197
    }

    /**
     * Question 1: Find the output (info[1]) after setting input to 1
     * and running code/
     */
    private static void question1(int input) {
        int[] memory = fileInfo.clone();
        int[] info = {input, 0};    //[0]=input, [1]=output
        int rslt = IntCode.runIntCode(memory, info);

        //Track ,  Confirmed: 05-returns 15426686 confirmed, 051-returns input confirmed
        if(rslt == 0){
            System.out.println("\nQ1: Value of last ouput:: " + info[1]);
        }else{
            System.out.println("\nQ1: Error occured in IntCode: " + rslt);
        }
    }

    /**
     * Question 2: 
     */
    private static void question2(int input) {
        // int[] memory = {3,9,8,9,10,9,4,9,99,-1,8};  //Rtn 1 if input redir == 8 else 0
        // int[] memory = {3,9,7,9,10,9,4,9,99,-1,8};  //Rtn 1 if input redir < 8 else 0
        // int[] memory = {3,3,1108,-1,8,3,4,3,99};    //Rtn 1 if input dir == 8 else 0
        // int[] memory = {3,3,1107,-1,8,3,4,3,99};    //Rtn 1 if input dir < 8 else 0
        // int[] memory = {3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9};//Rtn 0 if input redir == 0 else 1
        // int[] memory = {3,3,1105,-1,9,1101,0,0,12,4,12,99,1};  //Rtn 0 if input dir == 0 else 1
        int[] memory = fileInfo.clone();
        int[] info = {input, 0};    //[0]=input, [1]=output
        int rslt = IntCode.runIntCode(memory, info);

        //Track 15426686 hi,  Confirmed: 11430197
        if(rslt == 0){
            System.out.println("\nQ2: Value of last ouput: " + info[1]);
        }else{
            System.out.println("\nQ1: Error occured in IntCode: " + rslt);
        }
    }

}