package type;

public class Seed {
    public String str;
    public long[] seedNum;
    public long[] seedLen;
    public long[] seedLoc;
    public int cnt;

    public Seed(String inList, int q){
        str = inList;
        for(int i = 7; i < str.length(); i++) if(str.charAt(i) == ' ') cnt++;
        cnt++;
        long[] tmpNums = new long[cnt];
        parceNums(tmpNums);

        if(q == 1){
            saveFor1(tmpNums);
        }else{
            cnt /= 2;
            saveFor2(tmpNums);
        }
    }

    private void parceNums(long[] tmp){
        int begIdx;
        int endIdx = 0;
        for(int i = 0; i < cnt; i++){
            begIdx = str.indexOf(' ', endIdx) + 1;
            endIdx = str.indexOf(' ', begIdx);
            if(endIdx < 0) endIdx = str.length();
            tmp[i] = Long.parseLong((str.substring(begIdx, endIdx)).trim());
        }
    }

    private void saveFor1(long[] tmp){
        seedNum = new long[cnt];
        seedLen = new long[cnt];
        seedLoc = new long[cnt];
        for(int i = 0; i < cnt; i++){
            seedNum[i] = tmp[i];
            seedLen[i] = 1;
        }
    }

    private void saveFor2(long[] tmp){
        seedNum = new long[cnt];
        seedLen = new long[cnt];
        seedLoc = new long[cnt];
        for(int i = 0; i < cnt; i++){
            seedNum[i] = tmp[i * 2];
            seedLen[i] = tmp[(i * 2) + 1];
        }
    }
}
