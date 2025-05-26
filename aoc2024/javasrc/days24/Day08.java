package days24;

import java.io.IOException;
import java.util.Arrays;

import type.Node24;
import util.ReadWriteFiles;

public class Day08 {
    private static String fileInfo[];
    private static int len;

    /** Constructor, not needed but used for standards. 
     * <p>Started May 25, 2025,  Finished on May 25 2025.
     * <p>Took ~12 hour.  Runtime 0.175 S.
    */
    private Day08(){}

    public static void update() throws IOException {
        String fNum = "08";             //Part1- 409   Part2- 1308
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array


        Node24[] antNode = findAntenna();

        question1(antNode);    //Confirmed: 08- 409 hi-420  081- 14
        question2(antNode);    //Confirmed: 08- 1308   081- 34
    }

    /**
     * Question 1: Calculate the impact of the signal. 
     * How many unique locations within the bounds of the map contain an antinode?
     */
    private static void question1(Node24[] antNode) {
        Node24[] antiNode = findAntiNodes1(antNode);
        //Track ,  Confirmed: 08- 409  hi-420  081- 14
        System.out.println("\nPart 1: Unique antiNode  locations: " + antiNode.length);
    }
    
    /**
     * Question 2: Calculate the impact of the signal using this updated model. 
     * How many unique locations within the bounds of the map contain an antinode?
     */
    private static void question2(Node24[] antNode) {
        Node24[] antiNode = findAntiNodes2(antNode);

        //Map nodes for troubleshooting
        String[] aMap = mapANode(antiNode, len, fileInfo[0].length());
        aMap = addNode(aMap, antNode, len, fileInfo[0].length());
        // ReadWriteFiles.writeOutputStr("081", aMap);  //Test, print text

        //Track ,  Confirmed: 08- 1308   081- 34
        System.out.println("\nPart 2: Unique antiNode  locations: " + antiNode.length);
    }

    /**
     * Find ahtenna as nodes.  Node holds row, col and id(char).
     * @return An array of Antenna nodes.
     */
    private static Node24[] findAntenna(){
        Node24[] myNode = new Node24[0];
        for(int r = 0; r < fileInfo.length; r++){
            for(int c = 0; c < fileInfo[0].length(); c++){
                if(fileInfo[r].charAt(c) != '.'){
                    myNode = Arrays.copyOf(myNode, myNode.length + 1);
                    myNode[myNode.length - 1] = new Node24(r, c, fileInfo[r].charAt(c));
                }
            }
        }
        return myNode;
    }

    /**
     * Find all anti nodes associated with each antenna by starting with
     * the first then checking for all other same ID then moving to next.
     * @param antN array of anti nodes.
     * @return
     */
    private static Node24[] findAntiNodes1(Node24[] antN){
        Node24[] tmpNode = new Node24[0];
        int rLmt = fileInfo.length;
        int cLmt = fileInfo[0].length();
        int dRow, dCol, nRow, nCol;
        for(int i = 0; i < antN.length - 1; i++){
            for(int j = i + 1; j < antN.length; j++){
                if(antN[i].id() == antN[j].id()){
                    dRow = antN[j].r() - antN[i].r();
                    dCol = antN[j].c() - antN[i].c();

                    nRow = antN[i].r() - dRow;
                    nCol = antN[i].c() - dCol;
                    if(nRow > -1 && nRow < rLmt && nCol > -1 && nCol < cLmt){
                        tmpNode = addNode(tmpNode, nRow, nCol, antN[i].id() );
                    }
                    nRow = antN[j].r() + dRow;
                    nCol = antN[j].c() + dCol;
                    if(nRow > -1 && nRow < rLmt && nCol > -1 && nCol < cLmt){
                        tmpNode = addNode(tmpNode, nRow, nCol, antN[i].id());
                    }
                }
            }
        }
        return tmpNode;
    }

    /**
     * Find all anti nodes associated with each antenna by starting with
     * the first then checking for all other same ID then moving to next.
     * @param antN array of anti nodes.
     * @return
     */
    private static Node24[] findAntiNodes2(Node24[] antN){
        Node24[] tmpNode = new Node24[0];
        int rLmt = fileInfo.length;
        int cLmt = fileInfo[0].length();
        int dRow, dCol, nRow, nCol;
        int cnt = 0;
        int chkRange;
        for(int i = 0; i < antN.length - 1; i++){
            for(int j = i + 1; j < antN.length; j++){
                if(antN[i].id() == antN[j].id()){
                    dRow = antN[j].r() - antN[i].r();
                    dCol = antN[j].c() - antN[i].c();
                    cnt = 0;
                    do{
                        chkRange = 0;
                        nRow = antN[i].r() - cnt * dRow;
                        nCol = antN[i].c() - cnt * dCol;
                        if(nRow > -1 && nRow < rLmt && nCol > -1 && nCol < cLmt){
                            tmpNode = addNode(tmpNode, nRow, nCol, antN[i].id());
                            chkRange++;  
                        }
                        nRow = antN[j].r() + cnt * dRow;
                        nCol = antN[j].c() + cnt * dCol;
                        if(nRow > -1 && nRow < rLmt && nCol > -1 && nCol < cLmt){
                            tmpNode = addNode(tmpNode, nRow, nCol, antN[i].id());
                            chkRange++;
                        }
                        cnt++;
                    }while(chkRange > 0);
                }
            }
        }
        return tmpNode;
    }

    /**
     * Add a node to the array with info passed, if it doesn't already exist.
     * @param tmpNode
     * @param nRow
     * @param nCol
     * @param id
     * @return new array if it doesn't exist else passed array.
     */
    private static Node24[] addNode(Node24[] tmpNode, int nRow, int nCol, char id){
        if(chkDup(tmpNode, nRow, nCol)) return tmpNode;
        tmpNode = Arrays.copyOf(tmpNode, tmpNode.length + 1);
        tmpNode[tmpNode.length - 1] = new Node24(nRow, nCol, id);
        return tmpNode;
    }

    /**
     * Check if row/col already exist.
     * @param chkNode Array of nodes to check afainst
     * @param r row index
     * @param c col index
     * @return true if R/C already exists in chkNode 
     */
    private static boolean chkDup(Node24[] chkNode, int r, int c){
        for(Node24 n : chkNode){
            if(n.r() == r && n.c() == c) return true;
        }
        return false;
    }

    /**
     * For troubleshooting.  Map found anto nodes.
     * @param aNode
     * @param rows
     * @param col
     * @return
     */
    private static String[] mapANode(Node24[] aNode, int rows, int col){
        String[] mapNode = new String[rows];
        for(int i = 0; i < rows; i++) mapNode[i] = ".".repeat(col);

        for(Node24 n : aNode){
            mapNode[n.r()] = mapNode[n.r()].substring(0, n.c()) + "#"
                           + mapNode[n.r()].substring(n.c() + 1);
        }
        return mapNode;
    }

    /**
     * For troubleshooting.  Add antenna nodes to existing map
     * @param mapIn
     * @param aNode
     * @param rows
     * @param col
     * @return
     */
    private static String[] addNode(String[] mapIn, Node24[] aNode, int rows, int col){
        String[] mapNode = Arrays.copyOf(mapIn, rows);

        for(Node24 n : aNode){
            mapNode[n.r()] = mapNode[n.r()].substring(0, n.c()) + n.id()
                           + mapNode[n.r()].substring(n.c() + 1);
        }
        return mapNode;
    }
}