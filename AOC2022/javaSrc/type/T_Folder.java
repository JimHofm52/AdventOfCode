package type;

import java.util.Arrays;

public class T_Folder {
    public String name = "";
    public int size = 0;
    public int parFldrIdx;
    public int[] itemIdx = new int[0];  //Item can be folder or file.  Mod idx 0 = folder / 1 = file

    public T_Folder(){
    }

    public T_Folder(String fName, int fIdx){
        name = fName;
        size = 0;
        parFldrIdx = fIdx;
    }

    public void addItem(int iIdx){
        int len = itemIdx.length;
        itemIdx = Arrays.copyOf(itemIdx, len + 1);
        itemIdx[len] = iIdx;
    }

}
