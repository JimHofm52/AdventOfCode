package days18;

import java.io.IOException;

import type.BoxID;
import util.ReadWriteFiles;

public class Day02 {
    private static String fileInfo[];
    private static int len;
    private static BoxID[] myIDs;

    /** Constructor, not needed but used for standards. */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02"; //Part1- 6175   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        myIDs = new BoxID[len];
        parceInput();

        question1();    //Confirmed: 02- 6175
        question2();    //Confirmed: 02- asgwjcmzredihqoutcylvzinx  (indexs 33 & 131, char 9)
    }

    /**
     * Question 1: Of these box IDs, four of them contain a letter which 
     * appears exactly twice, and three of them contain a letter which 
     * appears exactly three times. Multiplying these together produces 
     * a checksum of 4 * 3 = 12.
     * 
     * What is the checksum for your list of box IDs?
     */
    private static void question1() {
        int cnt2 = 0;
        int cnt3 = 0;
        for(BoxID b : myIDs){
            if(b.get2Cnt() > 0) cnt2++;
            if(b.get3Cnt() > 0) cnt3++;
        }
        //Track ,  Confirmed: 02- 6175
        System.out.println("\nPart 1: Checksum: " + cnt2 * cnt3);
    }
    
    /**
     * Question 2: The IDs abcde and axcye are close, but they 
     * differ by two characters (the second and fourth). However, 
     * the IDs fghij and fguij differ by exactly one character, 
     * the third (h and u). Those must be the correct boxes.
     * 
     * What letters are common between the two correct box IDs?
     */
    private static void question2() {
        String fnd = findClsEq();
        //Track ,  Confirmed: 02- asgwjcmzredihqoutcylvzinx
        System.out.println("\nPart 2: Common letters: " + fnd);
    }

    private static void parceInput(){
        for (int i = 0; i < len; i++) {
            myIDs[i] = new BoxID(fileInfo[i]);
        }
    }

    private static String findClsEq(){
        int uneqCnt = 0;
        int chLoc = 0;
        int idLen = fileInfo[0].length();
        for(int i = 0; i < len - 1; i++){
            for(int j = i + 1; j < len; j++){
                uneqCnt = 0;
                for(int k = 0; k < idLen; k++){
                    if(fileInfo[i].charAt(k) != fileInfo[j].charAt(k)){
                        uneqCnt++;
                        chLoc = k;
                    }
                }
                if(uneqCnt == 1){
                    return fileInfo[j].substring(0, chLoc) + fileInfo[j].substring(chLoc + 1);
                }
            }
        }
        return "None Found";
    }
}