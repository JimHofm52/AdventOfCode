package days22;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day06 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day06(){}

    public static void update() throws IOException {
        String fNum = "06"; //Part1- 1757   Part2- 2950
        // String fNum = "061";//Part1- 1 - 5: 7, 5, 6, 10, 11   Part2- 1 - 5: 19, 23, 23, 29, 26
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        String[] msgIn = new String[fileInfo.length];
        for(int i = 0; i < fileInfo.length; i++) msgIn[i] = fileInfo[i];

        question1(msgIn);    //Confirmed: 06- 1757   061- 1 - 5: 7, 5, 6, 10, 11
        question2(msgIn);    //Confirmed: 06- 2950   061- 1 - 5: 19, 23, 23, 29, 26
    }

    /**
     * Question 1: Find the packet start, first 4 consecutive unique chars.
     */
    private static void question1(String[] msgIn) {
        System.out.println("Part 1:");
        for(int i = 0; i < msgIn.length; i++){
            int msgStart = findMsgStart(msgIn[i], 4);
            //Track ,  Confirmed: 06- 1757   061- 1 - 5 is 7, 5, 6, 10, 11
            System.out.println("Msg " + (i + 1) + " start is " + msgStart);
        }
    }

    /**
     * Question 2: Find the packet start, first 14 consecutive unique chars.
     */
    private static void question2(String[] msgIn) {
        System.out.println("Part 2:");
        for(int i = 0; i < msgIn.length; i++){      //This is for 061, 5 strings
            int msgStart = findMsgStart(msgIn[i], 14);
            //Track ,  Confirmed: 06- 2950   061- 1 - 5: 19, 23, 23, 29, 26
            System.out.println("Msg " + (i + 1) + " start is " + msgStart);
        }
    }

    /**
     * Find the packet/msg start, first char length of consecutive unique chars.
     * @param msg Message string to be evaluated.
     * @param chLen Unique length of chars needed
     * @return The end position of the unique segment.
     */
    private static int findMsgStart(String msg, int chLen){
        boolean fnd = false;
        for(int i = 0; i < msg.length() - chLen; i++){
            String m = msg.substring(i, i + chLen);
            fnd = false;
            for(int j = 0; j < (chLen - 1); j++){
                for(int k = j + 1; k < chLen; k++){
                    if(m.charAt(j) == m.charAt(k)) fnd = true;
                }
                if(fnd) break;
            }
            if(!fnd) return i + chLen;
        }
        return -1;
    }
}