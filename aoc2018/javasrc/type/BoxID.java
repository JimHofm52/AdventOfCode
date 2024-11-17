package type;

public class BoxID {
    private String id;
    private int[] charCnt = new int[26];
    private int twoCnt = 0;
    private int threeCnt = 0;

    public BoxID(String id){
        this.id = id;
        cntAlpha(id, charCnt);
        twoCnt = cntCharCnt(2, charCnt);
        threeCnt = cntCharCnt(3, charCnt);
    }

    private static void cntAlpha(String s, int[] chCnt){
        for(int i = 0; i < s.length(); i++){
            chCnt[s.charAt(i) - 'a']++;
        }
    }

    private static int cntCharCnt(int matchCnt, int[] chCnt){
        int cnt = 0;
        for(int i = 0; i < chCnt.length; i++){
            if(chCnt[i] == matchCnt) cnt++;
        }
        return cnt;
    }

    public int get2Cnt(){ return twoCnt; }
    public int get3Cnt(){ return threeCnt; }

}
