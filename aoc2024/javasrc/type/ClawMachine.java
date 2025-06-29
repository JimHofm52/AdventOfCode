package type;

public class ClawMachine {
    private int[] btnA;     //XY move per press (Cost 3 coins) and press cnt
    private int[] btnB;     //XY move per press (Costs 1 coin) and press cnt
    private int[] prize;    //XY Loc of prize and total cost

    /**
     * Constructor - values set external.
     */
    public ClawMachine(){ }

    /**
     * Constructor with 3 string with info for button A & B XY move per press
     * and prize XY location.
     * @param inStr string array[3] of button and prize info.
     */
    public ClawMachine(String[] inStr){
        btnA = parceStr(inStr[0], true);
        btnB = parceStr(inStr[1], true);
        prize = parceStr(inStr[2], false);
        findMove(btnA, btnB,prize);
    }

    /**
     * Pace the string in to XY info for either buttons of prize
     * @param strIn
     * @param isBtn
     * @return int array of XY coor.
     */
    private static int[] parceStr(String strIn, boolean isBtn){
        int beg = isBtn ? 12 : 9;
        int end = strIn.indexOf(',');
        int[] tmp = new int[3]; //[0]=>X, [1]=>Y, [2]=>cnt
        tmp[0] = Integer.parseInt(strIn.substring(beg, end));
        tmp[1] = Integer.parseInt(strIn.substring(end + 4));
        tmp[2] = isBtn ? 0 : 500;
        return tmp;
    }

    public int getCost(){ return prize[2] < 500 ? prize[2] : 0; }

    private static void findMove(int[] btnA, int[] btnB, int[] prize){
        int cntA;
        int cntB = 0;
        int tmp;
        int tot;
        do{                             //Try cheap btn first, start with X
            tot = (btnB[0] * ++cntB);   //Calc B tot move
            tmp = (prize[0] - tot);     //Calc diff = A tot move
            if(tmp % btnA[0] == 0){     //if A evenly div, A & B for X fnd, test Y
                cntA = tmp / btnA[0];   //calc A cnt
                tmp = (btnA[1] * cntA + btnB[1] * cntB);    //Use cnts to calc Y move
                if(tmp == prize[1]){    //if Y matches, chk new cost is lower
                    tmp = cntA * 3 + cntB;
                    if(tmp < prize[2]){     //If lower cost update numbers
                        btnA[2] = cntA;
                        btnB[2] = cntB;
                        prize[2] = tmp;
                    }
                }
            }
        }while(cntB < 100  && cntB * btnB[0] < prize[0]);

        cntA = 0;
        do{                             //Try the other btn, start with X
            tot = btnA[0] * ++cntA;     //Calc A tot move
            tmp = prize[0] - tot;       //Calc diff = B tot move
            if(tmp % btnB[0] == 0){     //if evenly div, A & B for X fnd, test Y
                cntB = tmp / btnA[0];   //calc B cnt
                tmp = (btnA[1] * cntB + btnB[1] * cntA);    //calc tot Y move
                if(tmp == prize[1]){        //if Y matches, chk new cost is lower
                    tmp = cntA * 3 + cntB;  //new cost
                    if(tmp < prize[2]){     //If lower cost update numbers
                        btnA[2] = cntA;
                        btnB[2] = cntB;
                        prize[2] = tmp;
                    }
                }
            }
        }while(cntA < 100  && cntA * btnA[0] < prize[0]);   //cont. until GT 100 or move is GT prize position
    }

}
