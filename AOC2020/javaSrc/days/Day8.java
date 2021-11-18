package days;

import java.io.IOException;
import util.ReadInput;

public class Day8 {

    public static void update() throws IOException {
        String fileInfo[];
        fileInfo = ReadInput.GetInputStr(8);//Get input in an array for 8
        int len = fileInfo.length;          //Length of input array

        int opsAcc[][] = new int[2][len];   //Opcode[0][] / Acc[1][]
        ParceCodes(fileInfo, opsAcc);       //Split input into [0]opCode and [1]acc or jmp value
                                            //Opcodes - 0=acc, 1=jmp, 2=nop

        boolean flipped[] = new boolean[len + 1];   //Part 2 track if flipping op has has been tried
                                                    //extra cell, used if program completes

        int acc = 0;    //Accumlator
        acc =  FindLoop(opsAcc, flipped, false);                //Part 1
        System.out.println("\n\nPart 1, Accumulator - " + acc); //Part 1, confirmed 1709

        for(int i = 0; i < len; i++){                   // Part 2
            acc = FindLoop(opsAcc, flipped, true);      // Part 2
            if(flipped[len]) break; //stop if at end of program[0][617(9)].  Flagged in flipped extra cell
        }

        System.out.println("Part 2, Accumulator - " + acc);     //Part 2, confirmed 1976
    }

    /**
     * Parce input and split to:
     * ops value[0].  0 = add to acc cmd, 1 = jmp to offset cmd, 2 = nop cmd
     * acc value[1], acc value or jmp offset
     * 
     * @param fileInfo      // Input
     * @param opsAcc        // opCode[0][] and acc/jmp[1][] value
     */
    private static void ParceCodes(String fileInfo[], int opsAcc[][]){
        String sTmp = "";

        for( int ndx = 0; ndx < fileInfo.length; ndx++){
            sTmp = fileInfo[ndx].substring(0,3);    //Get opcode
            if(sTmp.contains("acc")){               //0 = acc
                opsAcc[0][ndx] = 0;
            }else if(sTmp.contains("jmp")){         //1 = jmp
                opsAcc[0][ndx] = 1;
            }else{
                opsAcc[0][ndx] = 2;                 //2 = nop
            }
            opsAcc[1][ndx] = Integer.parseInt(fileInfo[ndx].substring(4));   //Get acc value
        }
    }

    //---------------------------  Part 1 and 2 ---------------------------------------------- 

    /**
     * Execute code.  Stop when it starts looping or reaches end of code
     * and return accumlated value to that point.
     * 
     * @param opsAcc    //Array of opCode[0] and acc or jmp[1] values
     * @param flipped   //Has an op been flipped to try to correct.  Part 2
     * @param chkFlip   //Used to run for part 2 only
     * @return
     */
    private static int FindLoop(int opsAcc[][], boolean flipped[], boolean chkFlip){
        int ptr = 0;    //pointer to opsAcc
        int exe = 0;    //execution pointer
        int len = opsAcc[0].length;
        int exeTrkr[] = new int[len];   //Used to track execution repeat
        int acc = 0;    //Accumulation
        int opCode = 2; //op code initialize as nop;

        do{
            if(exeTrkr[ptr] > 0) break; //Check for loop.  Exit if already been here.
            exe++;
            exeTrkr[ptr] = exe;         //Track execution order

            opCode = opsAcc[0][ptr];    //Get opCode
            //Part 2 - Flip if opCode is 1 or 2 and not flipped before AND requested
            if(opCode > 0 && !flipped[ptr] && chkFlip){
                opCode++;                   //Flip opCode 1 to 2 
                if( opCode > 2) opCode = 1; //or 2 to 1
                flipped[ptr] = true;        //Flag this op has been tried
                chkFlip = false;            //Only do 1 per pass
            }

            switch(opCode){ //Execute op code.
                case 0:     //acc - add [1] to accumulator and step 1
                    acc += opsAcc[1][ptr];
                    ptr++;
                break;
                case 1:     //jmp - jump to present location + offset [1]
                    ptr += opsAcc[1][ptr];
                break;
                case 2:     //nop - no operation, just step 1
                    int a = 0;
                    ptr++;
            }
        }while(ptr < len); //until end of program

        //Flag if end of program reached 617(9).
        if(ptr == len) flipped[len] = true;
        return acc;
    }
}