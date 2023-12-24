package type;

public class CamelCard {
    /**Card value 0 & 1 added as holder.  2 - 9, T(10) - A(14) */
    static char[] cardVal = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    static int handsDealt = 1;

    public String str;  //Save card string for troubleshooting.
    public int[] card = new int[5];     //Card char as cardVal.
    public int[] held = new int[15];    //Counter for each face value held.
    public int type;    //6=5 of a kind, 5=4 oak, 4=full house, 3=3 oak, 2=2 pair, 1=1 pair, 0=hi card
    public int wager;   //Wager made
    public int rank = 1;   //Init as the hand dealt. Then sieve outside to real rank.
    
    /**
     * Read in 5 poker cards as int 2 - 9, T=10, J=11, Q=12, K=13 & A=14 and wager as int.
     * @param strIn  "28Q4A 456"
     */
    public CamelCard(String strIn){
        str = strIn;
        rank = handsDealt++;
        for(int i = 0; i < 5; i++){
            card[i] = getCardVal(strIn.charAt(i));
        }
        wager = Integer.parseInt(strIn.substring(5).trim());
        parceHand();
        type = scoreHand();
    }

    /**
     * Covert card char to num: 2 - 9, T=10, J=11, Q=12, K=13 & A=14
     * @param cCh
     * @return
     */
    private static int getCardVal(char cCh){
        for(int cv = 0; cv < cardVal.length; cv++){
            if(cardVal[cv] == cCh) return cv;            
        }
        return 0;
    }

    /**
     * Count each card face value to determine 2, 3, 4 or 5 oak and FH
     */
    private void parceHand(){
        for(int c = 0; c < 5; c++){
            held[card[c]]++;
        }
    }

    /**
     * Determine the score of the hand by reviewing each face value.
     * If only 1 skip, 2 incr 2 counter, 3 incr 3 counter, 4 done (only
     * 5 cards), 5 done (same).
     * <p>Then check 3 counter for 3 oak or full house, done.
     * <p>Else check 2 for 1 pair or 2, done done.
     * 
     * @return the scored value of the hand. 0-Hi card, 1-1 pair, 2-2 pair, 3-3 0f a kind
     * 4-full house, 5-4 oak, 6-5 oak,
     */
    private int scoreHand(){
        int val3 = 0;   //cnt of 3 oak
        int val2 = 0;   //cnt of 2 oak
        for(int c = 2; c < 15; c++){    //Don't look at 0(Holder | Joker) or 1(Holder)
            switch(held[c]){
                case 0: //No cards
                case 1: //Only 1 card of this
                break;
                case 2: //2 cards the same
                val2++;
                break;
                case 3: //3 cards the same
                val3++;
                break;
                case 4: //4 cards the same, done.
                return 5;
                case 5: //5 cards the same, done.
                return 6;
                default:    //HMmmmm?
                System.out.println("Illegal card count: " + held[c] + " for card value " + cardVal[c]);
            }
        }
        if(val3 == 1) return val2 == 1 ? 4 : 3; //full house else 3 oak
        if(val2 > 0) return val2 == 2 ? 2 : 1;  //2 pair else 1 pair
        return 0;   //hi card
    }

    /**
     * Reevaluate the hand type (5 oak, 4 oak, full house, 3 oak, 2 pair, 1 pair and hi card) 
     * using the J, Jack (part 1), as J a Joker (part 2).  Joker has no face value when
     * evaluating for hi card but can pretend to be any card for type evaluation.
     */
    public void reevalHandTypeAsJoker(){
        if(held[11] > 0){           //Reeval if there is a J(11).
            for(int c = 0; c < 5; c++) if(card[c] == 11) card[c] = 0;   //zero J val for hi card 
            held[0] = held[11];     //Move J's from Jacks (11) to Jokers (0).
            held[11] = 0;           //Zero Jacks.
            type = scoreHand();
            switch(type){
                case 0: //Hi card => 1 pair, 3 -5 oak, 1, 3, 5 or 6
                type--;
                case 1: //1 pair => 3 - 5 oak, 3, 5 or 6
                type = Math.min(6, (type + 2 * held[0]));
                break;
                case 2: //2 pair => FH, 4
                type = 4;
                break;
                case 3: //3 oak => 4 or 5 oak, 5 or 6
                type = type + 1 + held[0];
                break;
                case 4: //full house => na
                break;
                case 5: //4 oak => 5 oak
                type = 6;
                break;
                case 6: //5 oak => na
                break;
                default:    //Hmmmm?
                break;
            }
        }
    }
}