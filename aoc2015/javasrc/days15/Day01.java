package days15;

import java.io.IOException;

import util.ReadWriteFiles;

public class Day01{
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day01(){}

    public static void update() throws IOException {
        String fNum = "01";    //Part1- 280   Part2- 1797
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 01- 280   011- n/a
        question2();    //Confirmed: 01- 1797   011- n/a
    }

    /**
     * Question 1: An opening parenthesis, (, means he should go up one floor, 
     * and a closing parenthesis, ), means he should go down one floor.
     * 
     * Starting at Floor 0, To what floor do the instructions take Santa?
     */
    private static void question1() {
        int lastFloor = 0;
        for(int upDn = 0; upDn < fileInfo[0].length(); upDn++){
            lastFloor += fileInfo[0].charAt(upDn) == '(' ? 1 : -1;
        }
        //Track ,  Confirmed: 01- 280   011- n/a
        System.out.println("\nPart 1: Last floor Santa entered is " + lastFloor);
    }
    
    /**
     * Question 2: What is the position of the character that causes 
     * Santa to first enter the basement?
     */
    private static void question2() {
        int lastFloor = 0;
        int upDn = 0;
        for(; upDn < fileInfo[0].length(); upDn++){
            lastFloor += fileInfo[0].charAt(upDn) == '(' ? 1 : -1;
            if(lastFloor == -1) break;
        }
        //Track ,  Confirmed: 04- 1797   011- n/a
        System.out.println("\nPart 2: Santa enters the basement on move " + (upDn + 1));
    }
}