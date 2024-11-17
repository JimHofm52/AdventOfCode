//Advent of Code 2015
/*
Programmer: Jim Hofmann
Date Created: 11/11/2024
*/
import java.io.*;
import util.ReadInput;
import days15.*;


public class AoC2015 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadInput.year = "aoc2015";

        // Day01.update();  //<===== Change to match day   //0.128 s.
        // Day02.update();  //<===== Change to match day   //0.157 s.
        // Day03.update();  //<===== Change to match day   //0.277 s.
        // Day04.update();  //<===== Change to match day   //2.12 s.
        // Day05.update();  //<===== Change to match day   //0.239 s.
        Day06.update();  //<===== Change to match day   //0.309 s.
        // Day07.update();  //<===== Change to match day   //0.291 s.
        // Day08.update();  //<===== Change to match day   //7.549 s.
        // Day09.update();  //<===== Change to match day   //0.157 s.
        // Day10.update();  //<===== Change to match day
        // Day11.update();  //<===== Change to match day
        // Day11Big.update();  //<===== Change to match day
        // Day12.update();  //<===== Change to match day

        System.out.println("Execution time: " + 
        /*      */((System.currentTimeMillis() - startTm)/1000.0));
    }
}