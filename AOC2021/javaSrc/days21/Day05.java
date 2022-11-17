package days21;

import java.io.IOException;

import util.ReadInput;

public class Day05 {
    private static String fileInfo[];
    private static int len;
    private static int[][] sttPt;       //[idx][0]='X' Row/Horz, [idx][1]='Y' Col/Vert
    private static int[][] endPt;
    private static short[][] lineRow;   //Matrix [0][0] Upper left to [max row][max col] Lower right

    /** Constructor, not needed but used for standards. */
    private Day05(){}

    public static void update() throws IOException {
        String fNum = "05";
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 05-6572   051-4
        question2();    //Confirmed: 05-21466  051-12
    }

    /**
     * Question 1: Find the number of points where at least two lines overlap:
     * consider only horz or vert line X1 = X2 or Y1 = Y2.
     */
    private static void question1() {
        parcePts();
        applySqdLines();
        int danger = countDanger();
        int a = 0;
        //Track ,  Confirmed: 05-6572   051-5
        System.out.println("\nPart 1: Danger zones: " + danger);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        applyDiagLines();
        int danger = countDanger();
        int a = 0;
        //Track ,  Confirmed: 05-21466   051-12
        System.out.println("\nPart 2: Danger zones: " + danger);
    }

    /**
     * Mark matrix for only squared lines
     */
    private static void applySqdLines(){
        for(int i = 0; i < len; i++){
            if(sttPt[i][1] == endPt[i][1] || sttPt[i][0] == endPt[i][0]){
                applySqd(i);    //If one pair is .EQ. apply to matrix
            }
        }
    }

    /**
     * Mark for squared lines
     * @param i index
     */
    private static void applySqd(int i){
        if(sttPt[i][1] == endPt[i][1]){     //If Col's equal, incr thru Rows
            if(sttPt[i][0] < endPt[i][0]){  //If row1 .LT. row2
                for(int x = sttPt[i][0]; x < endPt[i][0] + 1; x++){
                    lineRow[x][sttPt[i][1]]++;  //For row incr values
                }
            }else{                          //else do row2 to row1
                for(int x = endPt[i][0]; x < sttPt[i][0] + 1; x++){
                    lineRow[x][endPt[i][1]]++;  //For row incr values
                }
            }
        }
        
        if(sttPt[i][0] == endPt[i][0]){     //If Row's equal, incr thru Cols
            if(sttPt[i][1] < endPt[i][1]){  //If col1 .LT. col2
                for(int y = sttPt[i][1]; y < endPt[i][1] + 1; y++){
                    lineRow[sttPt[i][0]][y]++;  //For col incr values
                }
            }else{                          //else do Y2 to Y1
                for(int y = endPt[i][1]; y < sttPt[i][1] + 1; y++){
                    lineRow[endPt[i][0]][y]++;  //For col incr values
                }
            }
        }
    }

    /**
     * Mark matrix for diagonal lines.
     */
    private static void applyDiagLines(){
        for(int i = 0; i < len; i++){
            if(sttPt[i][1] != endPt[i][1] && sttPt[i][0] != endPt[i][0]){
                applyDiag(i);
            }
        }
    }

    /**
     * Mark for diagonal lines
     * @param i index
     */
    private static void applyDiag(int i){
        int tst = 0;    //Normal left to right & down
        int diff = Math.abs(sttPt[i][0] - endPt[i][0]); //Since all 45 deg, can be X or Y
        if(sttPt[i][0] > endPt[i][0]) tst += 1; //Is right to left?
        if(sttPt[i][1] > endPt[i][1]) tst += 2; //Is going up
        for(int xy = 0; xy < diff + 1; xy++){
            switch(tst){
                case 0:     //x1 < x2 & y1 < y2, left to right & down (Normal)
                lineRow[sttPt[i][0] + xy][sttPt[i][1] + xy]++;  //For col incr values
                break;
                case 1:     //x1 > x2 & y1 < y2, right to left & down
                lineRow[sttPt[i][0] - xy][sttPt[i][1] + xy]++;  //For col incr values
                break;
                case 2:     //x1 < x2 & y1 > y2, left to right & up
                lineRow[sttPt[i][0] + xy][sttPt[i][1] - xy]++;  //For col incr values
                break;
                case 3:     //x1 > x2 & y1 > y2right to left & up
                lineRow[sttPt[i][0] - xy][sttPt[i][1] - xy]++;  //For col incr values
                break;
            }
        }
    }

    /**
     * @return Count values 2 and up.
     */
    private static int countDanger(){
        int cnt = 0;
        for(int y = 0; y < lineRow.length; y++){
            for(int x = 0; x < lineRow[0].length; x++){
                if(lineRow[y][x] > 1) cnt++;
            }
        }
        return cnt;
    }

    /**
     * Parce the file input to an arrays of start & end points.
     * <p>Note: input pairs are column, row.  We use row, column.
     * <p>
     * <p>Also define storage for the tracking matrix [0][0] to [maxRow][maxCol]
     */
    private static void parcePts(){
        sttPt = new int[len][2];    //[idx][0]=row, [idx][1]=col
        endPt = new int[len][2];
        int end;
        int pIdx = 0;
        for(String str : fileInfo){ //Note: in is col, row
            end = str.indexOf(",");
            sttPt[pIdx][1] = Integer.parseInt(str.substring(0, end).trim());
            str = str.substring(end + 1).trim();
            end = str.indexOf(" ");
            sttPt[pIdx][0] = Integer.parseInt(str.substring(0, end).trim());
            str = str.substring(end + 3).trim();

            end = str.indexOf(",");
            endPt[pIdx][1] = Integer.parseInt(str.substring(0, end).trim());
            str = str.substring(end + 1).trim();
            endPt[pIdx][0] = Integer.parseInt(str.trim());
            pIdx++;
        }

        int minColVal = 9999; int maxColVal = 0;
        int minRowVal = 9999; int maxRowVal = 0;
        for(int i = 0; i < len; i++){
            minRowVal = getMin(minRowVal, sttPt[i][0], endPt[i][0]);
            maxRowVal = getMax(maxRowVal, sttPt[i][0], endPt[i][0]);
            minColVal = getMin(minColVal, sttPt[i][1], endPt[i][1]);
            maxColVal = getMax(maxColVal, sttPt[i][1], endPt[i][1]);
        }
        lineRow = new short[maxRowVal + 1][maxColVal + 1];
    }

    /**
     * @param a
     * @param b
     * @param c
     * @return The minimum of 3 int's.
     */
    private static int getMin(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * @param a
     * @param b
     * @param c
     * @return The maximum of 3 int's.
     */
    private static int getMax(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
}