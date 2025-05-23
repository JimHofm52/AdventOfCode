package days21;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day04 {
    private static String fileInfo[];
    private static int len;
    private static int[] callNums;      //Nums called
    private static int[][][] cards;     //Bingo cards w/row & col values
    private static boolean[] cardWon;   //Trk cards won, speed searches.
    //The following is used to capture last won card info.
    private static int[] winCardInfo = new int[6];   //[0]=bi, [1]=ri, [2]=ci, 
    //                                [3]=Series sum, [4]=last Num, [5]=unmarked sum

    /** Constructor, not needed but used for standards. */
    private Day04(){}

    public static void update() throws IOException {
        String fNum = "04";
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        callNums = parceNums();
        cards = parceCards();
        cardWon = new boolean[cards.length];

        question1();    //Confirmed: 04-c12, 824 * 8 = 6592   041-c3, 188 * 24 = 4512
        question2();    //Confirmed: 04-???   041-c2, 148 * 13 = 1924
    }

    /**
     * Question 1: Find the first winning card:
     */
    private static void question1() {
        findWinCard(1);
        sumUnmarked();
        //Track ,  Confirmed: 04-c12, 824 * 8 = 6592   041-c3, 188 * 24 = 4512
        System.out.println("\nPart 1: Bingo card: " + (winCardInfo[0] + 1) +
            ".  Unmarked: " + winCardInfo[5] + ".  Last Num picked: " + winCardInfo[4] +
            ".  Score is: " + winCardInfo[5] * winCardInfo[4]);
    }
    
    /**
     * Question 2: Find the last winning card:
     */
    private static void question2() {

        findWinCard(2);
        sumUnmarked();
        //Track 16566 Lo, 33228 Hi,
        //Confirmed: 04-c40, 365 * 87 = 31755   041-c2, 148 * 13 = 1924
        System.out.println("\nPart 2: Bingo card: " + (winCardInfo[0] + 1) +
            ".  Unmarked: " + winCardInfo[5] + ".  Last Num picked: " + winCardInfo[4] +
            ".  Score is: " + winCardInfo[5] * winCardInfo[4]);
    }

    /**
     * Find winning card(s).
     * @param part 1 - 1st winner else (2) last winner
     * @return
     */
    private static int findWinCard(int part){
        boolean fndNum = false;
        int tmp;

        for(int called : callNums){
            for(int bcIdx = 0; bcIdx < cards.length; bcIdx++){
                if(!cardWon[bcIdx]){    //If card not already won then
                    fndNum = false;     //set found num to false
                    for(int rIdx = 0; rIdx < 5; rIdx++){        //Chk row
                        for(int cIdx= 0; cIdx < 5; cIdx++){     //Column for match
                            if(cards[bcIdx][rIdx][cIdx] == called){ //If match found
                                cards[bcIdx][rIdx][cIdx] += 1000;   //tag num found
                                fndNum = true;
                                tmp = chkWinning(bcIdx, rIdx, cIdx);//Chk if card now winner
                                if(tmp > 0){            //If winner save info
                                    winCardInfo[0] = bcIdx;
                                    winCardInfo[1] = rIdx;
                                    winCardInfo[2] = cIdx;
                                    winCardInfo[3] = tmp;
                                    winCardInfo[4] = called; //Last num called
                                    cardWon[bcIdx] = true;  //card won: speed it up, don't chk again
                                    if(part == 1) return 1; //Part 1: 1st card
                                }
                            }
                            if(fndNum) break;
                        }   //end col
                        if(fndNum) break;
                    }   //end row
                }
            }   //end card
        }   //end num
        return 2;
    }

    /**
     * Chk for a winning card.  Row or column only, no diagonals.
     * <p>Note: cells flagged as called by being .GT. 1000, 1xxx.
     * <p>If sum of a row or column add to more than 5000 then card is a winner.
     * <p>Only the row & col just called needs to be checked.
     * 
     * @param bci Bingo card
     * @param ri Row index
     * @param ci Column index
     * @return 1xxx if row match or 2xxx if col match else 0
     */
    private static int chkWinning(int bci, int ri, int ci){
        int numSum = 0;
        for(int i = 0; i < 5; i++) numSum += cards[bci][ri][i];
        if(numSum > 5000) return numSum - 4000; //return 1xxx if row match
        numSum = 0;
        for(int i = 0; i < 5; i++) numSum += cards[bci][i][ci];
        if(numSum > 5000) return numSum - 3000; //return 2xxx if col match
        return 0;
    }

    /**
     * Find the sum of values on the card that have NOT been called.
     * <p>Note: Cells called are flagged by being .GT. 1000, 1xxx are called.
     * <p>Sum returned in winCardIdx[5].
     */
    private static void sumUnmarked(){
        int bi = winCardInfo[0];
        int sumNum = 0;
        for(int ri = 0; ri < 5; ri++){
            for(int ci = 0; ci < 5; ci++){
                if(cards[bi][ri][ci] < 1000){
                    sumNum += cards[bi][ri][ci];
                }
            }
        }
        winCardInfo[5] = sumNum; //return sum of uncalled cells.
    }

    /**
     * Get list of nums called.  1st string.
     * @return int array of nums called
     */
    private static int[] parceNums(){
        String str = fileInfo[0];
        int commaCnt = 0;
        for(int i = 0; i < str.length(); i++) if(str.charAt(i) == ',') commaCnt++;

        int[] input = new int[commaCnt + 1];
        int end;
        for(int i = 0; i < commaCnt; i++){
            end = str.indexOf(",");
            input[i] = Integer.parseInt(str.substring(0, end));
            str = str.substring(end + 1);
        }
        input[commaCnt] = Integer.parseInt(str.substring(0));
        return input;
    }

    /**
     * Get a list of cards, with row & col values
     * @return 3d array of [cards][row][col] values
     */
    private static int[][][] parceCards(){
        String[] input = fileInfo;
        int cardCnt = 0;
        String row;
        int bcIdx = 0;
        for(String str : input) if(str.isBlank()) cardCnt++;
        int[][][] cards = new int[cardCnt][5][5];

        for(int i = 2; i < input.length; i += 6){   //Cards start on row 2
            for(int rIdx = 0; rIdx < 5; rIdx++){
                row = input[i + rIdx].trim();
                for(int cIdx = 0; cIdx < 4; cIdx++){
                    int end = row.indexOf(" ");
                    cards[bcIdx][rIdx][cIdx] = Integer.parseInt(row.substring(0, end));
                    row = row.substring(end + 1).trim();
                }
                cards[bcIdx][rIdx][4] = Integer.parseInt(row);
            }
            bcIdx++;
        }
        return cards;
    }
}