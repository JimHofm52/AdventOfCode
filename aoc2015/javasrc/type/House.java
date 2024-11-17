package type;

public class House {
    private int adrX;
    private int adrY;
    private int gift = 0;

    public House(int x, int y){
        adrX = x;
        adrY = y;
        gift++;
    }

    public House(int[] adr){
        adrX = adr[0];
        adrY = adr[1];
        gift++;
    }

    public boolean chkAdrEqual(int[] adr){
        return adrX == adr[0] && adrY == adr[1];
    }

    public void incrGift(){ gift++; }
    public int getGiftCnt() { return gift; }

}
