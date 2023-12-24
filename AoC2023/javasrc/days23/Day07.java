package days23;

import java.io.IOException;

import type.CamelCard;
import util.ReadInput;

public class Day07 {
    private static String fileInfo[];
    private static int len;
    private static CamelCard[] hand;

    /** Constructor, not needed but used for standards. */
    private Day07(){}

    public static void update() throws IOException {
        String fNum = "07";     //Part1- 253205868 11949 Lo 2036375 Lo Part2- 253907829  253666245 Lo 251343210 Lo
        // String fNum = "071";    //Part1- 6440   Part2- 5905
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        hand = new CamelCard[len];
        for(int h = 0; h < len; h++) hand[h] = new CamelCard(fileInfo[h]);

        question1();    //Confirmed: 07- 253205868  11949 Lo 2036375 Lo 071- 6440
        question2();    //Confirmed: 07- 253907829  253666245 Lo 251343210 Lo  071- 5905
    }

    /**
     * Question 1: Determine the total winnings of this set of hands 
     * by adding up the result of multiplying each hand's bid with its rank.
     */
    private static void question1() {
        bubbleSortHands();

        long tmp = 0L;      //Sum of the product of wager & rank
        for(int i = 0; i < len; i++){
            tmp += (hand[i].wager * hand[i].rank);
        }
        //Track ,  Confirmed: 07- 253205868  11949 Lo 2036375 Lo 071- 6440
        System.out.println("\nPart 1: ???: " + tmp);
    }

    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        for(int i = 0; i < len; i++){
            // System.out.print(i + "\t" + hand[i].str + "\t" + hand[i].type);
            hand[i].reevalHandTypeAsJoker();
            // System.out.println("\t" + hand[i].type);
        }
        bubbleSortHands();

        long tmp = 0L;      //Sum of the product of wager & rank
        for(int i = 0; i < len; i++){
            tmp += (hand[i].wager * hand[i].rank);
        }
        //Track ,  Confirmed: 07- 253907829  253666245 Lo 251343210 Lo  071- 5905
        System.out.println("\nPart 2: ???: " + tmp);
    }

    private static void bubbleSortHands(){
        boolean done;
        long tmp;
        do{
            done = true;
            for(int c1 = 0; c1 < len - 1; c1++){
                for(int c2 = c1 + 1; c2 < len; c2++){
                    if(isGT(c1, c2)){
                        swapRank(c1, c2);
                        done = false;
                    }
                }
            }
        }while(!done);

    }

    /**
     * Hand 1 type is great than hand 2, if equal hi card is GT.
     * @param h1Idx
     * @param h2Idx
     */
    private static boolean isGT(int h1Idx, int h2Idx){
        int tmp = hand[h1Idx].type - hand[h2Idx].type;  //>0 h1 is higher, <0 h2 is GT, =0 same
        boolean h1HiRank = hand[h1Idx].rank > hand[h2Idx].rank;  //h1 is higher rank else h2 higher
        if(tmp == 0){                       //Hands are equal
            for(int i = 0; i < 5; i++){     //look for hi card
                tmp = hand[h1Idx].card[i] - hand[h2Idx].card[i];    //+ h1 > h2 or - h2 > h1
                if(tmp != 0){               //Cards not eq.
                    return (h1HiRank ^ tmp > 0); //h1 hi rank and h1 is hi card, OK else needs rev.
                }
            }
            System.out.println("Shouldn't get here.  Hands " + h1Idx + " & " + h2Idx + " are equal." );
            return false;   //Hands are equal (shouldn't happen but)
        }
        return ((tmp > 0) ^ h1HiRank);  //h2 is hi and h1 rank is hi, swap
    }
    
    private static void swapRank(int h1Idx, int h2Idx){
        int tmp = hand[h1Idx].rank;
        hand[h1Idx].rank = hand[h2Idx].rank;
        hand[h2Idx].rank = tmp;
    }

}