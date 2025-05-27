//Advent of Code 2023
/*
Programmer: Jim Hofmann
Date Created: 12/1/2023
*/
import java.io.*;
import util.ReadWriteFiles;
import days24.*;


public class AoC2024 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadWriteFiles.year = "AoC2024";

        // Day01.update();  //<===== Change to match day   //0.208 s.
        // Day02.update();  //<===== Change to match day   //0.207 s.
        // Day03.update();  //<===== Change to match day   //0.161 s.
        // Day04.update();  //<===== Change to match day   //0.226 s.
        // Day05.update();  //<===== Change to match day   //221 s.
        // Day06.update();  //<===== Change to match day   //19.533 s.
        // Day06A.update();  //<===== Change to match day  //18.375 s.
        // Day07.update();  //<===== Change to match day   //1.646 s.
        // Day08.update();  //<===== Change to match day   //0.175 s.
        Day09.update();  //<===== Change to match day   //0.157 s.
        // Day10.update();  //<===== Change to match day
        // Day11.update();  //<===== Change to match day
        // Day11Big.update();  //<===== Change to match day
        // Day12.update();  //<===== Change to match day

        System.out.println("Execution time: " + 
        /*      */((System.currentTimeMillis() - startTm)/1000.0));
    }
}