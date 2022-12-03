package days22;

import java.io.IOException;
import util.ReadInput;

/**
 * Simplier version of Day01.  
 * <p>Replaced T_Calorie type with a 1-D int[] array for total calories.
 */
public class Day01A {
    private static int fileInfo[];
    private static int len;
    private static int[] elfStuff;
    /** Constructor, not needed but used for standards. */
    private Day01A(){}

    public static void update() throws IOException {
        String fNum = "01";
        // String fNum = "011";    //Test input - rtn 24000 calories by 4th elf
        fileInfo = ReadInput.getInputInt(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        elfStuff = parceInput(fileInfo);
        int a = 0;

        question1(elfStuff);
        question2(elfStuff);
    }

    /**
     * Question 1: Find the most calories being carried by 1 elf.
     */
    private static void question1(int[] elfSthuff) {
        int elf = 0;
        int fatElf = 0;
        for(int i = 0; i < elfSthuff.length; i++){
            if(elfSthuff[i] > fatElf){
                elf = i;
                fatElf = elfSthuff[i];
            }
        }
        //Track ,  Confirmed: 01-69310   011-24000
        System.out.println("\nPart 1: Elf #" + (elf + 1) + " has " + fatElf + " calories.");
    }
    
    /**
     * Question 2: Find the top 3 calorie counts being carried.
     */
    private static void question2(int[] elfSthuff) {
        int[] elf = new int[3];
        int[] cal = new int[3];
        int len = elfSthuff.length;

        for(int j = 0; j < 3; j++){
            for(int i = 0; i < len; i++){
                if(elfSthuff[i] > cal[j] && i != elf[0] && i != elf[1] && i != elf[2]){
                    elf[j] = i;
                    cal[j] = elfSthuff[i];
                }
            }
        }

        //Track ,  Confirmed: 01-206104, Elfs 178, 143 & 113   011-44000, Elfs 3, 2 & 5
        System.out.println("\nPart 2:  Top 3 Elfs");
        for(int i = 0; i < 3; i++){
            System.out.println("\tElf #\t" + (elf[i] + 1) + " has " + cal[i] + " calories.");
        }
        System.out.println("Total calories for the 3: " + (cal[0]+cal[1]+cal[2]));
    }

    /**
     * Create records for Elfs calories for items being carried by each.
     * Each record holds an array of total calories for this elf.
     * @param inFile
     * @return int[] of total calories for each elf.
     */
    private static int[] parceInput(int[] inFile){
        int len = 1;
        for(int i = 0; i < inFile.length; i++) if(inFile[i] == -1) len++;
        int[] totCal = new int[len];

        int tmpCal = 0;
        int totIdx = 0;
        for(int i = 0; i < inFile.length; i++){
            tmpCal = inFile[i];
            if(tmpCal > 0){
                totCal[totIdx] += tmpCal;
            }else{
                totIdx++;
            }
        }
       return totCal;
    }
}