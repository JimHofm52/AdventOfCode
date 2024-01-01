package type;

public class Node {
    public String str;
    public String nodeID;
    public String[] dirID = new String[2];
    public int[] dirIdx = new int[2];     //index set outside

    public Node(String strIn){
        str = strIn;
        nodeID = str.substring(0, 3);
        dirID[0] = str.substring(7, 10);    //Left
        dirID[1] = str.substring(12, 15);   //Right
    }
}
