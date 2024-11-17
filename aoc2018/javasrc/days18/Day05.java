package days18;

import java.io.IOException;

import util.ReadInput;

public class Day05 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day05(){}

    public static void update() throws IOException {
        String fNum = "05";    //Part1- 11540   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 05- 11540   051- 10
        question2();    //Confirmed: 05- ???   051- ???
    }

    /**
     * Question 1: The polymer is formed by smaller units which, 
     * when triggered, react with each other such that two adjacent 
     * units of the same type and opposite polarity are destroyed. 
     * Units' types are represented by letters; units' polarity is 
     * represented by capitalization.
     * 
     * How many units remain after fully reacting the polymer you scanned?
     */
    private static void question1() {
        String tmpStrIn = reactPolymer(fileInfo[0]);
        //Track ,  Confirmed: 05- 11540   Lo - 11539   051- 10
        System.out.println("\nPart 1: Units remaining: " + tmpStrIn.length() + "\n");
    }
    
    /**
     * Question 2: One of the unit types is causing problems; it's preventing 
     * the polymer from collapsing as much as it should. Your goal is to 
     * figure out which unit type is causing the most problems, remove all 
     * instances of it (regardless of polarity), fully react the remaining polymer, 
     * and measure its length.
     * 
     * In this example, removing all C/c units and fully reacting was best, 
     * producing the answer 4.
     * What is the length of the shortest polymer you can produce by removing 
     * all units of exactly one type and fully reacting the result?
     */
    private static void question2() {
        int minRRPoly = fileInfo[0].length();
        for(char ch = 'a'; ch <= 'z'; ch++){
            String tmpStrIn = reducePolymer(fileInfo[0], (ch));
            tmpStrIn = reactPolymer(tmpStrIn);
            if(tmpStrIn.length() < minRRPoly){
                minRRPoly = tmpStrIn.length();
            }
        }
        //Track ,  Confirmed: 05- ???     051- 4
        System.out.println("\nPart 2: ???: " + minRRPoly);
    }

    /**
     * React, eliminate, all unit combinations (ie. aA & Aa to zZ & Zz)
     * @param strIn polymer string
     * @return
     */
    private static String reactPolymer(String strIn){
        String str = strIn.substring(0);
        char ch1, ch2;
        String strReduc;
        int strLen;
        do{
            strLen = str.length();
            for(int c = 0; c < 27; c++){
                ch1 = (char)('a' + c);  ch2 = (char)('A' + c);
                strReduc = "" + ch1 + ch2;
                str = str.replace(strReduc, "");
                strReduc = "" + ch2 + ch1;
                str = str.replace(strReduc, "");
            }
        } while(str.length() != strLen);
        return str;
    }

    /**
     * This is used by Question 2 to reduce the ploymer by the char passed, both 
     * upper & lower cases. 
     * @param strIn polymer string
     * @param ch char unit to eliminate, reduce
     * @return the reduced string polymer
     */
    private static String reducePolymer(String strIn, char ch){
        String str = strIn.substring(0);
        String strReduc;

        strReduc = "" + Character.toLowerCase(ch);
        str = str.replace(strReduc, "");
        strReduc = "" + Character.toUpperCase(ch);
        str = str.replace(strReduc, "");
        return str;
    }

}