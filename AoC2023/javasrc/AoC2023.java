//Advent of Code 2023
/*
Programmer: Jim Hofmann
Date Created: 12/1/2023
*/
import java.io.*;
import util.ReadWriteFiles;
import days23.*;


public class AoC2023 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadWriteFiles.year = "AoC2023";

        Day01.update();  //<===== Change to match day   //0.224 s.
        // Day02.update();  //<===== Change to match day   //0.144 s.
        // Day03.update();  //<===== Change to match day   //0.275 s.
        // Day04.update();  //<===== Change to match day   //0.163 s.
        // Day05.update();  //<===== Change to match day   //228.940 s.
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