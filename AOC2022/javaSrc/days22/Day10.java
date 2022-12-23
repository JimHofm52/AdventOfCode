package days22;

import java.io.IOException;

import util.ReadInput;

public class Day10 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day10(){}

    public static void update() throws IOException {
        String fNum = "10"; //Part1- 13760   Part2- RFKZCPEF
        // String fNum = "101";//Part1- 13140   Part2- ##.. ###... ####.... ######.....
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 10- 13760   101- 13140
        question2();    //Confirmed: 10- RFKZCPEF   101- ???
    }

    /**
     * Question 1: Find the signal strength during the 20th, 60th, 100th, 
     * 140th, 180th, and 220th cycles. What is the sum of these six signal strengths?
     */
    private static void question1() {
        int cycleCnt = 1;
        int regX = 1;
        int sigVal = 0;

        String instr = "";
        for(int i = 0; i < len; i++){
            instr = fileInfo[i].substring(0, 4);
            switch(instr){
                case "noop":
                sigVal += chkCycle1(cycleCnt, regX, sigVal);
                cycleCnt++;
                break;
                case "addx":
                sigVal += chkCycle1(cycleCnt, regX, sigVal);
                cycleCnt++;

                // if(cycleCnt == 219){
                //     int f = 0;
                // }
                sigVal += chkCycle1(cycleCnt, regX, sigVal);
                cycleCnt++;
                regX += Integer.parseInt(fileInfo[i].substring(5));
                break;
                default:
                System.out.println("Bad instruction: " + instr);
            }
        }
        //Track 14800 hi, 9960 lo,  Confirmed: 10- 13760   101- 13140
        System.out.println("\nPart 1: Sum of signal values: " + sigVal);
    }
    
    /**
     * Question 2: Render the image given by your program. 
     * What eight capital letters appear on your CRT?
     * CRT is 40 x 6 pixels.
     */
    private static void question2() {
        int cycleCnt = 0;
        int regX = 1;
        int sigVal = 0;
        String[] crt = new String[6];
        for(int i = 0; i < 6; i++) crt[i] = "........................................";
        // for(String p : crt) System.out.println(p);
        String instr = "";
        for(int i = 0; i < len; i++){
            instr = fileInfo[i].substring(0, 4);
            switch(instr){
                case "noop":
                chkCycle2(cycleCnt, regX, crt);
                cycleCnt++;
                break;
                case "addx":
                chkCycle2(cycleCnt, regX, crt);
                cycleCnt++;
                
                chkCycle2(cycleCnt, regX, crt);
                cycleCnt++;
                regX += Integer.parseInt(fileInfo[i].substring(5));
                break;
                default:
                System.out.println("Bad instruction: " + instr);
            }
        }
        //Track ,  Confirmed: 10- RFKZCPEF   101- Row 1 ##.. repeat, 2 ###... repeat, ... 6 ######...... repeat
        for(String p : crt) System.out.println(p);

    }

    /**Only calc signal strength for these cycles. */
    private static int chkCycle1(int cycCnt, int regVal, int sigTot){
        switch(cycCnt){
            case 20:
            case 60:
            case 100:
            case 140:
            case 180:
            case 220:
            prntCycTS(cycCnt, regVal, sigTot);
            return cycCnt * regVal;
            default: return 0;
        }
    }


    /**Update pixel if regX is within 2 of pixel location, cycle % 40. */
    private static int chkCycle2(int cycCnt, int regVal, String[] crt){
        int pxlRow = (cycCnt) / 40;
        int pxlCol = (cycCnt) % 40;
        int spriteCol = regVal % 40;
        int diff = Math.abs(spriteCol - pxlCol);
        if(pxlCol == 38){
            int x = 0;
        }
        // pxl = 4 => 3, 4, 5 :: 3-4=-1, 5-4=1
        if(diff < 2){
            crt[pxlRow] = crt[pxlRow].substring(0, pxlCol) + "#" + crt[pxlRow].substring(pxlCol + 1);
            int a = 1;
        }

        return cycCnt * regVal;
    }

    /**Troubleshooting print */
    private static void prntCycTS(int cycCnt, int regVal, int sigTot){
        int tmp = cycCnt * regVal;
        System.out.println("Cycle: " + (cycCnt) + "\tReg: " + regVal + 
        "\tSig Strn: " + tmp + "\tSig Tot: " + (tmp + sigTot));

    }
}