package days23;

import java.io.IOException;

import type.Card;
import util.ReadWriteFiles;

public class Day04 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day04(){}

    public static void update() throws IOException {
        String fNum = "04"; //Part1- 26443   Part2- 6284877
        // String fNum = "041";//Part1- 13   Part2- 30
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        Card[] card = new Card[fileInfo.length];
        for(int i = 0; i < fileInfo.length; i++) card[i] = new Card(fileInfo[i]);

        question1(card);    //Confirmed: 04- 26443   041- 13
        question2(card);    //Confirmed: 04- 6284877   041- 30
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1(Card[] card) {
        // getWinnings(card);
        int tot = 0;
        for(int i = 0; i < card.length; i++) tot += card[i].winnings;
        //Track ,  Confirmed: 04- 26443   041- 13
        System.out.println("\nPart 1: ???: " + tot);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2(Card[] card) {
        int tot = 0;
        cntCard(card);
        for(int i = 0; i < card.length; i++) tot += card[i].cardCnt;
        //Track ,  Confirmed: 04- 6284877   041- 30
        System.out.println("\nPart 2: ???: " + tot);
    }

    private static void cntCard(Card[] myCards){
        int cCnt;
        int tmp;
        for(int c = 0; c < myCards.length; c++){            //for each card
            cCnt = myCards[c].cardCnt;
            for(int w = 0; w < myCards[c].winCnt; w++){     //for each win
                tmp = c + w + 1;
                if(tmp < myCards.length) myCards[tmp].cardCnt += cCnt;
            }
        }
    }
}