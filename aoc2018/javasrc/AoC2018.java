//Advent of Code 2018
/*
Programmer: Jim Hofmann
Date Created: 12/1/2023
*/
import java.io.*;
import util.ReadInput;
import days18.*;


public class AoC2018 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadInput.year = "aoc2018";

        // Day01.update();  //<===== Change to match day   //13.779 s.
        // Day02.update();  //<===== Change to match day   //0.164 s.
        // Day03.update();  //<===== Change to match day   //0.225 s.
        // Day04.update();  //<===== Change to match day   //0.227 s.
        Day05.update();  //<===== Change to match day   //1.187 s.
        // Day06.update();  //<===== Change to match day   //0.136 s.
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