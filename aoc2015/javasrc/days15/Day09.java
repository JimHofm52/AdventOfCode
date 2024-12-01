package days15;

import java.io.IOException;
import java.util.Arrays;

import type.City;
import util.ReadInput;

public class Day09 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day09(){}

    public static void update() throws IOException {
        String fNum = "091";//Part1- ???   Part2- ???
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        City[] myCity = new City[len];
        // String[] cityList  = new String[0];

        for(int i = 0; i < len; i++) myCity[i] = new City(fileInfo[i]);
        String[] cityList  = findNewCity(myCity);

        question1();    //Confirmed: 09- ???   091- ???
        question2();    //Confirmed: 09- ???   091- ???
    }

    /**
     * Question 1: Number of possible PWs meeting the criteria:
     */
    private static void question1() {
        //Track ,  Confirmed: 09- ???   Hi-431

        //091-  London to Dublin = 464
        //      London to Belfast = 518
        //      Dublin to Belfast = 141
        //What is the shortest route between all cities?
        //     Dublin -> London -> Belfast = 982
        //     London -> Dublin -> Belfast = 605
        //     London -> Belfast -> Dublin = 659
        //     Dublin -> Belfast -> London = 659
        //     Belfast -> Dublin -> London = 605
        //     Belfast -> London -> Dublin = 982  
        //     shortest of these is London -> Dublin -> Belfast = 605
        System.out.println("\nPart 1: The shortest route between all cities: " + 1);
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 09- ???   091- ???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }

    //-------------  Parce Input  ----------------------------
    private static void cntCities(){

    }

    private static String[] findNewCity(City[] cty){
        String[] tmpStr = {cty[0].getPrime()};
        tmpStr = addCity(tmpStr, cty[0].getSecond());
        for(City c : cty){
            if(chkNewCity(tmpStr, c.getPrime())) tmpStr = addCity(tmpStr, c.getPrime());
            if(chkNewCity(tmpStr, c.getSecond())) tmpStr = addCity(tmpStr, c.getSecond());
        }
        return tmpStr;
    }

    private static boolean chkNewCity(String[] ctyLst, String cty){
        for(String tCty : ctyLst){
            if(tCty.equals(cty)) return false;
        }
        return true;
    }

    private static String[] addCity(String[] ctyLst, String ctyStr){
        int cLen = ctyLst.length;
        ctyLst = Arrays.copyOf(ctyLst, cLen + 1);
        ctyLst[cLen] = ctyStr;
        return ctyLst;
    }
}