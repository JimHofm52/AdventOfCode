package days21;

import java.io.IOException;
import java.util.Arrays;

import util.ReadWriteFiles;

public class Day10 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day10(){}

    public static void update() throws IOException {
        String fNum = "10";
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 10-341823   101-26397
        question2();    //Confirmed: 10-2801302861   101-288957
    }

    /**
     * Question 1: Find first syntax error.
     * <P> Valid (), [], {} and <>.  Points: 3, 57, 1197, 25137, respectfully
     * <p>
     * <p>Note: don't need Question 1. Question 2 used most of the same code and
     * calculates both.  Left this for comparison.
     */
    private static void question1() {
        char chIn, chCls;
        String strSyn;
        boolean matched;
        int synScore = 0;
        for(String str : fileInfo){
            strSyn = "";
            chCls = ' ';
            do{
                chIn = str.charAt(0);
                str = str.substring(1);
                matched = true;
                switch(chIn){
                    case '(':
                    case '[':
                    case '{':
                    case '<':
                    strSyn = strSyn + chIn;
                    break;
                    case ')':
                    case ']':
                    case '}':
                    case '>':
                    chCls = strSyn.charAt(strSyn.length() - 1);
                    strSyn = strSyn.substring(0, strSyn.length() - 1);
                    matched = chkMatch(chIn, chCls);
                    break;
                    default:
                }
            }while(matched && !str.isEmpty());
            if(!matched) synScore += getErrScore(chIn);
        }
        //Track ,  Confirmed: 10-341823   101-26397
        System.out.println("\nPart 1: Syntax error score: " + synScore);
    }

    /**
     * Question 2: Find the middle of all the chunk completion scores.
     * <p>Note: left part 1 since they almost use the same code.  For comparison.
     */
    private static void question2() {
        String str;
        char chIn, chCls;
        String strSyn;
        boolean matched;
        int synErrScore = 0;    //Part 1 score
        int sis = 0;
        long[] synIncompScore = new long[sis];  //Sized 0, resized later as needed
        for(int s = 0; s < len; s++){
            str = fileInfo[s];
            strSyn = "";
            chCls = ' ';
            do{
                chIn = str.charAt(0);
                str = str.substring(1);
                matched = true;
                switch(chIn){
                    case '(':
                    case '[':
                    case '{':
                    case '<':
                    strSyn = strSyn + chIn;
                    break;
                    case ')':
                    case ']':
                    case '}':
                    case '>':
                    chCls = strSyn.charAt(strSyn.length() - 1);
                    strSyn = strSyn.substring(0, strSyn.length() - 1);
                    matched = chkMatch(chIn, chCls);
                    break;
                    default:
                }
            }while(matched && !str.isEmpty());
            if(!matched){
                synErrScore += getErrScore(chIn);
            }else{
                synIncompScore = Arrays.copyOf(synIncompScore, ++sis);
                synIncompScore[sis - 1] = synIncompScore(strSyn);
            }
        }
        Arrays.sort(synIncompScore);
        //Track 141193416 Lo,  Confirmed: 10-2801302861   101-288957
        System.out.println("\nPart (1): Syntax error score: " + synErrScore);
        System.out.println("Part 2: Syntax Completion score: " +
                                synIncompScore[synIncompScore.length/2]);
    }

    /**
     * Check if the passed char are a open/close match.
     * @param clsCh Closing char ), ], } or >
     * @param opnCh Opening char (, [, { or <
     * @return true if matched
     */
    private static boolean chkMatch(char clsCh, char opnCh){
        switch(clsCh){
            case ')':
            if(opnCh == '(') return true;
            break;
            case ']':
            if(opnCh == '[') return true;
            break;
            case '}':
            if(opnCh == '{') return true;
            break;
            case '>':
            if(opnCh == '<') return true;
            break;
            default:
            System.out.println("WTF: " + clsCh);
        }
        return false;
    }

    /**
     * Get the score for each of the 4 chunk designators errors.
     * @param clsCh Char to score
     * @return error chunk score
     */
    private static int getErrScore(char clsCh){
        switch(clsCh){
            case ')':
            return 3;
            case ']':
            return 57;
            case '}':
            return 1197;
            case '>':
            return 25137;
            default:
            System.out.println("No score for: " + clsCh);
        }
        return 0;
    }

    /**
     * Calc syntax incomplete score for remaining chunk designators.
     * @param strIn String of missing matched chunk designators.
     * @return score
     */
    private static long synIncompScore(String strIn){
        int strLen = strIn.length() - 1;
        long scoreIt = 0;
        for(int ch = 0; ch < strIn.length(); ch++){
            scoreIt *= 5;
            scoreIt += getCompScore(strIn.charAt(strLen - ch));
        }
        return scoreIt;
    }   

    /**
     * Get the score for a chunk designator.
     * @param clsCh chunk designator
     * @return single score
     */
    private static int getCompScore(char clsCh){
        switch(clsCh){
            case '(':
            return 1;
            case '[':
            return 2;
            case '{':
            return 3;
            case '<':
            return 4;
            default:
            System.out.println("No score for: " + clsCh);
        }
        return 0;
    }

}