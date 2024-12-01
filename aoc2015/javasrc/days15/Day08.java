package days15;

import java.io.IOException;

import util.ReadInput;

public class Day08 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. */
    private Day08(){}

    public static void update() throws IOException {
        String fNum = "08";//Part1- 1371   Part2- 2117
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 08- 1371   081- 12
        question2();    //Confirmed: 08- 2117   081- 19
    }

    /**
     * Question 1: Disregarding the whitespace in the file, what is the number of characters
     *  of code for string literals minus the number of characters in memory for the values 
     * of the strings in total for the entire file?
     * 
     * Beside "" the only 3 escape sequences used are \\, \", \x plus two hex characters.
     */
    private static void question1() {
        int[][] strLenTLE = new int[len][3];    //[x][0]=tot cnt, [x][1]=ltr cnt, [x][2]=esc cnt
        parceStrings(strLenTLE);
        //Track ,  Confirmed: 08- 1371   
        //081 The total number of characters of string code (2 + 5 + 10 + 6 = 23) 
        //    minus the total number of characters in memory for string values 
        //    (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.
        int totChar = 0;
        int totLitCh = 0;
        for(int[] ch : strLenTLE){
            totChar += ch[0];
            totLitCh += ch[1];
        }
        System.out.println("\nPart 1: Total chars: " + totChar + 
                                 " minus lit chars " + totLitCh + " is " + (totChar - totLitCh) +"\n");
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        int[][] strLenTLE = new int[len][2];    //[x][0]=tot cnt, [x][1]=enc cnt
        parceString2(strLenTLE);
        //Track ,  Confirmed: 08- 2117
        //081- the total encoded length (6 + 9 + 16 + 11 = 42) minus the characters in 
        //the original code representation (23, just like in the first part of this puzzle) 
        //is 42 - 23 = 19.
        int totAllChar = 0;
        int totOrgCh = 0;
        for(int[] ch : strLenTLE){
            totOrgCh += ch[0];
            totAllChar += ch[1];
        }
        System.out.println("\nPart 2: Total ALL chars now: " + totAllChar + 
                                 " minus orginal chars " + totOrgCh + " is " + (totAllChar - totOrgCh) +"\n");
    }

    //------------------------ Part 1 ================
    private static void parceStrings(int[][] strLenIn){
        String str;
        char ch1;
        char ch2;
        for(int i = 0; i < len; i++){
            str = fileInfo[i];
            strLenIn[i][0] = fileInfo[i].length();  //Total characters in string
            strLenIn[i][2] = 2;     //Including lead/end quote char.
            for(int j = 1; j < fileInfo[i].length() - 1; j++){  //Skip 1st & last chars.
                ch1 = str.charAt(j);
                ch2 = str.charAt(j + 1);
                if(ch1 == '\\'){
                    switch(ch2){
                        case '\"':
                        case '\\':
                        strLenIn[i][1]++;
                        j++;
                        break;
                        case 'x':           //2 Hex representation of a char - \x27
                        strLenIn[i][1]++;
                        j += 3;
                        break;
                        default:
                        strLenIn[i][2]++;
                        //(2 + 5 + 10 + 6 = 23)
                        System.out.println("Lonesome '\\', in line " + i + " at char " + j);
                    }
                }else{
                    strLenIn[i][1]++;
                }
            }
        }
        int a = 0;
    }

    private static void parceString2(int[][] strLenIn){
        String str;
        char ch1;
        for(int i = 0; i < len; i++){
            str = fileInfo[i];
            strLenIn[i][0] = fileInfo[i].length();  //Total characters in string
            strLenIn[i][1] = strLenIn[i][0] + 2;    //Including lead/end quote char and encode.
            for(int j = 0; j < fileInfo[i].length(); j++){  //Skip 1st & last chars.
                ch1 = str.charAt(j);
                switch(ch1){
                    case '\\':
                    case '\"':
                    // case '*':
                    strLenIn[i][1] += 1;
                    break;
                    default:
                    // strLenIn[i][2]++;
                    //(6 + 9 + 16 + 11 = 42)
                    // System.out.println("Just a char, " + ch1 + ", in line " + i + " at char " + j);
                }
            }
        }
        int a = 0;
    }

    //------------------------ Part 2 ================
}