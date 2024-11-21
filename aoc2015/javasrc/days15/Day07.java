package days15;

import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;

import type.Wire;
import type.WireInstr;
import util.ReadInput;

public class Day07 {
    private static String fileInfo[];
    private static int len;
    private static WireInstr[] wInstr;
    private static Wire[] wire = new Wire[0];

    /** Constructor, not needed but used for standards.
     * This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates! 
     * Unfortunately, little Bobby is a little under the recommended age range, and he needs 
     * help assembling the circuit.
     * 
     * Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a 
     * number from 0 to 65535). A signal is provided to each wire by a gate, another wire, or 
     * some specific value. Each wire can only get a signal from one source, but can provide 
     * its signal to multiple destinations. A gate provides -no signal- until all of its inputs 
     * have a signal.
     * 
     * The included instructions booklet describes how to connect the parts 
     * together: x AND y -> z means to connect wires x and y to an AND gate, 
     * and then connect its output to wire z.
     */
    private Day07(){}

    public static void update() throws IOException {
        String fNum = "07";//Part1- 3176   Part2- 14710
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 07- 3176  hi-64223  071- 'i' => 65079
        question2();    //Confirmed: 07- 14710   071- ???
    }

    /**
     * Question 1: In little Bobby's kit's instructions booklet (provided as 
     * your puzzle input), what signal is ultimately provided -to- wire a?
     */
    private static void question1() {
        wInstr = new WireInstr[len];
        for(int i = 0; i < len; i++) wInstr[i] = new WireInstr(fileInfo[i]);

        //Track ,  Confirmed: 07- 3176   hi-64223
        //071- d: 72, e: 507, f: 492. g: 114. h: 65412, i: 65079, x: 123, y: 456  (0 to 65,536)
        int b = getWireOutput(getWireIdx("a"));
        System.out.println("\nPart 1: The signal on wire 'a': " + b);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        fileInfo[3] = "3176 -> b";
        wInstr = new WireInstr[len];
        for(int i = 0; i < len; i++) wInstr[i] = new WireInstr(fileInfo[i]);

        //Track ,  Confirmed: 07- 14710   071- ???
        int b = getWireOutput(getWireIdx("a"));
        System.out.println("\nPart 2: The signal on wire 'a': " + b);
    }

    //------------------------- Part 1 --------------------------
    /**
     * Get the final output of a gate wire.  Call recursively to get the value
     * @param wInstrIdx WireInstr index you're looking for theoutput for.
     * @return return the integer value 0 - 65536.
     */
    private static int getWireOutput(int wInstrIdx){
        //If output already valid return that value.
        if(wInstr[wInstrIdx].getOutVal() > -1) return wInstr[wInstrIdx].getOutVal();

        int inIdx;
        int inVal;
        if(wInstr[wInstrIdx].getSig1Val() < 0){     //If value not valid
            inIdx = getWireIdx(wInstr[wInstrIdx].getSig1ID());  //get the idx of the input wire
            wInstr[wInstrIdx].setSig1Val(getWireOutput(inIdx)); //get the value (recursive)
        }

        if(wInstr[wInstrIdx].getSig2Val() < 0){
            inIdx = getWireIdx(wInstr[wInstrIdx].getSig2ID());
            wInstr[wInstrIdx].setSig2Val(getWireOutput(inIdx));
        }

        return wInstr[wInstrIdx].getOutVal();
    }


    /**Search Instructions for an output ID to check for value.
     * If not found return -1;
     * @param wID output ID to look for
     * @return idx of WireInstr
     */
    private static int getWireIdx(String wID){
        int idx = 0;
        for(; idx < wInstr.length; idx++){
            if(wInstr[idx].getOutID().equals(wID)) return idx;
        }
        return -1;
    }
}