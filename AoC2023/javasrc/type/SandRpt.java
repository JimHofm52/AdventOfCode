package type;

/**
 * Enviro type for Sand Island oasis.
 */
public class SandRpt {
    /**Original input */
    private String str;
    /**Numbers from input */
    public int[] num;
    public int cnt = 0;
    /**Extrapolated value forward */
    public int extValFwd;
    /**Extrapolated value backward */
    public int extValBkwd;

    /**
     *Enviro type for Sand Island oasis.
     *
     * @param inp sting input of numbers for evaluation
     */
    public SandRpt(String inp){
        str = inp;
        for (int i = 0; i < str.length(); i++) if(str.charAt(i) == ' ') cnt++;
        num = new int[cnt + 1];
        // diff = new int[cnt];

        int beg = 0;
        int end;
        for(int i = 0; i < cnt; i++){
            end = str.indexOf(' ', beg);
            num[i] = Integer.parseInt(str.substring(beg, end));
            beg = end + 1;
        }
        num[cnt] = Integer.parseInt(str.substring(beg));

        // for(int i = 0; i < cnt; i++){
        //     diff[i] = num[i + 1] - num[i];
        // }
        cnt++;

        extValFwd = calcExtrapFwd(num);
        extValBkwd = calcExtrapBkwd(num);
        int a = 0;

    }

    private int calcExtrapFwd(int[] prvDiff){
        boolean done = true;
        int[] diff = new int[prvDiff.length - 1];
        int extraVal = prvDiff[prvDiff.length - 1];

        for(int i = 0; i < diff.length; i++){
            diff[i] = prvDiff[i + 1] - prvDiff[i];
        }
        for(int i = 0; i < diff.length; i++){
            done &= diff[i] == 0;
            if(!done) break;
        }
        if(!done) return extraVal + calcExtrapFwd(diff);

        return extraVal;
    }

    private int calcExtrapBkwd(int[] prvDiff){
        boolean done = true;
        int[] diff = new int[prvDiff.length - 1];
        int extraVal = prvDiff[0];

        for(int i = 0; i < diff.length; i++){
            diff[i] = prvDiff[i + 1] - prvDiff[i];
        }
        for(int i = 0; i < diff.length; i++){
            done &= diff[i] == 0;
            if(!done) break;
        }
        if(!done) return extraVal - calcExtrapBkwd(diff);

        return extraVal;
    }
}
