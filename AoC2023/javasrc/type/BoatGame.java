package type;

public class BoatGame {
    int cnt = 4;
    public int[] runTime = new int[cnt];
    public int[] distRun = new int[cnt];
    public long[] winCnt = new  long[cnt];    //34 * 8 * 69 * 7 = 131376
    public int winProd = 1;

    public long runTime2 = 0;
    public long distRun2 = 0;
    public long winCnt2  = 0;
    public long winProd2 = 1;

    public BoatGame(String[] inStr, int cnt){
        String rtIn = inStr[0].substring(0);
        String drIn = inStr[1].substring(0);
        for(int i = 0; i < cnt; i++){
            rtIn = getNextNum( rtIn, runTime, i);
            drIn = getNextNum( drIn, distRun, i);
            // calcWinCnt(i);
            winCnt[i] = calcWinCnt3(runTime[i], distRun[i]);
            winProd *= winCnt[i];
        }
        update2();
        winCnt2 = calcWinCnt3(runTime2, distRun2);
        winProd2 = winCnt2;
    }

    /**
     * Combine numbers,  xx yy zzz to xxyyzzz
     */
    private void update2(){
        int tmp;
        int pwr;
        for(int i = 0; i < cnt; i++){
            tmp = runTime[i];
            pwr = 1;
            while((tmp/pwr) >= 1) pwr *= 10;
            runTime2 = (runTime2 * pwr) + tmp;

            tmp = distRun[i];
            pwr = 1;
            while((tmp/pwr) >= 1) pwr *= 10;
            distRun2 = (distRun2 * pwr) + tmp;
        }
    }

    private String getNextNum(String numStr, int[] tm, int i){
        int bIdx = 0;
        while(!Character.isDigit(numStr.charAt(++bIdx)));
        int eIdx = bIdx;
        while(Character.isDigit(numStr.charAt(eIdx++)) && eIdx < numStr.length());
        tm[i] = Integer.parseInt((numStr.substring(bIdx, eIdx)).trim());
        return numStr.substring(eIdx);
    }

    /**
     * Deprecated - Orignally used on part one.  Moved to part calcWinCnt3
     * @param i
     */
    private void calcWinCnt(int i){
        int rt = runTime[i];
        for(int t = 0; t < runTime[i]; t++){
            if((t * (rt - t)) > distRun[i]) winCnt[i]++;  // 7:9 = 4: 0>0, 1>6, 2>10, 3>12, 4>12, 5>10, 6>6, 7>0
        }
        int a = 0;
    }

    /**
     * Find the first win, double the count, subtract that from the total possible wins and add 1.
     * @param rnTm - previous winning runtime
     * @param dist = ptrvious winning distance
     * @return max number of possible winning counts.
     */
    private long calcWinCnt3(long rnTm, long dist){
        long tmp;
        long timeHeld = 0;
        long upNum = -1;
        do{
            timeHeld++;
            tmp = (timeHeld * (rnTm - timeHeld));
            if(tmp > dist) upNum = timeHeld;
        }while(upNum < 0);
        return rnTm - 2 * upNum + 1;
    }
}
