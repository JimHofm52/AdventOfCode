package days15;

import java.io.IOException;

import util.ReadInput;

public class Day05 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day05(){}

    public static void update() throws IOException {
        String fNum = "05";//Part1- ???   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 05- 255  hi-256 051- 2
        question2();    //Confirmed: 05- 55   no-53  052- 2
    }

    /**
     * Question 1: A nice string is one with all of the following properties:
     * It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
     * It contains at least one letter that appears twice in a row, like xx, abcdde (dd), 
     * or aabbccdd (aa, bb, cc, or dd).
     * It does not contain the strings ab, cd, pq, or xy, even if they are part of one of 
     * the other requirements.
     */
    private static void question1() {
        int testBits = 0;
        int cnt = 0;
        int allCnt = 0;
        for(String str : fileInfo){
            allCnt++;
            testBits = has3Vowels(str) ? 1 : 0;
            testBits += hasdblChar(str) ? 2 : 0;
            testBits += !hasCharPair(str) ? 4 : 0;
            if(testBits == 7){
                cnt++;
                // System.out.println(cnt + ". " + "bits: " + testBits + " for " + str);
            }
            // if(allCnt == 998){
            //     int a = 0;
            // }
            // System.out.println(allCnt + ". " + "bits: " + testBits + " for " + str);
        }
        //Track ,  Confirmed: 05- 255 hi-256  051- 2
        System.out.println("\nPart 1: There are " + cnt + " nice strings and " + (len - cnt) + " naughty");
    }
    
    /**
     * Question 2: A nice string is NOW one with all of the following properties:
     * Contains a pair of any two letters that appears at least twice in the string 
     * without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa 
     * (aa, but it overlaps).
     * Contains at least one letter which repeats with exactly one letter between 
     * them, like xyx, abcdefeghi (efe), or even aaa.
     */
    private static void question2() {
        int testBits = 0;
        int cnt = 0;
        int allCnt = 0;
        for(String str : fileInfo){
            allCnt++;
            testBits = hasDblPair(str) ? 1 : 0;
            testBits += has_xyx_Pattern(str) ? 2 : 0;
            if(testBits == 3){
                cnt++;
                // System.out.println(cnt + ". " + "bits: " + testBits + " for " + str);
            }
            // if(allCnt == 998){
            //     int a = 0;
            // }
            // System.out.println(allCnt + ". " + "bits: " + testBits + " for " + str);
        }
        //Track ,  Confirmed: 05- 55   052- 2
        System.out.println("\nPart 2: There are now " + cnt + " nice strings and " + (len - cnt) + " naughty");
    }

    //---------------- Tests ----------------------
    //----------------Part 1 ----------------------
    /**Contains at least three vowels (aeiou only), like aei or aaa */
    private static boolean has3Vowels(String strIn){
        int cnt = 0;
        final char[] vowel = {'a', 'e', 'i', 'o', 'u'};
        for(int i = 0; i < strIn.length(); i++){
            for(char v : vowel) if(strIn.charAt(i) == v) cnt++;
            if(cnt == 3) return true;
        }
        return false;
    }

    /**Contains at least one letter that appears twice in a row, like xx */
    private static boolean hasdblChar(String strIn){
        int cnt = 0;
        String dblCh = "";
        for(char ch = 'a'; ch <= 'z'; ch++){
            dblCh = String.valueOf(ch) + String.valueOf(ch);
            if(strIn.contains(dblCh)) cnt++;
            if(cnt == 1) return true;
        }
        return false;
    }

    /**Contain the strings ab, cd, pq, or xy */
    private static boolean hasCharPair(String strIn){
        int cnt = 0;
        final String[] pair = {"ab", "cd", "pq", "xy"};
        for(String p : pair) if(strIn.contains(p)){
            cnt++;
            if(cnt == 1) return true;
        }
        return false;
    }

    //---------------------- Part 2 -------------------------
    /**Has double pairs, xyasdfxy (xy) */
    private static boolean hasDblPair(String strIn){
        String testStrIn = strIn;
        String chPair = "";
        for(int i = 0; i < strIn.length() - 1; i++){
            chPair = strIn.substring(i, i + 2); //get 2 char
            if(strIn.indexOf(chPair, i + 2) > 0) return true;
        }
        return false;
    }

    private static boolean has_xyx_Pattern(String strIn){
        char ch;
        for(int i = 0; i < strIn.length() - 2; i++){
            ch = strIn.charAt(i);
            if(ch == strIn.charAt(i + 2)) return true;
        }
        return false;
    }
}