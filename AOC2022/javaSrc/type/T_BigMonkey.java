package type;

import java.math.BigInteger;
import java.util.Arrays;

public class T_BigMonkey {
    private static int idCntr = 0;  //Used to assign unique monkey ID
    private int id;             //Monkey ID (index)
    private BigInteger[] item;        //Array of value of worry for items being held by monkey
    private int mathOp;         //Math to increase worry.  0=+, 1=*, 2=^2
    private BigInteger wryParm = BigInteger.valueOf(1);       //Parm to be used with MathOp
    private BigInteger testParm;      //Test to see if worry is divisible by this
    private int mnkyTrue;       //Monkey to pass to if the test is true
    private int mnkyFalse;      //Monkey to pass to if the test is false
    private int evalCntr;       //How many items has the monkey evaluated
    private BigInteger wryReducParm;   //Value to reduce worry by (part 1 = 3, part 2 = 1)

    /**Constructor */
    public T_BigMonkey(){
        id = idCntr++;
    }

    /**
     * Add item to list 
     * @param newItem
     */
    public void addItem(BigInteger newItem){
        if(item == null) item = new BigInteger[0];    //Initialize array if empty
        item = Arrays.copyOf(item, item.length + 1);    //Increasse by 1
        item[item.length - 1] = newItem;        //Add item
    }

    /**Clear all items */
    public void clearItems(){
        item = new BigInteger[0];
    }

    /**
     * Evaluate the worry value for each item before passing the items on.
     * <p>First, add to (0) or mult by (1) the existing worry value or square (2) the worry value/
     * <p>Then reduce that worry value by worry reduction factor. 
     * <p>Then see if divisible by test parm and pass to true monkey else false monkey.
     * @return long array for each item with 2-D array of monkey to pass to[0] and value[1]/
     */
    public BigInteger[][] evalMnky(){
        if(item == null) return new BigInteger[0][0];
        BigInteger itemVal = BigInteger.ZERO;
        BigInteger[][] mnkyThrow = new BigInteger[item.length][2];    //[0]mnky [1]itemval
        for(int i  = 0; i < item.length; i++){
            switch(mathOp){
                case 0:
                itemVal = item[i].add(wryParm);
                break;
                case 1:
                itemVal = item[i].multiply(wryParm);
                break;
                case 2:
                itemVal = item[i].multiply(item[i]);
                break;
                default: 
                itemVal = BigInteger.valueOf(-1);
                System.out.println("Invalid math op: " + mathOp);
            }
            // itemVal = itemVal / wryReducParm;
            itemVal = itemVal.divide(wryReducParm);
            mnkyThrow[i][0] = BigInteger.valueOf(itemVal.mod(testParm) == BigInteger.ZERO ? mnkyTrue : mnkyFalse);
            mnkyThrow[i][1] = itemVal;

            // itemVal = Long.divideUnsigned(itemVal, wryReducParm);
            // mnkyThrow[i][0] = Long.remainderUnsigned(itemVal, testParm) == 0L ? mnkyTrue : mnkyFalse;   //Monkey to throw to
            // mnkyThrow[i][1] = itemVal;      //Value of item worry thrown
        }
        evalCntr += item.length;
        item = null;
        return mnkyThrow;

    }

    /**
     * Set the applied math operation.
     * @param _op 0 = Add, 1 = Multiply, 2 = Square.
     */
    public void setMathOp(int _op) {mathOp = _op;}
    public void setWryParm(int _wryParm) {wryParm = BigInteger.valueOf(_wryParm);}
    public void setWryReducParm(int _wryReducParm) {wryReducParm = BigInteger.valueOf(_wryReducParm);}
    public void setTestParm(int _testParm) {testParm = BigInteger.valueOf(_testParm);}
    public void setMnkyTrue(int _mnkyTrue) {mnkyTrue = _mnkyTrue;}
    public void setMnkyFalse(int _mnkyFalse) {mnkyFalse = _mnkyFalse;}

    public void resetEvalCntr() {evalCntr = 0;}

    public int getMnkyID(){ return id;}
    public long getEvalCntr() {return evalCntr;}

}
