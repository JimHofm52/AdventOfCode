//Advent of Code 2022
/*
Programmer: Jim Hofmann
Date Created: 12/1/2022
*/

import java.io.*;

import util.ReadInput;
import days22.*;

public class AoC2022 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadInput.year = "AoC2022";

        // Day01.update();  //<===== Change to match day   //0.136 s.
        // Day01A.update();  //<===== Change to match day, simplier version    //0.126 s.
        // Day02.update();  //<===== Change to match day   //0.132 s.
        // Day03.update();  //<===== Change to match day   //0.107 s.
        // Day04.update();  //<===== Change to match day   //0.122 s.
        // Day05.update();  //<===== Change to match day   //0.106 s.
        // Day05A.update();  //<===== Change to match day, using Stacks   //0.106 s.
        // Day06.update();  //<===== Change to match day   //0.95 s.
        // Day07.update();  //<===== Change to match day
        // Day08.update();  //<===== Change to match day
        // Day09.update();  //<===== Change to match day
        // Day09A.update();  //<===== Change to match day      //rewrite of Day09 for part2
        // Day10.update();  //<===== Change to match day
        Day11.update();  //<===== Change to match day
        // Day12.update();  //<===== Change to match day

        System.out.println("Execution time: " + 
        /*      */((System.currentTimeMillis() - startTm)/1000.0));
    }
}