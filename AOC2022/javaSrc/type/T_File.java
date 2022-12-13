package type;

public class T_File {
    public String name = "";
    public int size = 0;
    public int parFldrIdx;

    public T_File(String fName, int fSize, int actFldrIdx){
        name = fName;
        size = fSize;
        parFldrIdx = actFldrIdx;
    }
}
