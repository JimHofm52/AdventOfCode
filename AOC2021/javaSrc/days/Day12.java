package javasrc.days;

import java.io.IOException;
import java.util.Arrays;

import javasrc.util.ReadInput;

public class Day12 {
    private static String fileInfo[];
    private static int len;
    private static String nodes[];
    private static int lenNodes;

    /** Constructor, not needed but used for standards. */
    private Day12(){}

    public static void update() throws IOException {
        String fNum = "120";
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array

        question1();    //Confirmed: 12-???   121-19  122-226
        question2();    //Confirmed: 12-???   121-???
    }

    /**
     * Question 1: Count the number of possible paths from start to end.
     * <p>Small caves (lower case) can only be visited once.
     * <p>Big cave (upper case) can be visited as often as needed.
     */
    private static void question1() {
        parceInput();
        //Track ,  Confirmed: 12-???   121-19   122-226
        System.out.println("\nPart 1: ???: ");
    }
    
    /**
     * Question 2: Number of possible PWs meeting the criteria:
     */
    private static void question2() {
        //Track ,  Confirmed: 04-???   041-???
        // System.out.println("\nPart 2: ???: " + pwOKCnt);
    }

    private static void parceInput(){
        findAllNodes();     //Find all unique node IDs
        sortAllNodes();     //Sort start, ..., end
        expAllNodes();      //Match connected nodes in synced array
    }

    private static void findAllNodes(){
        nodes = new String[0];
        String node1, node2;
        int dashIdx;

        for(String s : fileInfo){
            dashIdx = s.indexOf('-');
            node1 = s.substring(0, dashIdx);
            node2 = s.substring(dashIdx + 1);
            if(!nodeExist(node1, nodes)){
                nodes = Arrays.copyOf(nodes, nodes.length + 1);
                nodes[nodes.length - 1] = node1;
            }
            if(!nodeExist(node2, nodes)){
                nodes = Arrays.copyOf(nodes, nodes.length + 1);
                nodes[nodes.length - 1] = node2;
            }
        }
        lenNodes = nodes.length;
    }

    private static boolean nodeExist(String node, String[] nodes){
        for(String s : nodes){
            if(s.equals(node)) return true;
        }
        return false;
    }

    private static void sortAllNodes(){
        int nIdx = 0;
        String tmp1, tmp2;
        String[] sortNodes;

        Arrays.sort(nodes);
        while(!nodes[nIdx].equals("start")) nIdx++;
        nodes = removeElement(nodes, nIdx);
        nodes = addElement(nodes, "start", 0);

        nIdx = 0;
        while(!nodes[nIdx].equals("end")) nIdx++;
        nodes = removeElement(nodes, nIdx);
        nodes = addElement(nodes, "end", nodes.length);
    }

    private static String[] removeElement(String[] arr, int strIdx){
        String[] newArr = new String[arr.length - 1];
        System.arraycopy(arr, 0, newArr, 0, strIdx);
        System.arraycopy(arr, strIdx + 1,
                         newArr, strIdx, arr.length - strIdx - 1);
        return newArr;
    }

    private static String[] addElement(String[] arr, String str, int strIdx){
        String[] newArr = new String[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, strIdx);
        newArr[strIdx] = str;
        System.arraycopy(arr, strIdx,
                         newArr, strIdx + 1, arr.length - strIdx);
        return newArr;
    }

    private static void expAllNodes(){
        String[][] expNodes = new String[nodes.length][nodes.length - 1];
        int dashIdx, i, j;
        String node1, node2;

        for(i = 0; i < nodes.length; i++) expNodes[i][0] = nodes[i];

        for(String s : fileInfo){
            dashIdx = s.indexOf('-');
            node1 = s.substring(0, dashIdx);
            node2 = s.substring(dashIdx + 1);
            if(!node2.equals("start")){
                i = 0;  j = 0;
                while(!nodes[i].equals(node1)) i++;
                while(expNodes[i][j] != null) j++;
                expNodes[i][j] = node2;
            }

            if(!node1.equals("start")){
                i = 0;  j = 0;
                while(!nodes[i].equals(node2)) i++;
                while(expNodes[i][j] != null) j++;
                expNodes[i][j] = node1;
            }
        }
        i = 0;
    }

    private static void getNextNode(){
        
    }
}