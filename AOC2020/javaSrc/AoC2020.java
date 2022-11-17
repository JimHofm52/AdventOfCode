/*
Advant of Code 2020

Programmer: Jim Hofmann
Date Created: 12/5/2020
*/

import java.io.IOException;

import days20.Day14;
import util.ReadInput;

public class AoC2020 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadInput.year = "AoC2020";

        // Day1.update();  //<===== Change to match day    0.138 mS
        // Day2.update();  //<===== Change to match day    0.108 mS
        // Day3.update();  //<===== Change to match day    0.083 mS
        // Day4.update();  //<===== Change to match day    0.108 mS
        // Day5.update();  //<===== Change to match day    0.181 mS
        // Day6.update();  //<===== Change to match day    0.125 mS
        // Day7.update();  //<===== Change to match day    0.141 mS
        // Day8.update();  //<===== Change to match day    0.151 mS
        // Day9.update();  //<===== Change to match day    0.099 mS
        // Day10.update();  //<===== Change to match day   0.076 mS
        // Day11.update();  //<===== Change to match day   0.674 mS
        // Day12.update();  //<===== Change to match day   0.111 mS
        // Day13.update();  //1st Try: 27.07 MINUTES!, 2nd: 1.10 Min. (60.6 mS)
        Day14.update();  //<===== Change to match day   3.301 mS
        // Day15.update();  //<===== Change to match day   0.731 mS
        // Day16.update();  //<===== Change to match day   0.169 mS
        // Day17.update();  //<===== Change to match day   0.961 mS part 1 only
        // Total runtime for all days (so far) *<|;-{))>, 65.878 mS
        System.out.println("Execution time: " + 
                            ((System.currentTimeMillis() - startTm)/1000.0));
    }
}