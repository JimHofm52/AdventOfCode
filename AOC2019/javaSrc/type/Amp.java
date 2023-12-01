package type;

import java.util.Arrays;

import util.IntCode;

/** Type Amp */
public class Amp{
    private int[] code;  //code to load to memory
    private int phase;   //first input
    private int pwr;     //second input
    private int[] ioAmp = {0, 0, 0};
    
    public Amp(int[] inCode, int inPhase, int inPwr){
        this.code = Arrays.copyOf(inCode, inCode.length);
        this.ioAmp[0] = inPhase;
        this.ioAmp[1] = inPwr;
        this.phase = inPhase;
        this.pwr = inPwr;
    }

    public void setPwr(int p){
        ioAmp[1] = p;
    }

    public int runAmp(){
        IntCode.runIntCode(code, ioAmp);
        return ioAmp[ioAmp.length - 1];
    }
}
