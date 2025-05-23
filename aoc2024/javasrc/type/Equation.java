package type;

import util.AryUtil;

/**
 * Used in Day07
 */
public class Equation {
    private String inStr;
    private long answer;
    private int[] num;
    private int[] op;
    private boolean eqOK = false;   //Equation can be solved with + & *.

    /**
     * Parced data, IE. 7290: 6 8 6 15, answer: num1 num2 num3 ...
     * <p> Will need to use add & multiple num's for answer.
     * 
     * @param inStr from string
     */
    public Equation(String inStr){
        this.inStr = inStr;
        answer = parceAnswer(inStr);
        num = parceNums(inStr);
        op = new int[num.length - 1];   //0 = add, 1 = multiple
        eqOK = evalEq(answer, num, op);
    }

    /**
     * Parce for answer
     * @param str
     * @return answer
     */
    private static long parceAnswer(String str){
        int begIdx = str.indexOf(':');
        return Long.parseLong(str.substring(0, begIdx).trim());
    }

    /**
     * Parce for numbers
     * @param str
     * @return array of numbers
     */
    private static int[] parceNums(String str){
        String tmpStr;
        int begIdx = str.indexOf(':') + 1;
        tmpStr = str.substring(begIdx).trim();
        return AryUtil.getIntAry(tmpStr);
    }

    /**
     * Chack if calced value equals answer given.
     * @param ansIn
     * @param numIn
     * @param opIn
     * @return true if equal.
     */
    private static boolean evalEq(long ansIn, int[] numIn, int[] opIn){
        int tmpi = (int)Math.pow(2, opIn.length);
        for(int i = 0; i < (tmpi); i++){
            // opCombo = calcEq(numIn, opIn);
            if(ansIn == calcEq(numIn, opIn)){
                return true;
            }
            stepOps(opIn);
        }
        int a = 0;
        return false;
    }

    /**
     * increment thru the ops: 000 > 100 > 010 > 110 > 001 > 101 > 011 > 111. 
     * @param opIn int array to be incremented.
     */
    private static void stepOps(int[] opIn){
        for(int i = 0; i < opIn.length; i++){
            if(++opIn[i] > 1){  //carry
                opIn[i] = 0;
            }else{
                break;
            }
        }
    }

    /**
     * Combine the numbers with the ops provided
     * @param numIn
     * @param opIn
     * @return the value of the adds and/or multiplies
     */
    private static long calcEq(int[] numIn, int[] opIn){
        if(numIn.length - opIn.length != 1){
            System.out.println("num & op count not compatable: num = " + numIn.length + " op = " + opIn.length);
            return -1;
        }
        long tmpNum = numIn[0];
        for(int n = 0; n < numIn.length - 1; n++){
            switch(opIn[n]){
                case 0:
                    tmpNum += numIn[n + 1];
                break;
                case 1:
                    tmpNum *= numIn[n + 1];
                break;
                default:
                    System.out.println("Illegal operand: " + opIn[n]);
            }
        }
        return tmpNum;
    }

    /** @return The answer to the equation. */
    public long getAnswer(){ return answer; }

    /** @return The numbers used in the equation. */
    public int[] getNums(){return num;}

    /** @return true if equation is solvable.  */
    public boolean isEqOK(){return eqOK;}

}
