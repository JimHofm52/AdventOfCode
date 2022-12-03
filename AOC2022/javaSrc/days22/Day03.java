package days22;

import java.io.IOException;

import util.ReadInput;

public class Day03 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day03(){}

    public static void update() throws IOException {
        String fNum = "03";         //rtn 1: 7763  p2: 2569
        // String fNum = "031";     //rtn 1: 157   p2: 70
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        int[] fndChDupVal = parceDupChar(fileInfo);
        question1(fndChDupVal);    //Confirmed: 03-7763 031-157 = 16(p)+38(L)+42(P)+22(v)+20(t)+19(s)
        int[] fndChComVal = parceComChar3(fileInfo);
        question2(fndChComVal);    //Confirmed: 03-2569   031-70
    }

    /**
     * Question 1: Sum duplicates value found.
     */
    private static void question1(int[] inDupVal) {
        int totCnt = 0;
        for(int i : inDupVal) totCnt += i ;
        //Track ,  Confirmed: 03-7763   031-157 = 16(p)+38(L)+42(P)+22(v)+20(t)+19(s)
        System.out.println("\nPart 1: Total number: " + totCnt);
    }
    
    /**
     * Question 2: Sum common values found.
     */
    private static void question2(int[] inComVal) {
        int totCnt = 0;
        for(int i : inComVal) totCnt += i ;
        //Track 2429 Lo,  Confirmed: 03-2569   031-70
        System.out.println("\nPart 2: Total number: " + totCnt);
    }

    /**
     * Parce input for duplicates in the 1st & 2nd half of a line.
     * @param inFile
     * @return value for dup letter found.  a to z is 1 to 26 and A to Z is 27 to 52
     */
    private static int[] parceDupChar(String[] inFile){
        int len = inFile.length;
        int[] fndValue = new int[len];
        for(int i = 0; i < len; i++){
            int lenStr = inFile[i].length();
            int fndIdx = -1;
            for(int j = 0; j < lenStr/2; j++){
                char tstCh = inFile[i].charAt(j);
                fndIdx = inFile[i].indexOf(tstCh, lenStr/2);
                if(fndIdx > 0){
                    fndValue[i] =  tstCh - (tstCh > 96 ? 96 : (64 - 26));
                    break;
                }
            }
        }
        return fndValue;
    }


    /**
     * Parce input for common letter in 3 sequential lines.
     * @param inFile
     * @return value for common letter found.  a to z is 1 to 26 and A to Z is 27 to 52
     */
    private static int[] parceComChar3(String[] inFile){
        int len = inFile.length;
        int[] fndValue = new int[len / 3];
        for(int i = 0; i < len; i += 3){
            int lenStr = inFile[i].length();
            boolean isFnd = false;
            for(int j = 0; j < lenStr; j++){
                char tstCh = inFile[i].charAt(j);
                isFnd = inFile[i + 1].indexOf(tstCh) > -1;
                isFnd &= inFile[i + 2].indexOf(tstCh) > -1;
                if(isFnd){
                    fndValue[i/3] =  tstCh - (tstCh > 96 ? 96 : (64 - 26));
                    break;
                }
            }
        }
        return fndValue;
    }
}