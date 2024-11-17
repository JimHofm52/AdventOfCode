package type;

import java.util.Date;
/* Example
012345678901234567890123456789
[1518-04-11 00:00] Guard #2207 begins shift
[1518-08-08 00:48] wakes up
[1518-03-29 00:29] falls asleep */
public class DTGuard {
    private String dataIn;
    private int id;
    private int date;       //mmdd
    private int time;       //hhmm
    private int dateTime;   //mmddhhmm
    private boolean isAwake;//G-true, w-true, f-false

    private static int beg;
    private static int end;
    private static String tmpStr;

    public DTGuard(String input){
        dataIn = input;

        id = parceID(input);
        date = parceDate(input);
        time = parceTime(input);

        dateTime = 10000 * date + time;

        isAwake = input.charAt(19) != 'f';

    }

    private static int parceID(String str){
        if(str.charAt(25) != '#') return -1;
        beg = 26;
        end = str.indexOf(" ", beg);
        return Integer.parseInt(str.substring(beg, end));
    }

    private static int parceDate(String str){
        beg = 6;
        end = str.indexOf(" ", beg);
        tmpStr = str.substring(beg, end);
        tmpStr = tmpStr.replace("-", "");
        return Integer.parseInt(tmpStr);
    }

    private static int parceTime(String str){
        beg = 12;
        end = 17;
        tmpStr = str.substring(beg, end);
        tmpStr = tmpStr.replace(":", "");
        return Integer.parseInt(tmpStr);
    }

    public int getID(){ return id; }
    public int getDate(){ return date; }
    public int getTime(){ return time; }
    public int getDT(){ return dateTime; }
    public boolean getIsAwake(){ return isAwake; }

}
