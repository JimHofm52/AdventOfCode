package days;

import java.io.IOException;
import util.ReadInput;

public class Day7 {

    public static void update() throws IOException {
        String fileInfo[];
        fileInfo = ReadInput.GetInputStr(7);   //Get input in an array for 7
        int len = fileInfo.length;          //Length of input array

        String rules[][] = new String[2][len];  //All Outside Colors[0] & the rest[1], easy searches
        String fndColor[] = new String[len];    //Colors found to match
        boolean trkColor[] = new boolean[len];  //Track if a rule has already been used (594)
        //Initalize arrays
        for(int s = 0; s < rules.length; s++){
            rules[0][s] = "";   //Outside
            rules[1][s] = "";   //The rest
        }
        for(int s = 0; s < fndColor.length; s++) fndColor[s] = "";
        GetRules(fileInfo, rules);

        //------------------- Part 1 ------------------------------

        fndColor[0] = "shiny gold";     //Seed
        int outClrCnt = 0;              //Count for a outside color level
        int strtNdx = 0;                //start index of colors, do not repeat searches
        int endNdx = strtNdx + 1;       //end index of colors.  Add found color count
        do {
            outClrCnt = FindMatch(rules, fndColor, trkColor, strtNdx, endNdx);
            strtNdx = endNdx;
            endNdx += outClrCnt;
        }while (outClrCnt != 0);
            
        outClrCnt = CntColors(trkColor);

        //Start index, return value (found colors) pairs
        //0, 5 || 1, 30 || 6, 77 || 36, 83 || 113, 75 || 196, 45 || 271, 16
        //316, 5 || 332, 1 || 337, 0 || 338, 0

        System.out.println("\n\nTurducken Colors -\t" + (outClrCnt));   //confirmed 337


        //------------------- Part 2 ------------------------------

        String color[][] = new String[5][len];  //All Outside Colors[0] & the rest[1], easy searches
        //Initalize arrays
        for(int s = 0; s < len; s++) color[0][s] = "";
        for(int s = 0; s < len; s++) color[1][s] = "";

        int cntAll = 0;
        String myBag = "shiny gold";

        cntAll = GetBagCnt( rules, myBag, cntAll );
        cntAll--;   //Don't onclude the Gold bag
        System.out.println("Bag-O-Bags \t-\t" + cntAll);  //confirmed 50100
    }

    //------------------------- Common ----------------------------------

    /**
     * Breakup rule into outside color[0] & the rest of the rule[1] and initialize.
     * 
     * @param inFile
     * @param rules
     * @return
     */
    private static int GetRules(String inFile[], String rules[][]){
        int ndx = 0;
        for( String in : inFile){
            if(in != null && in !=""){
                rules[0][ndx] = in.substring(0, in.indexOf(" bags contain"));   //Get out color
                rules[1][ndx] = in.substring(in.indexOf(" bags contain") + 14); //Get the rest
            }else{
                rules[0][ndx] = "";
                rules[1][ndx] = "";
            }
            ndx++;
        }
        return ndx;
    }


    //------------------------------------  Part 1 --------------------------

    /**
     * Track which rules have been already captured
     * 
     * @param inFile
     * @param outColors
     * @param trkClr
     * @param strt
     * @return
     */
    private static int FindMatch(String rules[][], String fndColor[], boolean trkClr[], int strt, int end){
        int cnt = 0;
        int oNdx = 0;

        for (int i = strt; i < end; i++) {
            oNdx = 0;
            for(String s : rules[1]){
                if(s.contains(fndColor[i])){                    //If found
                    if(!trkClr[oNdx]){                      //and not found before
                        trkClr[oNdx] = true;                //Found now
                        fndColor[end + cnt] = rules[0][oNdx];   //Save the color
                        cnt++;
                    }
                }
                oNdx++;
            }
        }
        return cnt; //Return cnt of matched items in inner rules     
    }

    /**
     *  Count the colors found in track colors
     * 
     * @param trkClr
     * @return
     */
    private static int CntColors( boolean trkClr[]){
        int cnt = 0;

        for( boolean b : trkClr ) if(b) cnt++;
        return cnt;
    }


    //--------------------------- Part 2 ------------------------------------------

    /**
     * Get the bad count.  Recursive.
     * 
     * @param rules
     * @param color
     * @param cntAll
     * @return
     */
    private static int GetBagCnt( String rules[][], String color, int cntAll){
        int bagCnt = 0;
        int ndx = 0;
        int ruleNdx = 0;
        String myColor = color;
        String myRule = "";
        String aColor[] = new String[5];
        int numOf[] = new int[5];

        for( int i = 0; i < 5; i++) aColor[i] = "";

        while((!myColor.equals(rules[0][ndx])) && (ndx < 600)) ndx++; //Find color index
        myRule = rules[1][ndx];     //Get matching rule

        ParceRule( myRule, numOf, aColor);    //Returns num of colors and the colors in arrays
        if(numOf[0] == 0) return 1;
        ruleNdx = 0;
        do{
            myColor = aColor[ruleNdx];
            bagCnt += numOf[ruleNdx] * GetBagCnt(rules,myColor, cntAll);
            ruleNdx++;
        }while(ruleNdx < 5 && !aColor[ruleNdx].equals("") );
        cntAll += bagCnt;
        return bagCnt + 1;
    }

    /**
     * Parce a rule to bag cnt for a color.
     * 
     * @param color
     * @param numOf
     * @param rule
     */
    private static void ParceRule(String color, int numOf[], String rule[]){
        int i = 0;

        //dotted black bags contain   |no other bags.
        //striped yellow bags contain |1 posh yellow bag.
        //shiny violet bags contain   |3 vibrant crimson bags.
        //dim cyan bags contain       |1 faded indigo bag, 1 dotted blue bag.
        //dotted blue bags contain    |3 wavy bronze bags, 5 clear tomato bags.
        //dotted red bags contain     |2 drab white bags, 2 bright cyan bags, 4 bright coral bags, 5 drab maroon bags.

        String sTmp = color;
        int iTmp = 0;

        do{
            if( sTmp.startsWith("no")) sTmp = sTmp.replace("no", "0");   //Forms "0 other bags."
            numOf[i] = Integer.parseInt(sTmp.substring(0, 1));  //Store # from start, single digit

            sTmp = sTmp.substring(2);           //Drop #space
            iTmp = sTmp.indexOf(" bag");        //Find bag(s) index
            rule[i] = sTmp.substring(0, iTmp);  //Store color from 0 to " bag"

            //Drop previous rule and clean up
            sTmp = sTmp.substring(iTmp + 5);    //Drop to " bag" + 1
            // Now starts with /. /, /s. /s, /   "." = done, "s" 1 v. 2
            if( sTmp.length() > 6){     //bag(s). is end, if more then another rule
                sTmp = sTmp.substring(1);    //Drop "." or ","
                sTmp = sTmp.trim();
                i++;
            }else{
                sTmp = "";
            }
        }while (sTmp.length() > 0);
    }
}