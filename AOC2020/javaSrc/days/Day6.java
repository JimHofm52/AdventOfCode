package days;

import java.io.IOException;
import util.ReadInput;

public class Day6 {

    public static void update() throws IOException {
        String fileInfo[];
        fileInfo = ReadInput.GetInputStr(6);   //Get input in an array for 6
        int len = fileInfo.length;          //Length of input array

        int grpPplCnt[][] = new int[487][26];   //Yeses per grp(487) per answer(26)
        int grpPplNum[] = new int[487];         //people in each group
        int total = 0;
        int totYes = 0;

        CntGrps( fileInfo, grpPplCnt, grpPplNum);
        for (int i=0; i < grpPplCnt.length; i++) {
            total += GrpTot(grpPplCnt, i);                  //part 1
            totYes += CntAllYes(grpPplCnt, grpPplNum, i);   //part 2
        }

        System.out.println("\nTotal - \t" + total);     //Confirmed answer = 6532 part 1
        System.out.println("Total Yes - \t" + totYes);  //Confirmed answer = 3427 part 2
    }


    /**
     * Get counts for Yes answers for each question, a - z per group.
     * And number of people in group.
     * 
     * @param inData
     * @param grpCnt
     * @param pplCnt
     */
    private static void CntGrps( String inData[], int grpCnt[][], int pplCnt[] ){
        int rcdNdx = 0;
        
        for (String strIn : inData) {
            if(strIn.length() > 0 && strIn != null){
                for( int i = 0; i < strIn.length(); i++){
                    grpCnt[rcdNdx][strIn.charAt(i) - 'a']++;
                }
                pplCnt[rcdNdx]++;
            }else{
                rcdNdx++;
            }
        }
    }

    /************** Part 1 *********************
     * Count the number yes answers in the group.
     * 
     * @param grpCnt
     * @param grp
     * @return
     */
    private static int GrpTot( int grpCnt[][], int grp ){
        int totYes = 0;

        for(int i = 0; i < 26; i++){
            if(grpCnt[grp][i] > 0 ) totYes++;
        }
        return totYes;
    }

    /************************ Part 2 ********************
     * Count the number of people that ALL answered as yes to a question in the group.
     * 
     * @param grpCnt
     * @param grpNumPpl
     * @param grp
     * @return
     */
    private static int CntAllYes( int grpCnt[][], int grpNumPpl[], int grp ){
        int totYes = 0;

        for(int i = 0; i < 26; i++){
            if(grpCnt[grp][i] == grpNumPpl[grp] ) totYes++;
        }
        return totYes;
    }
}