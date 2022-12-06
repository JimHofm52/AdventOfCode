package days22;

import java.io.IOException;

import util.ReadInput;

public class Day05 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day05(){}

    public static void update() throws IOException {
        String fNum = "05";      //Part1- VPCDMSLWJ   Part2- TPWCGNCCG
        // String fNum = "051";     //Part1- CMZ   Part2- MCD
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        String[] stks = parceStacks(fileInfo);
        int[][] dir = parceDir(fileInfo);

        question1(dir, stks);    //Confirmed: 03-VPCDMSLWJ   031-CMZ
        question2(dir, stks);    //Confirmed: 03-TPWCGNCCG   031-MCD
    }

    /**
     * Question 1: Find the box on top of each box after rearranging them
     * by moving one box at a time.
     */
    private static void question1(int[][] dir, String[] stk) {
        String[] stking = stk.clone();
        int dirLen = dir.length;
        for(int i = 0; i < dirLen; i++){
            moveIt9000(dir[i], stking);
        }

        String ans = "";
        for(String s : stking){
            ans += s.charAt(s.length()-1);
        }
        //Track ,  Confirmed: 05- VPCDMSLWJ   051- CMZ
        System.out.println("\nPart 1: Top boxes on stacks: " + ans);
    }

    /**
     * Question 2: Find the box on top of each box after rearranging them
     * by moving multiple boxes at a time.
     */
    private static void question2(int[][] dir, String[] stk) {
        int dirLen = dir.length;
        for(int i = 0; i < dirLen; i++){
            moveIt9001(dir[i], stk);
        }

        String ans = "";
        for(String s : stk){
            ans += s.charAt(s.length()-1);
        }
        //Track ,  Confirmed: 05- VPCDMSLWJ   051- MCD
        System.out.println("\nPart 2: Top boxes on stacks: " + ans);
    }

    /**
     * Rearrange stacks, 1 box at a time using direction.
     * @param dir Array of directions, [0]Move box cnt, [1]From stk, [2]To this stk
     * @param stkIt Starting stack arrangement.  Used to return rearranged stack.
     */
    private static void moveIt9000(int[] dir, String[] stkIt){
        int moveCnt = dir[0];
        int from = dir[1] - 1;
        int to = dir[2] - 1;
        
        for(int  i = 0; i < moveCnt; i++){
            stkIt[to] += stkIt[from].substring(stkIt[from].length() - 1);
            stkIt[from] = stkIt[from].substring(0, stkIt[from].length() - 1);
        }
    }
    
    /**
     * Rearrange stacks, multiple boxes at a time using direction.
     * @param dir Array of directions, [0]Move box cnt, [1]From stk, [2]To this stk
     * @param stkIt Starting stack arrangement.  Used to return rearranged stack.
     */
    private static void moveIt9001(int[] dir, String[] stkIt){
        int moveCnt = dir[0];
        int from = dir[1] - 1;
        int to = dir[2] - 1;
        
        stkIt[to] += stkIt[from].substring(stkIt[from].length() - moveCnt);
        stkIt[from] = stkIt[from].substring(0, stkIt[from].length() - moveCnt);
    }
    
    /**
     * Get the starting stack arrangement.
     * @param inFile
     * @return Array of stacks
     */
    private static String[] parceStacks(String[] inFile){
        int rowLen = (inFile[0].length());
        int stkCnt = rowLen / 4 + 1;
        String[] stk = new String[stkCnt];

        int rowCnt = 0;
        for(String s : inFile){
            if(s.isEmpty()) break;
            rowCnt++;
        }
        rowCnt--;
        String[] row = new String[rowCnt];

        char tmpCh;
        for(int s = 0; s < stkCnt; s++ ){
            for( int i = rowCnt - 1; i >= 0; i--){
                tmpCh = inFile[i].charAt((s * 4) + 1);
                if(stk[s] == null){
                    stk[s] = String.valueOf(tmpCh);
                }else{
                    stk[s] += String.valueOf(tmpCh);
                }
            }
            stk[s] = stk[s].trim();
        }
        return stk;
    }

    /**
     * Get the directions to rearrange the stacks
     * @param inFile
     * @return Array of directions for: [0]Move box cnt, [1]From stk, [2]To this stk
     */
    private static int[][] parceDir(String[] inFile){
        int rowSrt = 1;
        for(String s : inFile){
            if(s.isEmpty()) break;
            rowSrt++;
        }
        int[][] dir = new int[inFile.length - rowSrt][3];

        String tmpStr;
        int idxBeg = 0;
        int idxEnd = 0;
        for( int i = rowSrt; i < inFile.length; i++){
            tmpStr = inFile[i];
            idxBeg = tmpStr.indexOf(" ", 3) + 1;
            tmpStr = tmpStr.substring(idxBeg);
            idxEnd = tmpStr.indexOf(" ");
            dir[i - rowSrt][0] =  Integer.parseInt(tmpStr.substring(0, idxEnd));
            idxBeg = tmpStr.indexOf(" ", 3) + 1;
            tmpStr = tmpStr.substring(idxBeg);
            idxEnd = tmpStr.indexOf(" ");
            dir[i - rowSrt][1] =  Integer.parseInt(tmpStr.substring(0, idxEnd));
            idxBeg = tmpStr.indexOf(" ", 2) + 1;
            tmpStr = tmpStr.substring(idxBeg);
            dir[i - rowSrt][2] =  Integer.parseInt(tmpStr);
        }
        return dir;
    }
}