package days;

import java.io.IOException;
import util.ReadInput;

public class Day5 {

    public static void update() throws IOException {
        String fileInfo[];
        fileInfo = ReadInput.getInputStr(5);   //Get input in an array for 5
        int len = fileInfo.length;          //Length of input array

        int seatRow = 0;
        int seatCol = 0;
        int seatNum = 0;
        int seatMax = 0;
        int mySeat = -1;                    //Num [0] is int 0-127, [1] is 1111111 display.
        int occInfo[][] = new int[2][128];  //[0]=Num, [1]=Row, [2]=RwD

        for (String string : fileInfo) {    //Find all occupied seats
            if(string == null) break;
            seatRow = calcSeatRow(string);
            seatCol = calcSeatCol(string);
            seatNum = seatRow * 8 + seatCol;
            if(seatNum > seatMax) seatMax = seatNum;//Find max seat num occ, Question 1 = 892.

            occInfo[0][seatRow] += 1 << seatCol;    //Seats occ in a row as a int, 0 - 7.
            occInfo[1][seatRow] += 10 ^ seatCol;    //Seats occ in a row as a digit, 1111111.
        }
        mySeat = findSeat(occInfo);                 //Question 2 = 625 / rw=78 * 8 + col=1
        displayInfo(occInfo, seatMax, mySeat);
    }

    /**************************************************************************
     * Print the Answsers
     * 
     * @param sInfo
     * @param maxSeat
     * @param mySeat
     */
    private static void displayInfo(int[][] sInfo, int maxSeat, int mySeat){

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
     * <p> For example, consider just the first seven characters of FBFBBFF(RLR):
     * <p> Start by considering the whole range, rows 0 through 127.
     * <p> F means to take the lower half, keeping rows 0 through 63.  / 0
     * <p> B means to take the upper half, keeping rows 32 through 63. / 2^5 = 32
     * <p> F means to take the lower half, keeping rows 32 through 47. / 0
     * <p> B means to take the upper half, keeping rows 40 through 47. / 2^3 =  8
     * <p> B keeps rows 44 through 47.                                 / 2^2 =  4
     * <p> F keeps rows 44 through 45.                                 / 0
     * <p> The final F keeps the lower of the two, row 44.             / 0    ====
     * <p>                                                                     44
     * 
     * @param code
     * @return
     */
    private static int calcSeatRow(String code){
        int num = 0;

        for( int i = 0; i < 7; i++){
            if( code.charAt(i) == 'B') num += 1 << (6 - i);
        }
        return num;
    }

    /**
     * Coverts the (FFBBFBF)RLR to a Seat number
     * <p> Consider just the last 3 characters of (FBFBBFF)RLR:
     * <p> Start by considering the whole range, columns 0 through 7.
     * <p> R means to take the upper half, keeping columns 4 through 7. / 2^2 = 4
     * <p> L means to take the lower half, keeping columns 4 through 5. / 0
     * <p> The final R keeps the upper of the two, column 5.            / 2^0 = 1
     * <p>                                                                     ===
     * <p>                                                                      5
     * @param code
     * @return
     */
    private static int calcSeatCol(String code){
        int num = 0;

        for( int i = 7; i < 10; i++){
            if( code.charAt(i) == 'R') num += 1 << (9 - i);
        }
        return num;
    }

    /**
     * Find my seat.  Assumes most seats are occupied.  Only 1 seat open in a row.
     * <p> It's a completely full flight, so your seat should be the only missing 
     * boarding pass in your list. However, there's a catch: some of the seats at 
     * the very front and back of the plane don't exist on this aircraft, so they'll 
     * be missing from your list as well.
     * Your seat wasn't at the very front or back, though; the seats 
     * with IDs +1 and -1 from yours will be in your list.
     * <p>
     * @param seatNum
     * @return
     */
    private static int findSeat(int[][] seatNum){
        int x = 0;
        int rwMatch = -1;   //Seat in matched row

        int rw = 0;
        for( int sNum : seatNum[0]){    //Use row numbers, 0 - 127
            if(sNum != 0 && sNum != 255) {      //Find rows not full or empty
                x = 255 - sNum;                 //get complement (invert)
                for(int seat = 1; seat < 7; seat++){    //Find empty seat.
                    if(x == (1 << seat)) rwMatch = 1 << (seat - 1);
                }
            }
            if(rwMatch > -1 ) break;    //Seat found
            rw++;
        }
        return rw * 8 + rwMatch;    //Seat number, rw * 8 + col
    }
}