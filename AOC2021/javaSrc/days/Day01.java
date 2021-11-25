package days;

import java.io.IOException;
import util.ReadInput;

public class Day5 {

    public static void update() throws IOException {
        String fileInfo[];
        fileInfo = ReadInput.GetInputStr(5);   //Get input in an array for 5
        int len = fileInfo.length;          //Length of input array

        int seatRow = 0;
        int seatCol = 0;
        int seatNum = 0;
        int seatMax = 0;
        int mySeat = -1;
        int occInfo[][] = new int[2][128]; //[0]=Num, [1]=Row, [2]=RwD

        for (String string : fileInfo) {
            if(string == null) break;
            seatRow = CalcSeatRow(string);
            seatCol = CalcSeatCol(string);
            seatNum = seatRow * 8 + seatCol;
            if(seatNum > seatMax) seatMax = seatNum;

            occInfo[0][seatRow] += 1 << seatCol;
            occInfo[1][seatRow] += 10 ^ seatCol;
        }
        mySeat = FindSeat(occInfo);
        DisplayInfo(occInfo, seatMax, mySeat);
    }

    /**************************************************************************
     * Print the Answsers
     * 
     * @param sInfo
     * @param maxSeat
     * @param mySeat
     */
    private static void DisplayInfo(int[][] sInfo, int maxSeat, int mySeat){

        for( int i = 0; i < sInfo[0].length; i++){
            System.out.print(i);
            System.out.print("\t" + sInfo[0][i]);
            System.out.println("\t" + sInfo[1][i]);
        }
        System.out.println("\n");
        System.out.println("\nMx Seat - " + maxSeat);   //confirmed 892
        System.out.println("\nMy Seat - " + mySeat);    //confirmed 625
    }

    /**
     * Coverts the FFBBFBF(RLR) to a Row number
     * 
     * @param code
     * @return
     */
    private static int CalcSeatRow(String code){
        int num = 0;

        for( int i = 0; i < 7; i++){
            if( code.charAt(i) == 'B') num += 1 << (6 - i);
        }
        return num;
    }

    /**
     * Coverts the (FFBBFBF)RLR to a Seat number
     * @param code
     * @return
     */
    private static int CalcSeatCol(String code){
        int num = 0;

        for( int i = 7; i < 10; i++){
            if( code.charAt(i) == 'R') num += 1 << (9 - i);
        }
        return num;
    }

    /**
     * Find my seat.  Assumes most seats are occupied.  Only 1 seat open in a row.
     * @param seatNum
     * @return
     */
    private static int FindSeat(int[][] seatNum){
        int x = 0;
        int rwMatch = -1;

        int rw = 0;
        for( int sNum : seatNum[0]){
            if(sNum != 0 && sNum != 255) {
                x = 255 - sNum;
                for(int seat = 1; seat < 7; seat++){
                    if(x == (1 << seat)) rwMatch = 1 << (seat - 1);
                }
            }
            if(rwMatch > -1 ) break;
            rw++;
        }
        return rw * 8 + rwMatch;
    }
}