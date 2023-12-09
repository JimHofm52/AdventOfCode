package type;

import java.util.Arrays;

public class SeedMap {
    public long[] destBeg = new long[0];
    public long[] srcBeg = new long[0];
    public long[] rngLen = new long[0];

public SeedMap(String strIn){
        int cnt = destBeg.length;

        int idxBeg = 0;
        int idxEnd = strIn.indexOf(" ", idxBeg);
        long tmpNum = Long.parseLong((strIn.substring(idxBeg, idxEnd)).trim());
        destBeg = Arrays.copyOf(destBeg, cnt + 1);
        destBeg[cnt] = tmpNum;

        idxBeg = idxEnd + 1;
        idxEnd = strIn.indexOf(" ", idxBeg);
        tmpNum = Long.parseLong((strIn.substring(idxBeg, idxEnd)).trim());
        srcBeg = Arrays.copyOf(srcBeg, cnt + 1);
        srcBeg[cnt] = tmpNum;

        idxBeg = idxEnd + 1;
        idxEnd = strIn.indexOf(" ", idxBeg);
        tmpNum = Long.parseLong((strIn.substring(idxBeg)).trim());
        rngLen = Arrays.copyOf(rngLen, cnt + 1);
        rngLen[cnt] = tmpNum;
    }

    public void addDest2Src(String strIn){
        int cnt = destBeg.length;
        int beg = 0;
        int end = strIn.indexOf(" ", beg);

        destBeg = Arrays.copyOf(destBeg, cnt + 1);
        destBeg[cnt] = Long.parseLong((strIn.substring(beg, end)).trim());

        beg = end + 1;
        end = strIn.indexOf(" ", beg);
        srcBeg = Arrays.copyOf(srcBeg, cnt + 1);
        srcBeg[cnt] = Long.parseLong((strIn.substring(beg, end)).trim());

        beg = end + 1;
        rngLen = Arrays.copyOf(rngLen, cnt + 1);
        rngLen[cnt] = Long.parseLong((strIn.substring(beg)).trim());
    }

    public long getDest(long src){
        for(int i = 0; i < destBeg.length; i++){
            if(src >= srcBeg[i] && src < srcBeg[i] + rngLen[i]){
                return destBeg[i] + (src - srcBeg[i]);
            }
        }
        return src;
    }
}
