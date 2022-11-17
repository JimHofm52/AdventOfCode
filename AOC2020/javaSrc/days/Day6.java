package javasrc.days;

import java.io.IOException;
import javasrc.util.ReadInput;

public class Day6 {

    /** Constructor, no action.  Standard only. */
    private Day6(){}

    public static void update() throws IOException {
        String fileInfo[];
        fileInfo = ReadInput.getInputStr(6);   //Get input in an array for 6
        int len = fileInfo.length;          //Length of input array

        int grpPplCnt[][] = new int[487][26];   //Yeses per grp(487) per answer(26)
        int grpPplNum[] = new int[487];         //people in each group
        int total = 0;
        int totYes = 0;

        CntGrps( fileInfo, grpPplCnt, grpPplNum);   //Sort input data(fileInfo) 
        for (int i=0; i < grpPplCnt.length; i++) {
            total += GrpTot(grpPplCnt, i);                  //part 1 - 6532
            totYes += CntAllYes(grpPplCnt, grpPplNum, i);   //part 2 - 3427
        }

        System.out.println("\nTotal - \t" + total);     //Confirmed answer = 6532 part 1
        System.out.println("Total Yes - \t" + totYes);  //Confirmed answer = 3427 part 2
    }


    /**
     * Get counts for Yes answers for each question, a - z per group.
     * And number of people in group.
     * <p> If a letter exists they answer yes for that question (a - z).
     * 
     * @param inData Ref to input data
     * @param grpCnt Ref to array to questions answer yes
     * @param pplCnt Ref to array to people answering yes
     */
    private static void CntGrps( String[] inData, int[][] grpCnt, int[] pplCnt ){
        int rcdNdx = 0;
        
        for (String strIn : inData) {
            if(!strIn.isEmpty()){
                for(int i = 0; i < strIn.length(); i++){
                    grpCnt[rcdNdx][strIn.charAt(i) - 'a']++;    //incr for ques. in rcd.
                }
                pplCnt[rcdNdx]++;   //incr ppl answering yes in this grp.
            }else{
                rcdNdx++;   //incr grp
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