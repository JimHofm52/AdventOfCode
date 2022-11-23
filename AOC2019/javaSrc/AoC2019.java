/*
Advent of Code 2019

Programmer: Jim Hofmann
Date Created: 11/24/2021
*/

import java.io.*;

import days19.*;
import util.ReadInput;

public class AoC2019 {
    public static void main(String[] arg) throws IOException{
        final long startTm = System.currentTimeMillis();
        ReadInput.year = "AoC2019";

        // Day01.update();  //<===== Change to match day
        // Day02.update();  //<===== Change to match day
        // Day03.update();  //<===== Change to match day
        Day03A.update();  //<===== Change to match day.  Day03 cleanup
        // Day04.update();  //<===== Change to match day
        // Day05.update();  //<===== Change to match day    ---- Not completed ----
        System.out.println("Execution time: " + 
        /*      */((System.currentTimeMillis() - startTm)/1000.0));
    }
}