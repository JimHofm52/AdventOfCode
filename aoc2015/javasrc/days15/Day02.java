package days15;

import java.io.IOException;

import type.Box;
import util.ReadWriteFiles;

public class Day02 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day02(){}

    public static void update() throws IOException {
        String fNum = "02"; //Part1- 1586300   Part2- 3737498
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        Box[] santaBox = new Box[len];
        for(int idx = 0; idx < len; idx++) santaBox[idx] = new Box(fileInfo[idx]);

        question1(santaBox);    //Confirmed: 02- 1586300  021- 58
        question2(santaBox);    //Confirmed: 02- 3737498   021- 34
    }

    /**
     * Question 1: The elves have a list of the dimensions (length l, width w, and height h) 
     * of each present, and only want to order exactly as much as they need.
     * 
     * Every present is a box (a perfect right rectangular prism), which makes calculating 
     * the required wrapping paper for each gift a little easier: find the surface area of 
     * the box, which is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper 
     * for each present: the area of the smallest side.
     * 
     * Ex. 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet
     *     1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet
     * 
     * How many total square feet of wrapping paper should they order?
     */
    private static void question1(Box[] sBox) {
        int totalPaper = 0;
        for(Box sb : sBox) totalPaper += sb.getTotalArea();
        //Track ,  Confirmed: 02- 1586300   021- ???
        System.out.println("\nPart 1: Total square feet of wrapping paper: " + totalPaper);
    }
    
    /**
     * Question 2: The elves are also running low on ribbon.
     * The ribbon required to wrap a present is the shortest distance around its sides, 
     * or the smallest perimeter of any one face. Each present also requires a bow made 
     * out of ribbon as well; the feet of ribbon required for the perfect bow is equal 
     * to the cubic feet of volume of the present. Don't ask how they tie the bow, though; 
     * they'll never tell.
     * 
     * Ex. 2x3x4 requires 2+2+3+3 = 10 feet of ribbon + 2*3*4 = 24 feet of ribbon for the bow = 34 feet.
     *     1x1x10 requires 1+1+1+1 = 4 feet of ribbon + 1*1*10 = 10 feet of ribbon for the bow = 14 feet.
     */
    private static void question2(Box[] sBox) {
        int totalRibbon = 0;
        for(Box sb : sBox) totalRibbon += sb.getMinPerim() + sb.getTotalVol();
        //Track ,  Confirmed: 02- 3737498   021- 34
        System.out.println("\nPart 2: Total length of ribbon: " + totalRibbon);
    }
}