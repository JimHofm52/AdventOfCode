package type;

public class T_Areas {
    public int elfA[] = new int[2];    //[0] = lo, [1] = hi
    public int elfB[] = new int[2];

    public T_Areas(String inStr){
        int idxEnd = inStr.indexOf('-');
        elfA[0] = Integer.parseInt(inStr.substring(0, idxEnd++));   //1st elf lo int
        inStr = inStr.substring(idxEnd);
        idxEnd = inStr.indexOf(',');
        elfA[1] = Integer.parseInt(inStr.substring(0, idxEnd++));   //1st elf hi int
        inStr = inStr.substring(idxEnd);
        idxEnd = inStr.indexOf('-');
        elfB[0] = Integer.parseInt(inStr.substring(0, idxEnd++));   //2nd elf lo int
        inStr = inStr.substring(idxEnd);
        elfB[1] = Integer.parseInt(inStr);                        //2nd elf hi int
    }
}
