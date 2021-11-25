package days;

import java.io.IOException;
import util.ReadInput;

public class Day01 {

    public static void update() throws IOException {
        String fileInfo[];
        fileInfo = ReadInput.GetInputStr("011");   //Get input in an array for 5
        int len = fileInfo.length;          //Length of input array
        int tmpFuel;
        for(int module : fileInfo){
            tmpFuel = (module / 3) - 2;
        }
    }
}