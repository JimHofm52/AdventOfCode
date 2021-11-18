package days;

import java.io.IOException;

import util.*;

public class Day16{

    public static void update() throws IOException {
        int fileNum = 16;                   //File number of input
        String dataIn[] = ReadInput.GetInputStr(fileNum);// Get input in an array
        int len = dataIn.length;

        int[][] rules = Parce_rules(dataIn);    //[][0]-Lo1 [][1]-Hi1 [][2]-Lo2 [][3]-Hi2
        int[] myTkt = Parce_myTkt(dataIn);      //Returns rule value for my tkt
        int[][] nbTkt = Parce_nbTkt(dataIn, rules.length);  //Return nearby tkts rule values

        // ---- Part 1 ------
        long ans = AddBadNums(nbTkt, rules); //Adds bad vals. Mods nbTkt w/ rule match, 20 1's matches all
        long a = 0;
        System.out.println("\nPart 1 - " + ans);    // Confirmed - Part 1 - 32842(161 - 71)

        // ----- Part 2 ------
        ans = MatchRules(nbTkt, myTkt);     //Find which col matches rules, apply Departure in my tkt
        System.out.println("\nPart 2 - " + ans);// Confirmed - Part 2 - 2628667251989(161 - 98)
        //Lo-147266837
    }

    // ----------------------------------- Parce Input ----------------------------------
    private static int[][] Parce_rules(String dataIn[]){
        int i = 0;
        while(!dataIn[i].equals("")) i++;
        int rules[][] = new int[i][4];

        String str;
        int beg, end;
        i = 0;
        str = dataIn[i];
        do{
            beg = str.indexOf(":") + 2;
            end = str.indexOf("-");
            rules[i][0] = Integer.parseInt(str.substring(beg, end));
            beg = end + 1;
            end = str.indexOf(" or");
            rules[i][1] = Integer.parseInt(str.substring(beg, end));
            beg = end + 4;
            end = str.indexOf("-", beg);
            rules[i][2] = Integer.parseInt(str.substring(beg, end));
            beg = end + 1;
            rules[i][3] = Integer.parseInt(str.substring(beg));
            i++;
            str = dataIn[i];
        }while(!str.equals(""));
        return rules;
    }

    private static int[] Parce_myTkt(String[] dataIn){
        int mtNdx = 0;
        while(dataIn[mtNdx++].indexOf("your ticket") < 0);   //Find start of nearby
        return ReadInput.ParceStrIntCS(dataIn[mtNdx]);
    }

    private static int[][] Parce_nbTkt(String[] dataIn, int numRules){
        int nbNdx = 0;
        while(dataIn[nbNdx++].indexOf("nearby ticket") < 0);   //Find start of nearby
        int[][] nbTkt = new int[dataIn.length - nbNdx][numRules];

        //Parce nb ticket values
        for(int i = nbNdx; i < dataIn.length; i++){     //Parce nb value
            nbTkt[i - nbNdx] = ReadInput.ParceStrIntCS(dataIn[i]);
        }
        return nbTkt;
    }

    // ----------------------------------- Part 1 ----------------------------------
    /**
     * Sum any invalid values, don't match any rules.
     * Set bit for each rule matched.  20 1's is matches all. No match set to -1.
     * Sum any set to -1.
     * Pass back bit col mask for part 2.
     * 
     * @param nbTkt
     * @param rules
     * @return
     */
    private static long AddBadNums(int[][] nbTkt, int[][] rules){
        int nbLen = nbTkt.length;
        int numRules = rules.length;
        int nbRslt[][] = new int[nbTkt.length][rules.length];

        //Evaluate each tkt and value nbTkt[][]
        for(int i = 0; i < nbLen; i++){             //For each near ticket
            for(int j = 0; j < numRules; j++){      //For each int in near ticket
                int mask = 1;
                for(int r = 0; r < numRules; r++){  //Chk against each rule
                    if(Betwn(nbTkt[i][j], rules[r][0], rules[r][1], true) ||
                        Betwn(nbTkt[i][j], rules[r][2], rules[r][3], true))
                    {
                        nbRslt[i][j] |= mask;
                    }
                    mask = mask << 1;
                }
            }
        }

        //Sum bad numbers.  Zero value, no match to rules, bad tkt, add value to badSum.
        //zero all values on this tkt for part 2 eval.
        long badSum = 0;
        for(int i = 0; i < nbLen; i++){         //In each tkt
            for(int j = 0; j < numRules; j++){  //for each rule, col
                if(nbRslt[i][j] == 0){          //if illegal, didn't match any rule,
                    badSum += nbTkt[i][j];      //add value to badSum (part 1)
                    for(int k = 0; k < numRules; k++){  //Flag whole tkt as bad (for part 2)
                        for(int x = 0; x < numRules; x++) nbRslt[i][j] = -1;
                    }
                } 
            }
        }
        AryUtil.CopyInt2By(nbRslt, nbTkt); //Overwrite value with rules matched, 20 1's matches all
        return badSum;
    }

    // ----------------------------------- Part 2 ----------------------------------
    /**
     * Multiple columns that start with Departure, first 6 columns of the Rules
     * from my ticket.
     * First need to evaluate columns to match then to Rule numer, index.
     * 
     * @param nbTkt
     * @param myTkt
     * @return
     */
    private static long MatchRules(int[][] nbTkt, int[] myTkt){
        int nbLen = nbTkt.length;
        int ruleLen = nbTkt[0].length;

        // Match columns to Rules.  AND all valid values in each col
        int[] colForRule = new int[ruleLen];    //Array for each Column for a rule
        for(int i = 0; i < ruleLen; i++){       //For each col
            colForRule[i] = nbTkt[0][i];        //Initialize with first tkt value.
            for(int j = 1; j < nbLen; j++){     //in all nb tkts
                if(nbTkt[j][i] > 0){
                    colForRule[i] &= nbTkt[j][i];//AND the rest of the tkts.
                }
            }
        }

        // Find any single 1's, only match for that column.  AND inverse against other
        // columns to eliminate that column from other columns.
        boolean done;
        boolean[] colFnd = new boolean[ruleLen];   //Final value for col found
        do{
            for(int i = 0; i < ruleLen; i++){          //For each col
                if(!colFnd[i] && OnePingOnly(colForRule[i], 20)){     //If a digit & not already found
                    for(int j = 0; j < ruleLen; j++){  //Mask it against other cols
                        if(j != i) colForRule[j] &= ~colForRule[i];
                    }
                    colFnd[i] = true;                      //Flag for future
                }
            }
            done = true;
            for(int i = 0; i < ruleLen; i++) done &= colFnd[i]; //Are all done?
        }while(!done);

        //Multiple the values from my tkt for the Rules that start with Departure.
        //Cheated - Departure are the first 6 rules, index 0 - 5.
        long ans = 1;
        int j;
        for(int i = 0; i < 6; i++){     //Multiple first 6 redirects
            for(j = 0; j < ruleLen; j++){
                if(colForRule[j] == 1 << i){
                    ans *= myTkt[j];
                }
            }
        }
        return ans;
    }

    /**
     * Check if number is between the lo & hi values.  If inclusive include equal to the lo/hi
     * 
     * @param num
     * @param lmtLo
     * @param lmtHi
     * @param inclusive
     * @return
     */
    private static boolean Betwn(long num, int lmtLo, int lmtHi, boolean inclusive){
        if(num >= lmtLo && num <= lmtHi && inclusive) return true;
        if(num > lmtLo && num < lmtHi) return true;
        return false;
    }
    /**
     * Check if there is only 1 bit set for this number, power of 2.
     * 
     * @param num
     * @param len - number of bits to check
     * @return - true if only 1 bit set.
     */
    private static boolean OnePingOnly(int num, int len){
        int mask = 1;
        for(int i = 0; i < len; i++){
            if(num == mask) return true;
            mask = mask << 1;
        }
        return false;
    }
}