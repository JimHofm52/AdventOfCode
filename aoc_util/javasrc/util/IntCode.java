package util;

public class IntCode {

    /**Constructor, not needed for standard only. */
    private IntCode(){}

    /**
     * @param memory pre-loaded with code to execute
     * @param inOut is array for input[0] & output[1]
     * @return result 0=done, -1=Illegal memory ref, -2=Illegal opcode (not 1 to 4 or 99)
     * <p>
     * <p>Run int code computer against instructions passed in memory.
     * <p> Total opcode (ABCDE), digits D & E are the actual opcode, 1 to 4 & 99.
     * <p> Digits C, B & A are modes for parms 1, 2 & 3.
     * <p> Mode digit value 0 means use next parm as a ptr.  A 1 as an immediate value.
     * <p> opcode 1 - add values using the next 2 parms & store using the 3rd
     * <p> opcode 2 - mult values using the next 2 parms & store using the 3rd
     * <p> opcode 3 - store the value passed(?) using the next parm.  3,50 store ?? at 50 (or=>50)
     * <p> opcode 4 - output(??) the value using the next parm.  4,50 output 50 (or+>50)
     * <p> opcode 99 - end execution
     * <p> Examples
     * <p>ModeABC/Op - parm1 mode        +  parm2 mode        => parm3 mode
     * <p>    000/01 - pointer to value  +  pointer to value  => pointer to storage
     * <p>    001/02 - value in 1st loc  *  pointer to value  => pointer to storage
     * <p>    010/01 - pointer to value  +  value in 2nd loc  => pointer to storage
     * <p>    011/02 - value in 1st loc  +  value in 2nd loc  => pointer to storage
     * <p>    111/01 - value in 1st loc  +  value in 2nd loc  => output
     * <p>    x00/03 - pointer to value  => pointer to storage
     * <p>    x11/03 - value in 1st loc  => output
     */
    public static int runIntCode(int[] memory, int[] inOut) {
        int instrPtr = 0;
        int opCode, parmModes, rslt, tmp;
        int[] parm;    //[0]opcode, parm[1], parm2[2], parm[3]
        int[] reg = new int[2];

        do{
            opCode = memory[instrPtr] % 100;    //Get opcode
            parmModes = memory[instrPtr] / 100; //Get modes, strip opcode
            switch(opCode){
                case 1: //add values pointed to by next 2 parms & store in loc pointed to in 3
                parm = getParms(instrPtr, 4, memory);
                reg[0] = parmModes % 10 == 1 ? parm[1] : memory[parm[1]];
                parmModes /= 10;    //Strip C mode
                reg[1] = parmModes % 10 == 1 ? parm[2] : memory[parm[2]];
                parmModes /= 10;    //Strip B mode
                reg[0] += reg[1];   //Value to save

                if((parmModes % 10) > 0) {
                    inOut[1] = reg[0];
                }else{
                    memory[parm[3]] = reg[0];
                }
                instrPtr += 4;
                rslt = 1;
                break;
                case 2: //mult values pointed to by next 2 parms & store in loc pointed to in 3
                parm = getParms(instrPtr, 4, memory);
                reg[0] = parmModes % 10 == 1 ? parm[1] : memory[parm[1]];
                parmModes /= 10;    //Strip C mode
                reg[1] = parmModes % 10 == 1 ? parm[2] : memory[parm[2]];
                parmModes /= 10;    //Strip B mode
                reg[0] *= reg[1];   //Value to save

                if((parmModes % 10) > 0) {
                    inOut[1] = reg[0];
                }else{
                    memory[parm[3]] = reg[0];
                }
                instrPtr += 4;
                rslt = 1;
                break;
                case 3: //Store value using next.  3,50 store input at mem[50] / 103,XX .. at XX
                parm = getParms(instrPtr, 2, memory);
                reg[0] = inOut[0];  //Value to save
                reg[1] = (parmModes % 10) == 1 ? instrPtr + 1 : parm[1];    //Save Loc
                memory[reg[1]] = reg[0];
                instrPtr += 2;
                rslt = 1;
                break;
                case 4: //Retrieve value using next.  4,50 output mem[50] / 104,50 output 50
                parm = getParms(instrPtr, 2, memory);
                inOut[1] = parmModes % 10 == 1 ? parm[1] : memory[parm[1]];
                instrPtr += 2;
                rslt = 1;
                break;
                case 5: //Jump if false, if next parm is not zero set iPtr parm[2] else iPtr + 3
                parm = getParms(instrPtr, 3, memory);
                reg[0] = parmModes % 10 == 1 ? parm[1] : memory[parm[1]];   //Value to evaluate
                parmModes /= 10;
                reg[1] = parmModes % 10 == 1 ? parm[2] : memory[parm[2]];   //adr to possibly store to
                instrPtr = reg[0] != 0 ? reg[1] : instrPtr + 3;
                rslt = 1;
                break;
                case 6: //Jump if true, if parm1 == 0 set iPtr to parm2 else iPtr + 3
                parm = getParms(instrPtr, 3, memory);
                reg[0] = parmModes % 10 == 1 ? parm[1] : memory[parm[1]];   //Value to evaluate
                parmModes /= 10;
                reg[1] = parmModes % 10 == 1 ? parm[2] : memory[parm[2]];   //adr to possibly store to
                instrPtr = reg[0] == 0 ? reg[1] : instrPtr + 3;
                rslt = 1;
                break;
                case 7: //If parm1 < parm2 store 1 in parm3 else store 0.
                parm = getParms(instrPtr, 4, memory);
                reg[0] = parmModes % 10 == 1 ? parm[1] : memory[parm[1]];   //Compare Value 1
                parmModes /= 10;
                reg[1] = parmModes % 10 == 1 ? parm[2] : memory[parm[2]];   //Compare Value 2
                parmModes /= 10;
                reg[0] = reg[0] < reg[1] ? 1 : 0;                       //Value to store
                reg[1] = parmModes % 10 == 1 ? instrPtr + 1 : parm[3];  //Storage loc.
                memory[reg[1]] = reg[0];    //Store it
                instrPtr += 4;
                rslt = 1;
                break;
                case 8: //If parm1 == parm2 store 1 else store 0 in parm3.
                parm = getParms(instrPtr, 4, memory);
                reg[0] = parmModes % 10 == 1 ? parm[1] : memory[parm[1]];   //Compare value 1
                parmModes /= 10;
                reg[1] = parmModes % 10 == 1 ? parm[2] : memory[parm[2]];   //Compare vaue 2
                parmModes /= 10;
                reg[0] = reg[0] == reg[1] ? 1 : 0;                      //Value to store
                reg[1] = parmModes % 10 == 1 ? instrPtr + 1 : parm[3];  //mem loc.
                memory[reg[1]] = reg[0];    //Store it
                instrPtr += 4;
                rslt = 1;
                break;
                case 99:    //Stop execution
                instrPtr++;
                rslt = 0;
                break;
                case 100:   //Illegal opCode pointer, not between 0 and end of memory (len).
                System.out.println("Illegal opcode pointer: " + instrPtr);
                rslt = -1;
                break;
                default:    //Illegal opCode, not 1 to 4 or 99 (at this time).
                System.out.println("Bad opcode: " + instrPtr + " at: " + memory[instrPtr]);
                rslt = -2;
            }
        }while(rslt > 0);
        return rslt;
    }

    // /**
    //  * Run int code computer against instructions passed in memory.
    //  * @param memory per loaded with code to execute
    //  * @return
    //  */
    // public static int runIntCode(int[] memory){
    //     int[] info = {0,0};
    //     return runIntCode(memory, info);
    // }

    /**
     * Get opCode + needed parms for this opCode.
     * @param iptr instruction pointer
     * @param cnt count of opCode + parms
     * @param mem memory to use.
     * @return an array of opCode + parms values
     */
    private static int[] getParms(int iptr, int cnt, int[] mem){
        int[] p = new int[cnt];
        for(int i = 0; i < cnt; i++) p[i] = mem[iptr + i];
        return p;
    }

}