package type;

import java.util.Arrays;

public class Card {
    String card;
    public int cardCnt = 0;
    public int[] winNum = new int[0];
    public int[] myNum = new int[0];
    public int winnings = 0;
    public int winCnt = 0;

    public Card(String inCard){
        card = inCard;
        cardCnt = 1;
        readCard();
        getWinnings();
    }

    private void readCard(){
        int beg = card.indexOf(':') + 2;
        int end = beg + 2;
        int dvdr = card.indexOf('|');
        int idx = 0;
        int tmp = 0;
        while(beg < dvdr){
            tmp = Integer.parseInt(card.substring(beg, beg + 2).trim());
            beg = end + 1;
            end = beg + 2;
            winNum = Arrays.copyOf(winNum, ++idx);
            winNum[idx - 1] = tmp;
        }
        beg = dvdr + 2;
        end = beg + 2;
        idx = 0;
        while(beg < card.length()){
            tmp = Integer.parseInt(card.substring(beg, beg + 2).trim());
            beg = end + 1;
            end = beg + 2;
            myNum = Arrays.copyOf(myNum, ++idx);
            myNum[idx - 1] = tmp;
        }
    }

    private void getWinnings(){
        int tot = 0;
        int win = 0;
        int tmp = 1;
        for(int w = 0; w < winNum.length; w++){
            win = winNum[w];
            for(int m = 0; m < myNum.length; m++){
                if(win == myNum[m]){
                    winnings = tmp;
                    winCnt++;
                    tmp = tmp << 1;
                }
            }
        }
    }
}
