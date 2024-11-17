package type;

public class GuardInfo {
    private int id;
    public int maxNapPeriod = 0;
    private int totalNapTime = 0;
    private int minuteOfMaxNapPeriod;
    private short[] commonMinutesNapped = new short[60];

    public GuardInfo(int id){
        this.id = id;
    }

    public void setID(int _id){ id = _id; }
    public int getID(){ return id; }
    public int getTotalNapTime(){ return totalNapTime; }

    public void addCMN(int begNap, int endNap){
        int napTime = endNap - begNap;
        totalNapTime += napTime;
        for(int i = begNap; i < endNap; i++){
            commonMinutesNapped[i]++;
        }
    }

    public int findCNM(){
        int maxCNM = 0;
        int minute = 0;
        for(int min = 0; min < commonMinutesNapped.length; min++){
            if(commonMinutesNapped[min] > maxCNM){
                maxCNM = commonMinutesNapped[min];
                minute = min;
            }
        }
        return minute;
    }

    public int findCNM_Days(){
        return commonMinutesNapped[findCNM()];
    }
}
