package days15;

import java.io.IOException;
import java.util.ArrayList;

import type.House;
import util.ReadWriteFiles;

public class Day03 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day03(){}

    public static void update() throws IOException {
        String fNum = "03";//Part1- 2081   Part2- 2341
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 03- 2081   031a: 2, b: 4, c: 2
        question2();    //Confirmed: 03- 2341   032a: 2, b: 3, c: 11 
    }

    /**
     * Question 1: Moves are always exactly one house to the 
     * north (^), south (v), east (>), or west (<). After each move, 
     * he delivers another present to the house at his new location.
     * 
     * However, the elf back at the north pole has had a little too much eggnog, 
     * and so his directions are a little off, and Santa ends up visiting some 
     * houses more than once. How many houses receive at least one present?
     */
    private static void question1() {
        int housesVisited = parceInput(false);
        //Track ,  Confirmed: 03- 2081   031a: 2, b: 4, c: 2
        System.out.println("\nPart 1: Houses that got at least 1 gift: " + housesVisited);
    }
    
    /**
     * Question 2: Santa and Robo-Santa start at the same location (delivering 
     * two presents to the same starting house), then take turns moving based 
     * on instructions from the elf, who is eggnoggedly reading from the same 
     * script as the previous year.  Santa goes first.
     * 
     * This year, how many houses receive at least one present?
     */
    private static void question2() {
        int housesVisited = parceInput(true);
        //Track ,  Confirmed: 03- 2341   031a: 3, b: 3, c: 11
        System.out.println("\nPart 2: Houses that got at least 1 gift: " + housesVisited + "\n");
    }

    //------------------------------ Count houses -----------------------------
    private static int parceInput(boolean isQ2){
        ArrayList<House> house = new ArrayList<>();

        char ch;
        int[] santaAdr = {0, 0};   //[0] x, [1] y
        int[] roboSantaAdr = {0, 0};
        int[] tmpAdr;

        boolean dup = false;
        House tmpHse = new House(santaAdr); //Adds the first house [0. 0]
        house.add(tmpHse);

        for(int i = 0; i < fileInfo[0].length(); i++){
            ch = fileInfo[0].charAt(i);
            tmpAdr = (i % 2 == 1 && isQ2) ? roboSantaAdr : santaAdr;
            incrMove(tmpAdr, ch);

            dup = false;
            for(House h : house){
                if(h.chkAdrEqual(tmpAdr)){
                    h.incrGift();
                    dup = true;
                }
            }
            if(!dup){
                tmpHse = new House(tmpAdr);
                house.add(tmpHse);
            }
        }
        int a = 0;
        return house.size();
    }

    /**
     * Increament address per char passed.
     * @param xy
     * @param at
     */
    private static void incrMove(int[] xy, char ch){
        switch( ch ){
            case '>':   //X right
            xy[0]++;
            break;
            case '^':   //Y Up
            xy[1]++;
            break;
            case '<':   //X left
            xy[0]--;
            break;
            case 'v':   //Y dn
            xy[1]--;
            break;
            default:
            System.out.println("WTH? " + ch);
        }
    } 
}