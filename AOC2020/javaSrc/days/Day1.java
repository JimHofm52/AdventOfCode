package days;

import java.io.IOException;
import util.ReadInput;

public class Day1 {
    private static int fileInfo[];
    private static int ptr[];
    private static int len;

    /**
     * Constructor, not needed but used for standards.
     */
    private Day1(){
    }

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputInt(1);    //Get input in an array for 5
        len = fileInfo.length;                  //Length of input array
        ptr = new int[2];
        question1();
        ptr = new int[3];
        question2();
    }

    /**
     * Find 2 values that sum to 2020.  What is the product.
     * Num - 148       Value - 1312
     * Num - 164       Value - 708
     * Sum = 2020      Product = 928896
     */
    private static void question1(){
        for ( ptr[0] = 0; ptr[0] < len - 1; ptr[0]++) {
            for(ptr[1] = ptr[0] + 1; ptr[1] < len; ptr[1]++){
                if(2020 == valSum()){
                    displayInfo();
                    break;
                }
            }
        }
    }

    /**
     * Find 3 values that sum to 2020.  What is the product.
     * Num - 44        Value - 798
     * Num - 55        Value - 664
     * Num - 90        Value - 558
     * Sum = 2020      Product = 295668576
     */
    private static void question2(){
        for ( ptr[0] = 0; ptr[0] < len - 2; ptr[0]++) {
            for(ptr[1] = ptr[0] + 1; ptr[1] < len - 1; ptr[1]++){
                for(ptr[2] = ptr[1] + 1; ptr[2] < len; ptr[2]++){
                    if(2020 == valSum()){
                        displayInfo();
                        break;
                    }
                }
            }
        }
    }

    /**
     * @return Calc'ed product of values pointed to.
     */
    private static int valSum(){
        int sum = 0;
        for(int i : ptr){
            sum += fileInfo[i];
        }
        return sum;
    }

    /**
     * @return Calc'ed product of values pointed to.
     */
    private static int valProd(){
        int prod = 1;
        for(int i : ptr){
            prod *= fileInfo[i];
        }
        return prod;
    }

   /**************************************************************************
     * Print the Answsers
     * 
     * @param intInfo
     * @param ptr1
     * @param ptr2
     */
    private static void displayInfo(){
        System.out.println("\n");
        for(int i : ptr){
            System.out.println("Num - " + i + "\tValue - " + fileInfo[i]);   //confirmed 892
        }

        System.out.println("\t\t Sum = " + valSum() +
                     "\tProduct = " + valProd());
    }
}