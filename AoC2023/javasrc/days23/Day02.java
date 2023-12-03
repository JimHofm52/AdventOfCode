package days23;

import java.io.IOException;

import util.ReadInput;
import type.RBGGame;

public class Day02 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02";    //Part1- 2528   Part2- 67363
        // String fNum = "021";   //Part1- 8   Part2- 2286
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        RBGGame[] rbg = new RBGGame[len];
        for(int i = 0; i < len; i++) rbg[i] = new RBGGame();    //Initalize
        parceInput(fileInfo, rbg);

        question1(rbg);    //Confirmed: 021- 8   02- 2528
        question2(rbg);    //Confirmed: 021- 2286   02- 67363
    }

    /**
     * Question 1: Which games are possible if loaded
     * with just only 12 red cubes, 13 green cubes, and 14 blue cubes.
     * <p>Sum of the games.
     */
    private static void question1(RBGGame[] rbg) {
        boolean[] goodGame = new boolean[len];
        int gameCnt = 0;
        for(int i = 0; i < len; i++){
            goodGame[i] =  rbg[i].redMax <= 12
                        && rbg[i].bluMax <= 14
                        && rbg[i].grnMax <= 13;
            gameCnt += goodGame[i] ? i + 1 : 0;
        }
        int a = 0;
        //Track ,  Confirmed: 021- 8   02- 2528
        System.out.println("\nPart 1: Games playable with this these sets: " + gameCnt);
    }
    
    /**
     * Question 2: Calc Power of Cubes: number of red, blue & green cubes to
     * play the game multiplied together.
     * <p>Then sum each game's POC value.
     */
    private static void question2(RBGGame[] rbg) {
        int sumPoc = 0;
        for(int i = 0; i < len; i++){
            rbg[i].poc =  rbg[i].redMax * rbg[i].bluMax * rbg[i].grnMax;
            sumPoc += rbg[i].poc;
        }
        int a = 0;
        //Track ,  Confirmed: 021- 2286   02- 67363
        System.out.println("\nPart 2: Sum of Power of Cubes: " + sumPoc);
    }

    /**
     * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
     *    rcd:       rbgMbr1;                rbgMbr2;  rbgMbr3
     * @param inGame - game input
     * @param rbg - type to store min, max & poc for each game (line).
     */
    private static void parceInput(String[] inGame, RBGGame[] rbg){
        String str;
        int[] mnmx = {100000, 0};
        String[] pulls = new String[3];
        int begIdx = 0;
        for(int i = 0; i < inGame.length; i++){
            str = inGame[i];
            begIdx = str.indexOf(':');
            str = str.substring(begIdx + 1).trim();
            findMinMax(str, "red", mnmx);
            rbg[i].redMin = mnmx[0];
            rbg[i].redMax = mnmx[1];
            findMinMax(str, "blue", mnmx);
            rbg[i].bluMin = mnmx[0];
            rbg[i].bluMax = mnmx[1];
            findMinMax(str, "green", mnmx);
            rbg[i].grnMin = mnmx[0];
            rbg[i].grnMax = mnmx[1];
        }
        int a = 0; 
    }

    /**
     * Find the min & max of the color passed in the string passed.
     * @param str string to search
     * @param srch seach alue: red, blue or green
     * @param mnmx array to return the min[0] & max[1]
     */
    private static void findMinMax(String str, String srch, int[] mnmx){

        int min = 100000;
        int max = 0;
        String tStr;
        int num;
        int endIdx = str.indexOf(srch, 0);

        while(endIdx > -1){
            num = Math.max(0, endIdx - 3);
            num = Integer.parseInt(str.substring(num, endIdx).trim());
            min = Math.min(min, num);
            max = Math.max(max, num);
            endIdx = str.indexOf(srch, endIdx + 1);
        }
        mnmx[0] = min;
        mnmx[1] = max;
    }
}