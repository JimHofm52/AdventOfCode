package days18;

import java.io.IOException;

import type.Design;
import util.ReadWriteFiles;

public class Day03 {
    private static String fileInfo[];
    private static int len;
    private static Design[] elfDesign;
    private static short[][] clothBoltXY = new short[1000][1000];

    /** Constructor, not needed but used for standards. */
    private Day03(){}

    public static void update() throws IOException {
        String fNum = "03"; //Part1- 100595   Part2- ???
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        elfDesign = new Design[len];
        for(int i = 0; i < len; i++) elfDesign[i] = new Design(fileInfo[i]);
    
        question1();    //Confirmed: 03- 100595   031- 4
        question2();    //Confirmed: 03- 415      031- 3
    }

    /**
     * Question 1: The whole piece of fabric they're working on 
     * is a very large square - at least 1000 inches on each side.
     * If the Elves all proceed with their own plans, none of them 
     * will have enough fabric. 
     * 
     * How many square inches of fabric are within two or more claims?
     */
    private static void question1() {
        mapDesigns();
        //Track ,  Confirmed: 03- 100595   041- 4
        System.out.println("\nPart 1: Sq. inches overlap. " + cntOverlap());
    }
    
    /**
     * Question 2: What is the ID of the only claim that doesn't overlap?
     */
    private static void question2() {
        mapOverlap();
        int id = findNoOverlap();
        if(id < 0 ){
            System.out.println("\nPart 2: Error, All overlap ");
        }else{
            //Track ,  Confirmed: 03- 415   031- 3
            System.out.println("\nPart 2: Elf Design ID: " + id);
        }
    }

    private static void mapDesigns(){
        for(Design d : elfDesign){
            for(int x = d.getLeft(); x < (d.getLeft() + d.getWidth()); x++){
                for(int y = d.getTop(); y < (d.getTop() + d.getLength()); y++){
                    if(x > 999 || y > 999){
                        System.out.println("Error: x = " + x + "  y = " + y);
                    }
                    clothBoltXY[x][y]++;
                }
            }
        }
    }

    private static int cntOverlap(){
        int cnt = 0;
        for(int x = 0; x < clothBoltXY.length; x++){
            for(int y = 0; y < clothBoltXY[0].length; y++){
                if(clothBoltXY[x][y] > 1) cnt++;
            }
        }
        return cnt;
    }

    private static void mapOverlap(){
        for(Design d : elfDesign){
            for(int x = d.getLeft(); x < (d.getLeft() + d.getWidth()); x++){
                for(int y = d.getTop(); y < (d.getTop() + d.getLength()); y++){
                    if(clothBoltXY[x][y] > 1) d.setOverlap();
                }
            }
        }
    }

    private static int findNoOverlap(){
        for(Design d : elfDesign){
            if(!d.getOverlap()) return d.getID();
        }
        return -1;
    }
}