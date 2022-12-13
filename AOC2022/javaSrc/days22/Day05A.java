package days22;

import java.io.IOException;
import java.util.Stack;

import util.ReadInput;

public class Day05A {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day05A(){}

    public static void update() throws IOException {
        // String fNum = "05";      //Part1- VPCDMSLWJ   Part2- TPWCGNCCG
        String fNum = "051";     //Part1- CMZ   Part2- MCD
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        Stack<Character>[] stks = parceStacks(fileInfo);
        for(Stack s : stks) System.out.println(s);

        int[][] dir = parceDir(fileInfo);

        question1(dir, stks);    //Confirmed: 03-VPCDMSLWJ   031-CMZ
        question2(dir, stks);    //Confirmed: 03-TPWCGNCCG   031-MCD
    }

    /**
     * Question 1: Find the box on top of each box after rearranging them
     * by moving one box at a time.
     */
    private static void question1(int[][] dir, Stack[] stk) {
        Stack[] stking = stk.clone();
        int dirLen = dir.length;
        for(int i = 0; i < dirLen; i++){
            moveIt9000(dir[i], stking);
        }

        String ans = "";
        for(Stack s : stking){
            ans += s.pop();
        }
        //Track ,  Confirmed: 05- VPCDMSLWJ   051- CMZ
        System.out.println("\nPart 1: Top boxes on stacks: " + ans);
    }

    /**
     * Question 2: Find the box on top of each box after rearranging them
     * by moving multiple boxes at a time.
     */
    private static void question2(int[][] dir, Stack[] stk) {
        int dirLen = dir.length;
        for(int i = 0; i < dirLen; i++){
            moveIt9001(dir[i], stk);
        }

        String ans = "";
        for(Stack s : stk){
            ans += s.peek();
        }
        //Track ,  Confirmed: 05- VPCDMSLWJ   051- MCD
        System.out.println("\nPart 2: Top boxes on stacks: " + ans);
    }

    /**
     * Rearrange stacks, 1 box at a time using direction.
     * @param dir Array of directions, [0]Move box cnt, [1]From stk, [2]To this stk
     * @param stkIt Starting stack arrangement.  Used to return rearranged stack.
     */
    private static void moveIt9000(int[] dir, Stack[] stkIt){
        int moveCnt = dir[0];
        int from = dir[1] - 1;
        int to = dir[2] - 1;
        
        for(int  i = 0; i < moveCnt; i++){
            stkIt[to].push(stkIt[from].pop());
        }
    }
    
    /**
     * Rearrange stacks, multiple boxes at a time using direction.
     * @param dir Array of directions, [0]Move box cnt, [1]From stk, [2]To this stk
     * @param stkIt Starting stack arrangement.  Used to return rearranged stack.
     */
    private static void moveIt9001(int[] dir, Stack<Character>[] stkIt){
        int moveCnt = dir[0];
        int from = dir[1] - 1;
        int to = dir[2] - 1;
        Stack<Character> tmpStk = new Stack<Character>();
        
        for(int i = 0; i < moveCnt; i++){
            tmpStk.push(stkIt[from].pop());
        }

        for(int i = 0; i < moveCnt; i++){
            stkIt[to].push(tmpStk.pop());
        }
        // stkIt[to] += stkIt[from].substring(stkIt[from].length() - moveCnt);
        // stkIt[from] = stkIt[from].substring(0, stkIt[from].length() - moveCnt);
    }
    
    /**
     * Get the starting stack arrangement.
     * @param inFile
     * @return Array of stacks
     */
    private static Stack[] parceStacks(String[] inFile){
        int rowLen = (inFile[0].length());
        int stkCnt = rowLen / 4 + 1;

        Stack<Character>[] stk = new Stack[stkCnt];
        for(int i = 0; i < stkCnt; i++) stk[i] = new Stack<Character>();

        int rowCnt = -1;
        for(String s : inFile){
            if(s.isEmpty()) break;
            rowCnt++;
        }

        char tmpCh;
        for(int s = 0; s < stkCnt; s++ ){
            for( int i = rowCnt - 1; i >= 0; i--){
                tmpCh = inFile[i].charAt((s * 4) + 1);
                if(tmpCh != ' ') stk[s].push(tmpCh);
            }
        }
        // for(Stack s : stk) System.out.println(s);
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