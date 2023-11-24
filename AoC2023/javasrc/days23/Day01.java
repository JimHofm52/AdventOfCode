package days23;

import java.io.IOException;
import java.util.Arrays;

import type.T_Calorie;
import util.ReadInput;

public class Day01 {
    private static int fileInfo[];
    private static int len;
    private static T_Calorie[] elfStuff;
    /** Constructor, not needed but used for standards. */
    private Day01(){}

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
    private static void question1(T_Calorie[] elfSthuff) {
        int elf = 0;
        int fatElf = 0;
        for(int i = 0; i < elfSthuff.length; i++){
            if(elfSthuff[i].total > fatElf){
                elf = i;
                fatElf = elfSthuff[i].total;
            }
        }
        //Track ,  Confirmed: 01-69310   011-2400
        System.out.println("\nPart 1: Elf #" + (elf + 1) + " has " + fatElf + " calories.");
    }
    
    /**
     * Question 2: Find the top 3 calorie counts being carried.
     */
    private static void question2(T_Calorie[] elfSthuff) {
        int[] elf = new int[3];
        int[] cal = new int[3];
        int len = elfSthuff.length;

        for(int j = 0; j < 3; j++){
            for(int i = 0; i < len; i++){
                if(elfSthuff[i].total > cal[j] && i != elf[0] && i != elf[1] && i != elf[2]){
                    elf[j] = i;
                    cal[j] = elfSthuff[i].total;
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
     * Each record holds an array of items and the total calories for this elf.
     * @param inFile
     * @return
     */
    private static T_Calorie[] parceInput(int[] inFile){
        T_Calorie[] tmpElf = new T_Calorie[1];  //Create the first record
        tmpElf[0] = new T_Calorie();            //and initialize it.

        int rcd = 0;
        int tmpInt;
        for(int i = 0; i < inFile.length; i++){
            tmpInt = inFile[i];
            if(tmpInt < 0){             //If read in is -1, indicates a new record, next elf.
                tmpElf = Arrays.copyOf(tmpElf, ++rcd + 1);  //Add a record
                tmpElf[rcd] = new T_Calorie();              //and initialize it.
            }else{
                tmpElf[rcd].addItem(tmpInt);    //Add the item to item array and add to total.
            }
        }
       return tmpElf;
    }
}