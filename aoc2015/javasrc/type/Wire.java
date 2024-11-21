package type;

public class Wire {
    private String wireID;
    private Integer value;

    public Wire(String strIn){
        wireID = strIn;
    }

    public String getID(){ return wireID; }
    public void setValue(int val){ value = val; }
    public int getValue(){ return value; }
}
