package type;

import java.util.Arrays;

public class T_Monkey {
    private static int idCntr = 0;  //Used to assign unique monkey ID
    private int id;             //Monkey ID (index)
    private long[] item;        //Array of value of worry for items being held by monkey
    private int mathOp;         //Math to increase worry.  0=+, 1=*, 2=^2
    private long wryParm;       //Parm to be used with MathOp
    private long testParm;      //Test to see if worry is divisible by this
    private int mnkyTrue;       //Monkey to pass to if the test is true
    private int mnkyFalse;      //Monkey to pass to if the test is false
    private int evalCntr;       //How many items has the monkey evaluated
    private int wryReducParm;   //Value to reduce worry by (part 1 = 3, part 2 = 1)

    /**Constructor */
    public T_Monkey(){
        id = idCntr++;
    }

    /**
     * Add item to list 
     * @param newItem
     */
    public void addItem(long newItem){
        if(item == null) item = new long[0];    //Initialize array if empty
        item = Arrays.copyOf(item, item.length + 1);    //Increasse by 1
        item[item.length - 1] = newItem;        //Add item
    }

    /**Clear all items */
    public void clearItems(){
        item = new long[0];
    }

    /**
     * Evaluate the worry value for each item before passing the items on.
     * <p>First, add to (0) or mult by (1) the existing worry value or square (2) the worry value/
     * <p>Then reduce that worry value by worry reduction factor. 
     * <p>Then see if divisible by test parm and pass to true monkey else false monkey.
     * @return long array for each item with 2-D array of monkey to pass to[0] and value[1]/
     */
    public long[][] evalMnky(){
        if(item == null) return new long[0][0];
        long itemVal = 0L;
        long[][] mnkyThrow = new long[item.length][2];    //[0]mnky [1]itemval
        for(int i  = 0; i < item.length; i++){
            switch(mathOp){
                case 0:
                itemVal = item[i] + wryParm;
                break;
                case 1:
                itemVal = item[i] * wryParm;
                break;
                case 2:
                itemVal = item[i] * item[i];
                break;
                default: 
                itemVal = -1L;
                System.out.println("Invalid math op: " + mathOp);
            }

            // Works for part 1, Ex. 111 101[0] * 105[3] = 10605 and for 11 - 231[6] * 234[7] = 54054
            //Part 2, Ex. 111 is supposed to be 52166[0] * 52013[3] = 2713310158
            //              but part 2 produces 50258    * 52481    = 2637590098
            // itemVal = itemVal / wryReducParm;
            // mnkyThrow[i][0] = itemVal % testParm == 0 ? mnkyTrue : mnkyFalse;
            // mnkyThrow[i][1] = itemVal;

            // Works for part 1, Ex. 111 101[0] * 105[3] = 10605 and for 11 - 231[6] * 234[7] = 54054
            //Part 2, Ex. 111 is supposed to be 52166[0] * 52013[3] = 2713310158
            //                     but produces 50234    * 52472    = 2635878448
            itemVal = Long.divideUnsigned(itemVal, wryReducParm);
            mnkyThrow[i][0] = Long.remainderUnsigned(itemVal, testParm) == 0L ? mnkyTrue : mnkyFalse;   //Monkey to throw to
            mnkyThrow[i][1] = itemVal;

            //This doesn't work, yet.
            // itemVal = Long.divideUnsigned(itemVal, wryReducParm);
            // if(Long.remainderUnsigned(itemVal, testParm) == 0L){
            //     mnkyThrow[i][0] = mnkyTrue;
            //     // mnkyThrow[i][1] = itemVal / testParm;
            //     mnkyThrow[i][1] = mathOp < 2 ? itemVal : itemVal / testParm;
            // }else{
            //     mnkyThrow[i][0] = mnkyFalse;
            //     mnkyThrow[i][1] = itemVal;
            // }
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
    public void setWryParm(int _wryParm) {wryParm = _wryParm;}
    public void setWryReducParm(int _wryReducParm) {wryReducParm = _wryReducParm;}
    public void setTestParm(int _testParm) {testParm = _testParm;}
    public void setMnkyTrue(int _mnkyTrue) {mnkyTrue = _mnkyTrue;}
    public void setMnkyFalse(int _mnkyFalse) {mnkyFalse = _mnkyFalse;}

    public void resetEvalCntr() {evalCntr = 0;}

    public int getMnkyID(){ return id;}
    public long getEvalCntr() {return evalCntr;}

}
