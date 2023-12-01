package type;

public class StrNum {
    private static String[][] str ={{"zero", "one", "two", "three", "four",
                              "five", "six", "seven", "eight", "nine"},
                             {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}};

    public static String getStr(int idx){
        return idx >= 0 && idx <= 9 ? str[0][idx] : "error";
    }
    
    public static String getNum(int idx){
        return idx >= 0 && idx <= 9 ? str[1][idx] : "error";
    }
    
}
