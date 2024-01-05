package type;

public class Node {
    public String str;
    public String nodeID;
    /**Left=[0], Right[1] */
    public String[] dirID = new String[2];  //Left=[0], Right[1]
    /**Left=[0], Right[1] */
    public int[] dirIdx = new int[2];   //index set outside

    public Node(String strIn){
        str = strIn;
        nodeID = str.substring(0, 3);
        dirID[0] = str.substring(7, 10);    //Left
        dirID[1] = str.substring(12, 15);   //Right
    }
}
