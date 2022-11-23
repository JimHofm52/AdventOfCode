package days19;

import java.io.IOException;

import util.ReadInput;

public class Day01 {
    private static int fileInfo[];  //Array for weight of modules
    private static int len;

    /**Constructor, not needed but used for standards. */
    private Day01(){}

    /**
     * Readin data for Year 2019, Day1, Questions 1 & 2 from 
     * <p>AdventOfCode.com
     * 
     * @throws IOException - ????
     */
    public static void update() throws IOException {
        fileInfo = ReadInput.getInputInt("01");   //Get input in an array for 19
        int len = fileInfo.length;          //Length of input array

        System.out.println("Total modules: " + len);

        question1();    //confirmed - 3159380
        question2();    //confirmed - 4736213
    }

    /**
     * Calc fuel for each module as mod / 3 - 2.
     */
    private static void question1() {
        int tmpFuel = 0;
        for(int module : fileInfo){
            tmpFuel += (module / 3) - 2;    //Calc fuel for the module only.
        }
        System.out.println("Fuel for modules only: \t\t" + tmpFuel);    //confirmed - 3159380
    }

    /**
     * Calc fuel for each module recursively with same formula.
     * Fuel for the fuel.
     */
    private static void question2() {
        int tmpFuel = 0;
        for(int module : fileInfo){
            tmpFuel += calcFuel(module);    //Calc fuel for module + fuel for the fuel.
        }
        System.out.println("Fuel for mods + fuel for fuel: \t" + tmpFuel);    //confirmed - 4736213
    }

    /**
     * Recursively calcFuel until calcFuel is LTEQ 0.
     * 
     * @param module - inital module weight to calc fuel.
     * @return - Weight of fuel including fuel for the fuel.
     */
    private static int calcFuel(int module){
        int tmpFuel = (module / 3) - 2;
        if(tmpFuel <= 0) return 0;
        return tmpFuel + calcFuel(tmpFuel);
    }
}