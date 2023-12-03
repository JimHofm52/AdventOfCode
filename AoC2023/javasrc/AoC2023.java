//Advent of Code 2023
/*
Programmer: Jim Hofmann
Date Created: 12/1/2023
*/

import java.io.*;

import util.ReadInput;
import days23.*;

public class AoC2023 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadInput.year = "AoC2023";

        // Day01.update();  //<===== Change to match day   //0.224 s.
        // Day02.update();  //<===== Change to match day   //0.144 s.
        Day03.update();  //<===== Change to match day   //0.107 s.
        // Day04.update();  //<===== Change to match day   //0.122 s.
        // Day05.update();  //<===== Change to match day   //0.106 s.
        // Day05A.update();  //<===== Change to match day, using Stacks   //0.106 s.
        // Day06.update();  //<===== Change to match day   //0.95 s.
        // Day07.update();  //<===== Change to match day
        // Day08.update();  //<===== Change to match day
        // Day09.update();  //<===== Change to match day
        // Day09A.update();  //<===== Change to match day      //rewrite of Day09 for part2
        // Day10.update();  //<===== Change to match day
        // Day11.update();  //<===== Change to match day
        // Day11Big.update();  //<===== Change to match day
        // Day12.update();  //<===== Change to match day

        System.out.println("Execution time: " + 
        /*      */((System.currentTimeMillis() - startTm)/1000.0));
    }
}