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

        // Day01.update();  //<===== Change to match day   //0.132 s.
        // Day02.update();  //<===== Change to match day   //0.251 s.
        // Day03.update();  //<===== Change to match day   //0.274
        // Day03A.update();  //<===== Change to match day. //0.154  Day03 cleanup
        // Day04.update();  //<===== Change to match day   //0.213
        // Day05.update();  //<===== Change to match day   //0.169
        // Day06.update();  //<===== Change to match day   //0.206 s.
        Day07.update();  //<===== Change to match day   //0.206 s.
        System.out.println("Execution time: " + 
        /*      */((System.currentTimeMillis() - startTm)/1000.0));
    }
}