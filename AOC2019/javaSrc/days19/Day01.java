package days19;

import java.io.IOException;

import util.ReadInput;

public class Day01 {
    private static int fileInfo[];
    private static int len;

    /**Constructor, not needed but used for standards. */
    private Day01(){}

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputInt("01");   //Get input in an array for 5
        int len = fileInfo.length;          //Length of input array

        question1();
        question2();
    }

    /**
     * Calc fuel for each module as mod / 3 - 2.
     */
    private static void question1() {
        int tmpFuel = 0;
        for(int module : fileInfo){
            tmpFuel += (module / 3) - 2;
        }
        System.out.println(tmpFuel);    //confirmed - 3159380
    }

    /**
     * Calc fuel for each module recursively with same formula.
     * Fuel for the fuel.
     */
    private static void question2() {
        int tmpFuel = 0;
        for(int module : fileInfo){
            tmpFuel += calcFuel(module);
        }
        System.out.println(tmpFuel);    //confirmed - 4736213
    }

    private static int calcFuel(int module){
        int tmpFuel = (module / 3) - 2;
        if(tmpFuel <= 0) return 0;
        return tmpFuel + calcFuel(tmpFuel);
    }
}