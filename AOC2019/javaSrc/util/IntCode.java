package javasrc.util;

public class IntCode {

    /**Constructor, not needed for standard only. */
    private IntCode(){}

    /*
     *   opcode  - parm1             +  parm2             => parm3
     * <p>     1 - value pointed to  +  value pointed to  => pointed to in 3
     * <p>   101 - value in 1st loc  +  value pointed to  => loc pointed to.
     * <p>  1001 - value pointed to  +  value in 2nd loc  => loc pointed to.
     * <p>  1101 - value in 1st loc  +  value in 2nd loc  => loc pointed to.
     * <p> 11101 - value in 1st loc  +  value in 2nd loc  => output.
     * @param memory per loaded with code to execute
     * @return
     */
    /**
     * Run int code computer against instructions passed in memory.
     * <p> opcode 1 - add values pointed to by next 2 parms & store in loc pointed to in 3
     * <p>   11 - value in next loc + value pointed to => loc pointed to.
     * <p>  111 - value in next loc + value in 2nd loc => loc pointed to.
     * <p>  101 - value pointed to + value pointed to => loc pointed to.
     * <p> 1111 - value in next loc + value in 2nd loc => output.
     * @param memory per loaded with code to execute
     * @return
     */
    public static int runIntCode(int[] memory, int[] inOut) {
        int instrPtr = 0;
        int opCode, parmMode, rslt, tmp;
        int[] parm;
        int[] val;
        int len = memory.length;

        do{
            opCode = memory[instrPtr] % 100;
            parmMode = memory[instrPtr] / 100;
            switch(opCode){
                case 1:     //Add next 2, ptr+1 + ptr+2 and store in ptr+3
                val = getValues(instrPtr, 3, memory, parmMode);
                tmp = val[0] + val[1];

                parmMode /= 100;
                if((parmMode) > 0) {
                    inOut[1] = tmp;
                }else{
                    memory[instrPtr + 2] = tmp;
                }
                instrPtr += 4;
                rslt = 1;
                break;
                case 2:     //Multiply next 2, ptr+1 + ptr+2 and store in ptr+3
                parm = getParms(instrPtr, 4, memory);
                memory[parm[3]] = memory[parm[1]] * memory[parm[2]];
                instrPtr += 4;
                rslt = 1;
                break;
                case 3:     //Store input at redirect ptr+1
                parm = getParms(instrPtr, 2, memory);
                memory[parm[1]] = inOut[0];
                instrPtr += 2;
                rslt = 1;
                break;
                case 4:     //Retrieve value at redirect ptr+1
                parm = getParms(instrPtr, 2, memory);
                inOut[1] = memory[parm[1]];
                instrPtr += 2;
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
                default:    //Illegal opCode, not 1, 2 or 99 (at this time).
                System.out.println("Bad opcode: " + instrPtr + " at: " + memory[instrPtr]);
                rslt = -2;
            }
        }while(rslt > 0);
        return rslt;
    }

    /**
     * Run int code computer against instructions passed in memory.
     * @param memory per loaded with code to execute
     * @return
     */
    public static int runIntCode(int[] memory){
        int[] info = {0,0};
        return runIntCode(memory, info);
    }

    private static int getOpcode(int[] memory, int instrPtr, int len){
        if(instrPtr < 0 || instrPtr > len) return 100;
        if(memory[instrPtr] % 100 == 99) return 99;   //Is ptr in memory range?
        return memory[instrPtr] % 10;
    }

    /**
     * Get opCode + needed parms for this opCode.
     * @param iptr instruction pointer
     * @param cnt count of opCode + parms
     * @param mem memory to use.
     * @return an array of opCode + parms values
     */
    private static int[] getValues(int iPtr, int cnt, int[] mem, int pMode){
        int[] p = new int[cnt];
        int tmpP;
        iPtr++;
        for(int i = 0; i < cnt; i++) {
            tmpP = mem[iPtr + i];
            p[iPtr + i] = pMode % 10 > 0 ? mem[tmpP] : tmpP;
            pMode /= 10;
        }
        return p;
    }

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