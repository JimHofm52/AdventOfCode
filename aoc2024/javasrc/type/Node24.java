package type;

/**
 * Used in Day08
 */
public class Node24 {
    private int r;
    private int c;
    private char id;

    public Node24(int row, int col, char ch){
        r = row;
        c = col;
        id = ch;
    }

    /** @return row corr  */
    public int r(){return r;}

    /** @return col corr  */
    public int c(){return c;}

    /** @return freq id  */
    public char id(){return id;}

}
