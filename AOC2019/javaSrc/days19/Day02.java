package days19;

import java.io.IOException;
import util.ReadInput;
import util.IntCode;

public class Day02 {
    private static int[] fileInfo;  //Define array for input type data
    private static int len;         //Length of data
    private static int[] inOutput = {0,0};
    private static int rslt = 0;

    /**Constructor, not needed but used for standards. */
    private Day02(){}

    /**
     * Create a Integer Code Computer, Intcode.  Then use it to calc the gravity assist.
     * @throws IOException - ???
     */
    public static void update() throws IOException {
        fileInfo = ReadInput.getInputIntCS("02");   //Get input in an array for 2
        len = fileInfo.length;                      //Length of input array
        question1();    //Track 520625 too low,  Confirmed 3760627
        question2();    //Track ,  Confirmed 7195
    }

    /**
     * Question 1: Find the results of running code after setting
     * memory[1] & [2] to 12 & 2 respectfully.
     */
    private static void question1() {
        int[] memory = fileInfo.clone();    //Make a clone, need same copy in Q2.
        memory[1] = 12;
        memory[2] = 2;
        rslt = IntCode.runIntCode(memory, inOutput);

        //Track 520625 too low,  Confirmed 3760627
        if(rslt == 0){
            System.out.println("\nQ1: Value at memory location 0: " + memory[0]);
        }else{
            System.out.println("\nQ1: Errod occured in IntCode: " + rslt);
        }
    }

    /**
     * Question 2: Find noun/verb seed pair that produce a result at 
     * memory[0] of 19690720.  Prints answer as noun * 100 + verb.
     * <p>Noun & verb limited to 0 - 99 inclusive.
     */
    private static void question2() {
        int noun = 0;
        int verb = 0;
        int nounVerb = 0;
        boolean fnd = false;
        do{     //noun 0 to 99
            verb = 0;
            do{     //verb 0 to 99
                int[] memory = fileInfo.clone();    //reset memory
                memory[1] = noun;                   //seed loc 1
                memory[2] = verb;                   //& 2
                rslt = IntCode.runIntCode(memory, inOutput);                 //run code
                if(memory[0] == 19690720) fnd = true;   //chk if answer found
                if(!fnd) verb++;                    //if not found, incr verb
            }while(!fnd && verb < 100);
            if(!fnd) noun++;                        //if still not fnd, incr noun & do again
        }while(!fnd && noun < 100);
        nounVerb = noun * 100 + verb;               //When fnd, merge & print.
        //Track ,  Confirmed 7195
        if(rslt == 0){
            System.out.println("\nQ2: NounVerb value: " + nounVerb + "\n");
        }else{
            System.out.println("\nQ2: Error in IntCode: " + rslt + "\n");
        }
    }

    // /**
    //  * Run int code computer against instructions passed in memory.
    //  * @param memory per loaded with code to execute
    //  * @return
    //  */
    // private static int runIntCode(int[] memory){
    //     int instrPtr = 0;
    //     int opCode, rslt;
    //     int[] parm;

    //     do{
    //         opCode = (instrPtr < 0 || instrPtr > len) ? 100 :  memory[instrPtr];   //Is ptr in memory range?
    //         switch(opCode){
    //             case 1:     //Add next 2, ptr+1 + ptr+2
    //             parm = getParms(instrPtr, 4, memory);   //ret opcode + parms
    //             memory[parm[3]] = memory[parm[1]] + memory[parm[2]];
    //             instrPtr += 4;
    //             rslt = 1;
    //             break;
    //             case 2:     //Multiply next 2, ptr+1 + ptr+2
    //             parm = getParms(instrPtr, 4, memory);
    //             memory[parm[3]] = memory[parm[1]] * memory[parm[2]];
    //             instrPtr += 4;
    //             rslt = 1;
    //             break;
    //             case 99:    //Stop execution
    //             instrPtr++;
    //             rslt = 0;
    //             break;
    //             case 100:   //Illegal opCode pointer, not between 0 and end of memory (len).
    //             System.out.println("Illegal opcode pointer: " + instrPtr);
    //             rslt = -1;
    //             break;
    //             default:    //Illegal opCode, not 1, 2 or 99 (at this time).
    //             System.out.println("Bad opcode: " + instrPtr + " at: " + memory[instrPtr]);
    //             rslt = -2;
    //         }
    //     }while(rslt > 0);
    //     return rslt;
    // }

    // /**
    //  * Get opCode + needed parms for this opCode.
    //  * @param iptr instruction pointer
    //  * @param cnt count of opCode + parms
    //  * @param mem memory to use.
    //  * @return an array of opCode + parms.
    //  */
    // private static int[] getParms(int iptr, int cnt, int[] mem){
    //     int[] p = new int[cnt];
    //     for(int i = 0; i < cnt; i++) p[i] = mem[iptr + i];
    //     return p;
    // }
}