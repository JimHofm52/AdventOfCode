package days21;

import java.io.IOException;

import util.ReadInput;

public class Day02 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02";
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 2
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 02-1383564      021-150
        question2();    //Confirmed: 02-1488311643   021-900
    }

    /**
     * Question 1: Find distance traveled vert * horz.
     * <p> - forward X increases the horizontal position by X units.
     * <p> = down X increases the depth by X units.
     * <p> = up X decreases the depth by X units.
     */
    private static void question1() {
        int horzDist = 0;
        int vertDist = 0;
        for(String s : fileInfo){
            switch(s.charAt(0)){
                case 'f':
                horzDist += Integer.parseInt(s.substring(s.indexOf(" ") + 1));
                break;
                case 'u':
                vertDist -= Integer.parseInt(s.substring(s.indexOf(" ") + 1));
                if(vertDist < 0) System.out.println("Out of da water:" + s);
                break;
                case 'd':
                vertDist += Integer.parseInt(s.substring(s.indexOf(" ") + 1));
                break;
                default:
                System.out.println("Unkonw direction: " + s);
                break;
            }
        }

        //Track ,  Confirmed: 02-1383564   021-150
        System.out.println("\nPart 1: Distance: " + vertDist * horzDist);
    }
    
    /**
     * Question 2: Find distance traveled vert * horz.  New rules
     * <p> - down X increases your aim by X units.
     * <p> - up X decreases your aim by X units.
     * <p> - forward X does two things:
     * <p> - - It increases your horizontal position by X units.
     * <p> - =  It increases your depth by your aim multiplied by X.
     */
    private static void question2() {
        int aim = 0;
        int horzDist = 0;
        int vertDist = 0;
        int tmp = 0;
        for(String s : fileInfo){
            switch(s.charAt(0)){
                case 'f':
                tmp = Integer.parseInt(s.substring(s.indexOf(" ") + 1));
                horzDist += tmp;
                vertDist += tmp * aim;
                break;
                case 'u':
                aim -= Integer.parseInt(s.substring(s.indexOf(" ") + 1));
                if(vertDist < 0) System.out.println("Out of da water:" + s);
                break;
                case 'd':
                aim += Integer.parseInt(s.substring(s.indexOf(" ") + 1));
                break;
                default:
                System.out.println("Unkonw direction: " + s);
                break;
            }
        }

        //Track ,  Confirmed: 02-1488311643   021-900
        System.out.println("\nPart 2: Distance: " + vertDist * horzDist);
    }
}