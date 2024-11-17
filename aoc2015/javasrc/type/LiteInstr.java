package type;

public class LiteInstr {
    private String str;
    private int[] coor1 = new int[2];
    private int[] coor2 = new int[2];
    private int cmd = -2;
    private static String tmpStr;

    /* Examples
     * turn on 0,0 through 999,999
     * toggle 0,0 through 999,0
     * turn off 499,499 through 500,500
     */
    public LiteInstr(String strIn){
        str = strIn;
        String[] tmpStr = strIn.split("\\s|,");
        int os = tmpStr[0].equals("toggle") ? 0 : 1;
        if(os == 0){
            cmd = 2;
        }else{
            cmd = tmpStr[1].equals("off") ? -1 : 1;
        }
        coor1[0] = Integer.parseInt(tmpStr[1 + os]);
        coor1[1] = Integer.parseInt(tmpStr[2 + os]);
        coor2[0] = Integer.parseInt(tmpStr[4 + os]);
        coor2[1] = Integer.parseInt(tmpStr[5 + os]);

        int a = 0;
    }
    public int getCmd() { return cmd; }
    public int[] getCoor1(){ return coor1;}
    public int[] getCoor2(){ return coor2;}
}
