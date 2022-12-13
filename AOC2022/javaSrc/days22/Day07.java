package days22;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.DelayQueue;
import java.util.jar.Attributes.Name;

import type.T_Disk;
import type.T_Folder;
import util.ReadInput;

public class Day07 {
    private static String fileInfo[];
    private static int len;
    private static T_Disk myDisk;

    /** Constructor, not needed but used for standards. */
    private Day07(){}

    public static void update() throws IOException {
        String fNum = "07"; //Part1- 1232307   Part2- 7268994
        // String fNum = "071";//Part1- 95437   Part2- 24933642
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        myDisk = new T_Disk();
        parceInput(fileInfo);

        question1();    //Confirmed: 07- 1232307   071- 95437
        question2();    //Confirmed: 07- 7268994   071- 24933642
    }

    /**
     * Question 1: Find the sum of directories with at most 100000.
     */
    private static void question1() {
        int[] fdrIdx = new int[0];
        int tmpSz = 0;
        long sumFdr = 0;
        int cnt = 1;
        System.out.println();
        for(int i = 0; i < myDisk.fldrLst.length; i++){
            if((tmpSz = myDisk.fldrLst[i].size) <= 100000){
                fdrIdx = Arrays.copyOf(fdrIdx, fdrIdx.length + 1);
                fdrIdx[fdrIdx.length - 1] = i;
                sumFdr += tmpSz;
                // System.out.println("Folder " + cnt++ + ", index " + i + " size of " + myDisk.fldrLst[i].name + " is " + tmpSz);
            }
        }
        //Track ,  Confirmed: 07- 1232307   071- 95437
        System.out.println("\nPart 1: Sum of folders: " + sumFdr);
    }
    
    /**
     * Question 2: Find the smallest folder to delete to free up 30M.
     * 70M (disk size) - root size = free size.  30M - free size = min req del
     * OR -- 70M - 30 = 40M allowed used.  SO, root used - del folder > 40M
     */
    private static void question2() {
        int usedSpc = myDisk.fldrLst[0].size;
        int delSpc = usedSpc;
        int delFdrIdx = 0;
        int tstSpc;

        for(int idx = 0; idx < myDisk.fldrLst.length; idx++){
            tstSpc = myDisk.fldrLst[idx].size;
            if( usedSpc - tstSpc <= 40000000 && tstSpc < delSpc ){
                delSpc = tstSpc;
                delFdrIdx = idx;
            }
        }

        //Track 17348139 hi,  Confirmed: 07- 7268994   071- 24933642
        System.out.println("\nPart 2: Smallest folder to delete is " + myDisk.fldrLst[delFdrIdx].name + 
                            ", index " + delFdrIdx + ", size is " + delSpc);
    }

    private static void parceInput(String[] fileIn){
        int cnt = 0;
        for(String inp : fileIn){
            // System.out.println("Input " + (++cnt) + " is " + inp );
            String chk = inp.substring(0, 4);
            switch(inp.substring(0, 4)){
                case "$ cd":    //Chg directory active
                cmd_CD(inp);
                break;
                case "$ ls":    //List contents - can be ignored at ths time
                cmd_LS();
                break;
                case "dir ":    //add dir with path & name id
                chk = inp.substring(4);
                myDisk.add_Folder(inp.substring(4));
                break;
                default:        //add file with path & name id
                int sIdx = inp.indexOf(" ");
                myDisk.add_File(Integer.parseInt(inp.substring(0, sIdx)), inp.substring(sIdx + 1));
                break;
            }
        }
        myDisk.fnlSumItems();
    }

    private static void cmd_CD(String inCmd){
        switch(inCmd.charAt(5)){
            case '/':
            myDisk.chgDir("/");
            break;
            case '.':
            myDisk.chgDir("..");
            break;
            default:
            myDisk.chgDir(inCmd.substring(5));
            break;
        }
    }

    private static void cmd_LS(){
    }

    private static void add_Dir(){
    }

    private static void add_File(){
    }


    }