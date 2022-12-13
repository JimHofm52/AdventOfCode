package type;

import java.util.Arrays;

public class T_Disk {
    public String actPath;
    public String actFldr;
    public int actFldrIdx;
    public T_Folder[] fldrLst = new T_Folder[0];
    public T_File[] fileLst = new T_File[0];

    public T_Disk(){
        add_Folder("/");
        chgDir("/");
    }

    public void add_Folder(String name){
        int len = fldrLst.length;
        fldrLst = Arrays.copyOf(fldrLst, len + 1);  //Add a new folder to the folder list
        fldrLst[len] = new T_Folder(name, actFldrIdx);

        fldrLst[actFldrIdx].addItem(len * 10);      //Add this folder idx to the act folder
        // sumItems(actFldrIdx);
    }

    public void add_File(int size, String name){
        int len = fileLst.length;
        fileLst = Arrays.copyOf(fileLst, len + 1);  //Add a new file to the file list
        fileLst[len] = new T_File(name, size, actFldrIdx);

        fldrLst[actFldrIdx].addItem(len * 10 + 1);  //Add this file idx to the act folder
        // sumItems(actFldrIdx);
    }

    public void sumItems(int folderIdx){
        int fldrSize = 0;
        fldrLst[folderIdx].size = 0;

        for(int f : fldrLst[folderIdx].itemIdx){
            if(f % 10 > 0) {
                fldrSize += fileLst[f/10].size;
            }else{
                fldrSize += fldrLst[f/10].size;
            }
        }
        fldrLst[folderIdx].size = fldrSize;
    }

    public void fnlSumItems(){
        for(int i = fldrLst.length - 1; i >= 0; i--){
            sumItems(i);
        }
    }

    /**
     * Simple version only checks for / or .. !
     * @param dirNm
     * @return
     */
    public int chgDir(String dirNm){
        int idx;
        if(dirNm.equals("/")){
            actPath = "//";
            actFldr = "/";
            actFldrIdx = 0;
        }else if(dirNm.equals("..")){
            idx = actPath.lastIndexOf("/");
            actPath = actPath.substring(0, idx);
            actFldrIdx = fldrLst[actFldrIdx].parFldrIdx;
            actFldr = fldrLst[actFldrIdx].name;
        }else if((idx = fldrExInActFldr(dirNm)) > -1){
            actPath += "/" + dirNm;
            actFldr = dirNm;
            actFldrIdx = idx;
        }else{
            System.out.println("Bad chgDir: " + dirNm);
            return -1;
        }
        return actFldrIdx;
    }

    /**
     * Checks and returns the item index that matches the name passed else return -1.
     * @param dirNm
     * @return
     */
    private int fldrExInActFldr(String dirNm){
        int chk;
        for(int i = 0; i < fldrLst[actFldrIdx].itemIdx.length; i++){
            chk = fldrLst[actFldrIdx].itemIdx[i];
            //If item is a folder and the item name matches then return true.
            if(chk % 10 == 0 && fldrLst[chk/10].name.equals(dirNm)) return chk/10;
        }
        return -1;
    }

    /**
     * Find the folder index that matches the name passed.
     * @param dirName
     * @return
     */
    private int findActDirIdx(String dirName){
        if(dirName.isEmpty()) return 0; //Root dir.
        for(int i = 0; i < fldrLst.length; i++) if(fldrLst[i].name.equals(dirName)) return i;
        System.out.println("findActDirIdx not found for: " + dirName);
        return -1;
    }
}
