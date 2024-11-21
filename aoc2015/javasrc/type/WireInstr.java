package type;
/**Instructions for little Bobby's gate kit. */
public class WireInstr {
    private String instrStr;
    /** Originals = [0] - output,  [1] - signal1,  [2] - signal2,  [3] - operation */
    private String[] signal_IDs = new String[4];
    /** Values of = [0] - output,  [1] - signal1,  [2] - signal2 // If null not initialized*/
    private int[] signal_Vals = {-1, -1, -1};

    /** Example 071:        run twice to find i;
     * 123 -> x             x AND y -> d
     * 456 -> y             x OR y -> e
     * x AND y -> d         x LSHIFT 2 -> f
     * x OR y -> e          y RSHIFT 2 -> g
     * x LSHIFT 2 -> f      NOT x -> h
     * y RSHIFT 2 -> g      NOT y -> i
     * NOT x -> h           123 -> x
     * NOT y -> i           456 -> y
     */
    public WireInstr(String instrIn){
        instrStr = instrIn;
        String[] inStrs = instrStr.split("\\s");
        parce(inStrs, signal_IDs, signal_Vals);
    }

    private static void parce(String[] strIn, String[] sigID, int[] sigVal){
        switch (strIn[1]) {
            case "->":      //Set ex. 123 -> x  ==>  123 SET NA x
                sigID[0] = strIn[2];    //Signal output Wire ID
                sigID[1] = strIn[0];    //Signal input 1 Wire ID or value
                sigVal[1] = chkForNum(strIn[0]);    //Convert to int if number
                sigID[2] = "NA";        //Signal input 2, No Attachment, wires are lower case.
                sigVal[2] = 0;          //Value input 2 so calc sees 2 valid inputs
                sigID[3] = "SET";       //Operation
                break;
            case "AND":     //AND Ex. x AND y -> d
            case "OR":      //OR Ex. x OR y -> e
            case "LSHIFT":  //LSHIFT Ex. x LSHIFT 2 -> f
            case "RSHIFT":  //RSHIFT Ex. y RSHIFT 2 -> g
                sigID[0] = strIn[4];  //Signal output Wire ID
                sigVal[0] = -1;       //set to -1 as not calc yet
                sigID[1] = strIn[0];  //Signal input 1 Wire ID or value
                sigVal[1] = chkForNum(strIn[0]);    //Convert to int if number else -1
                sigID[2] = strIn[2];  //Signal input 2 Wire ID or value
                sigVal[2] = chkForNum(strIn[2]);    //Convert to int if number else -1
                sigID[3] = strIn[1];  //Operation
                break;
        
            default:        //NOT Ex. NOT y -> i
                sigID[0] = strIn[3];  //Signal output
                sigID[1] = strIn[1];  //Signal input 1
                sigID[2] = "NA";      //Signal input 2, No Attachment, wires are lower case.
                sigVal[2] = 0;        //Value input 2 so calc sees 2 valid inputs
                sigID[3] = "NOT";     //Operation
                break;
        }

        if(sigVal[1] > -1 && sigVal[2] > -1) calcOper(sigID[3], sigVal);
    }

    /**
     * 
     * @param inStr
     * @return an integer if string is a number else return -1
     */
    private static int chkForNum(String inStr){
        return (Character.isDigit(inStr.charAt(0)) ? Integer.parseInt(inStr) : -1);
    }

    public String getOutID(){ return signal_IDs[0]; }
    public String getSig1ID(){ return signal_IDs[1]; }
    public String getSig2ID(){ return signal_IDs[2]; }
    public String getOper(){ return signal_IDs[3]; }

    public int getOutVal(){ return signal_Vals[0]; }
    public int getSig1Val(){ return signal_Vals[1]; }
    public int getSig2Val(){ return signal_Vals[2]; }

    /**Set the value of signal1 and if signal2 is already set calculate output */
    public void setSig1Val(int in){
        signal_Vals[1] = in;
        if(signal_Vals[2] > -1) calcOper(signal_IDs[3], signal_Vals);
    }
    /**Set the value of signal2 and if signal1 is already set calculate output */
    public void setSig2Val(int in){
        signal_Vals[2] = in;
        if(signal_Vals[1] > -1) calcOper(signal_IDs[3], signal_Vals);
    }

    private static void calcOper(String oper, int[] sigVal){
        switch(oper){
            case "SET":
                sigVal[0] = sigVal[1];
                break;
            case "AND":
                sigVal[0] = (sigVal[1] & sigVal[2]);
                break;
            case "OR":
                sigVal[0] = (sigVal[1] | sigVal[2]);
                break;
            case "LSHIFT":
                sigVal[0] = (sigVal[1] << sigVal[2]);
            break;
            case "RSHIFT":
                sigVal[0] = (sigVal[1] >> sigVal[2]);
            break;
            case "NOT":
                sigVal[0] = ((65536 + ~sigVal[1]));
            break;
        }
    }
}
