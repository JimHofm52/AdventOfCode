package type;

import java.util.Arrays;

public class Engine {
    public String partList;
    public int partListWidth;
    public int partCnt = 0;
    public int partSum = 0;
    public boolean[] tags;
    public boolean[] tagsAll;
    public boolean[] tagIsAst;
    public int astCnt = 0;
    public int tagsAllCnt = 0;
    public int[] num = new int[0];
    public int[] numBegIdx = new int[0];
    public int[] numEndIdx = new int[0];
    public int[] numLen = new int[0];
    public boolean numIsPart[] = new boolean[0];
    int len;
    
    //Constructor
    public Engine(String in){
        partList = in;
        partListWidth = partList.length();
        len = partList.length();
        tags = new boolean[len];
        tagIsAst = new boolean[len];
        char ch;
        int tmp = 0;
        int loc = 0;
        int plen = 0;
        for(int i = 0; i < len; i++){       //for each ch in string
            ch = partList.charAt(i);
            if(Character.isDigit(ch)){      //if digit
                if(tmp == 0) loc = i;       //if the first digit grab the location
                plen++;                     //incr the length
                tmp = (tmp * 10) + Character.digit(ch, 10); //push existing and add
            }else{                          //not a digit
                if(tmp > 0){                //but prv digit
                    saveNum(tmp, loc, plen);//save num info
                    plen = 0;               //and 0 shtuff
                    tmp = 0;
                }
                if(ch != '.') tags[i] = true;
                if(ch == '*') tagIsAst[i] = true;
            }
        }
        if(tmp > 0) saveNum(tmp, loc, plen);    //save num info if at EOL
    }

    private void saveNum(int pNum, int pLoc, int pLen){
        int cnt = num.length;
        num = Arrays.copyOf(num, cnt + 1);
        num[cnt] = pNum;

        numBegIdx = Arrays.copyOf(numBegIdx, cnt + 1);
        numBegIdx[cnt] = pLoc;

        numEndIdx = Arrays.copyOf(numEndIdx, cnt + 1);
        numEndIdx[cnt] = pLoc + pLen - 1;

        numLen = Arrays.copyOf(numLen, cnt + 1);
        numLen[cnt] = pLen;

        numIsPart = Arrays.copyOf(numIsPart, cnt + 1);
        numIsPart[cnt] = false;
    }

}
