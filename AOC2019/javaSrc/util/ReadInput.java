package util;

import java.util.*;
import java.io.*;

public class ReadInput {
    
    private static String year = "AOC2019";

    private ReadInput(){
        throw new IllegalStateException("Utility class"); 
    }
    /**
     * Read in strings from a text file and return an array of strings.
     * @param dayNumday number of the input file to evaluate
     * @return int array of values in text file.
     * @throws IOException
     */
    public static String[] getInputStr(String dayNum) throws IOException{

        String fPath = getFilePath(dayNum);
        File file = new File(fPath);
        String fileIn[] = new String[getFileLen(fPath)]; //Declare array to length of file lines
        //Initialize with "" - for(int s = 0; s < fileIn.length; s++) fileIn[s] = ""

        Scanner sf = new Scanner(file); //Open file
        int maxIndx = -1;
        while(sf.hasNext()){    //Read in lines from file
            maxIndx++;
            fileIn[maxIndx] = sf.nextLine();
        }
        sf.close();             //Close file

        return fileIn;          //Pass back new array
    }

    /** dayNum is int and adds leading 0 for LT 9, converts to string. */
    public static String[] getInputStr(int dayNum) throws IOException{
        return getInputStr((dayNum < 10 ? "0" : "") + String.valueOf(dayNum));
    }

    /**
     * Read in int from file and return an array of int.
     * @param dayNum day number of the input file to evaluate
     * @return int array of values in text file.
     * @throws IOException
     */ 
    public static int[] getInputInt(String dayNum) throws IOException{

        String fPath = getFilePath(dayNum);
        File file = new File(fPath);
        int fileIn[] = new int[getFileLen(fPath)]; //Declare array to length of file lines

        Scanner sf = new Scanner(file); //Open file
        int maxIndx = -1;
        while(sf.hasNext()){    //Read in lines from file
            maxIndx++;
            fileIn[maxIndx] = Integer.parseInt(sf.nextLine());
        }
        sf.close();             //Close file

        return fileIn;          //Pass back new array
    }

    /** dayNum is int and adds leading 0 for LT 9, converts to string. */ 
    public static int[] getInputInt(int dayNum) throws IOException{
        return getInputInt((dayNum < 10 ? "0" : "") + String.valueOf(dayNum));
    }

    /**
     * Read in a text file as int and return an array of int.
     * @param dayNum day number of the input file to evaluate
     * @return int array of values in text file.
     * @throws IOException
     */
    public static int[] getInputIntCS(String dayNum) throws IOException{

        String fileIn[] = getInputStr(dayNum);

        int numIn[] = new int[0];
        for(int i = 0; i < fileIn.length; i++){
                numIn = parceStrIntCS(fileIn[i], numIn);
        }
        return numIn;          //Pass back new array
    }

    /** dayNum is int and adds leading 0 for LT 9, converts to string. */ 
    public static int[] getInputIntCS(int dayNum) throws IOException{
        return getInputIntCS((dayNum < 10 ? "0" : "") + String.valueOf(dayNum));
    }

    /**
     * Read in a text file as long and return an array of long.
     * @param dayNum day number of the input file to evaluate
     * @return long array of values in text file.
     * @throws IOException
     */
    public static long[] getInputLong(String dayNum) throws IOException{

        String fPath = getFilePath(dayNum);
        File file = new File(fPath);
        long[] fileIn = new long[getFileLen(fPath)]; //Declare array to length of file lines

        Scanner sf = new Scanner(file); //Open file
        int maxIndx = -1;
        while(sf.hasNext()){    //Read in lines from file
            maxIndx++;
            fileIn[maxIndx] = Long.parseLong(sf.nextLine());
        }
        sf.close();             //Close file

        return fileIn;          //Pass back new array
    }

    /** dayNum is int and adds leading 0 for LT 9, converts to string. */ 
    public static long[] getInputLong(int dayNum) throws IOException{
        return getInputLong((dayNum < 10 ? "0" : "") + String.valueOf(dayNum));
    }

    /**
     * Convert a space seperated string and crlf separated record to an array of records.
     * IE. Day4:
     * <p>eyr:2029 ecl:amb
     * <p>byr:1980
     * <p>hgt:177cm pid:914628384 hcl:#623a2f iyr:2013
     * <p>crlf (empty)
     * <P>to:
     * <p>eyr:2029 ecl:amb byr:1980 hgt:177cm pid:914628384 hcl:#623a2f iyr:2013(sp)
     * @param readIn list of records separated by empty line, separted items space 
     * separted values, on various line.
     * @return an array of int
     */
    public static String[] getInputStrSpS(String dayNum) throws IOException{

        String fPath = getFilePath(dayNum);
        File file = new File(fPath);
        String fileIn[] = new String[getFileRcdCnt(fPath)]; //Declare array to record count
        //Initialize with "" - for(int s = 0; s < fileIn.length; s++) fileIn[s] = ""

        Scanner sf = new Scanner(file); //Open file
        int maxIdx = 0;
        String strIn = "";
        String strLn = "";
        while(sf.hasNext()){    //Read in lines from file
            fileIn[maxIdx] = "";
            strIn = sf.nextLine();
            if(strIn.isEmpty()){
                fileIn[maxIdx] = strLn;
                maxIdx++;
                strLn = "";
            }else{
                if(!strLn.isEmpty()) strLn = strLn.concat(" ");
                strLn = strLn.concat(strIn);
            }
            fileIn[maxIdx] = strLn;
        }
        sf.close();             //Close file

        return fileIn;          //Pass back new array
    }

    /** dayNum is int and adds leading 0 for LT 9, converts to string. */ 
    public static String[] getInputStrSpS(int dayNum) throws IOException{
        return getInputStrSpS((dayNum < 10 ? "0" : "") + String.valueOf(dayNum));
    }

    /**
     * Assemble full path/filename using the day number.
     * Probably need to pull the common directory from the system.
     * @param dayNum day number of the input file to open
     * @return string with full path/filename
     */
    private static String getFilePath(String dayNum){
        String fDir = "C:\\Users\\Hofmjc\\Documents\\_Prog\\AdventOfCode\\" +
                       year + "\\javaSrc\\textIn";
        String fName = "Day" + dayNum + "Input.txt";
        return fDir + "\\" + fName;
    }

    /** dayNum is int and adds leading 0 for LT 9, converts to string. */ 
    private static String getFilePath(int dayNum){
        return getFilePath((dayNum < 10 ? "0" : "") + String.valueOf(dayNum));
    }

    /**
     * Find number of crlf lines, length, in a file.
     * Open file, count lines and close file.  Return count.
     * @param fPaNa  file path/filename.txt
     * @return count of lines
     * @throws IOException
     */
    private static int getFileLen(String fPaNa)  throws IOException{
        File file = new File(fPaNa);
        Scanner sf = new Scanner(file);
        int i = 0;
        while(sf.hasNext()){
            sf.nextLine();      //txt = sf.nextLine(), don't need assignment.
            i++;
        }
        sf.close();
        return i;
    }

    /**
     * Find number of empty lines, records, in a file.
     * Open file, count empty lines and close file.  Return count.
     * @param fPaNa  file path/filename.txt
     * @return count of lines
     * @throws IOException
     */
    private static int getFileRcdCnt(String fPaNa)  throws IOException{
        File file = new File(fPaNa);
        Scanner sf = new Scanner(file);
        int i = 0;
        String txt = "";
        while(sf.hasNext()){
            txt = sf.nextLine();      //txt = sf.nextLine(), don't need assignment.
            if(txt.isEmpty()) i++;
        }
        i++;    //Last record.
        sf.close();
        return i;
    }

    /**
     * Convert a comma seperated string of int to an array and append to passed array.
     * @param readIn comma seprated string list of intergers
     * @param numIn is the number of int in the list.
     * @return additional int in the readIn array appended to numIn
     */
    public static int[] parceStrIntCS(String readIn, int[] numIn){
        int numLen = numIn.length;  //Length of existing int array
        int cCnt = 0;               //Count of new ones
        for(int i = 0; i < readIn.length(); i++) if(readIn.charAt(i) == ',') cCnt++; //Count commas
        numIn = Arrays.copyOf(numIn, numLen + ++cCnt);  //Expand array for new values

        /*
         * Read in each char.  If it is a "," parse in from beg to end as an int.
         * and insert into numIn array.
         */
        int beg = 0;
        for(int end = 0; end < readIn.length(); end++){
            if(readIn.charAt(end) == ','){
                numIn[numLen] = Integer.parseInt(readIn.substring(beg, end));
                beg = end + 1;
                numLen++;
            }
        }
        //Do last entry
        numIn[numLen] = Integer.parseInt(readIn.substring(beg, readIn.length()));
        return numIn;
    }
    
    /**
     * Convert a comma seperated string (of int) to an array of int.
     * @param readIn comma seprated string list of intergers
     * @return an array of int
     */
    public static int[] parceStrIntCS(String readIn){
        int cCnt = 0;   //Comma count
        for(int i = 0; i < readIn.length(); i++) if(readIn.charAt(i) == ',') cCnt++; //Count commas
        int[] numIn = new int[++cCnt];  //Define an array to hold the intergers

        cCnt = 0;
        int beg = 0;
        for(int end = 0; end < readIn.length(); end++){     //For each char
            if(readIn.charAt(end) == ','){                  //if char is ','
                //parse in from beg to end as an int.
                numIn[cCnt] = Integer.parseInt(readIn.substring(beg, end));
                cCnt++;
                beg = end + 1;
            }
        }
        //Do last entry
        numIn[cCnt] = Integer.parseInt(readIn.substring(beg, readIn.length()));
        return numIn;
    }
}