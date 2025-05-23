package days23;

import java.io.IOException;

import type.Node;
import util.ReadWriteFiles;

public class Day08 {
    private static String fileInfo[];
    private static int len;
    private static int[] lrSeq;        //Array of left/right seq, 0=Left 1=Right
    private static Node[] node;

    /** Constructor, not needed but used for standards. */
    private Day08(){}

    public static void update() throws IOException {
        String fNum = "08"; //Part1- 12643   Part2- 13133452426987 8281151997014995039 Hi 48823243223 Lo
        // String fNum = "081";//Part1- 2   Part2- na
        // String fNum = "082";//Part1- 6   Part2- na
        // String fNum = "083";//Part1- na   Part2- 6
        fileInfo = ReadWriteFiles.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        parceInput();

        question1();    //Confirmed: 08- 12643   081- 2    082- 6
        question2();    //Confirmed: 08- 13133452426987 8281151997014995039 Hi 48823243223 Lo 083- 6
    }

    /**
     * Question 1: Starting at AAA, follow the right/left instructions. 
     * How many steps are required to reach ZZZ?
     */
    private static void question1() {
        long tmp = cntMovesToZZZ();
        //Track ,  Confirmed: 08- 12643   081- 2   082- 6
        System.out.println("\nPart 1: Moves to get to ZZZ: " + tmp);
    }
    
    /**
     * Question 2: Simultaneously start on every node that ends with A.  
     * How many steps does it take before you're only on nodes that end with Z?
     */
    private static void question2() {
        long tmp = calcMovesToAllxxZ();
        //Track ,  Confirmed: 08- 13133452426987 8281151997014995039 Hi 48823243223 Lo 083- 6
        System.out.println("\nPart 2: Simultaneous Moves to get to all Z: " + tmp);
    }

    /**
     * Parce the fileInfo input to:
     * <p>1. Left/Right array of L(0) or R(1) directions.
     * <p>2. Create array of Nodes & initialize
     * <p>3. Convert nodeID & dirID's (Strings) to nodeIdx & dirIdx (int)
     */
    private static void parceInput(){
        //Get left/right seq, RL | LLR | ,,, first line.
        lrSeq = new int[fileInfo[0].length()]; //dimension array (length of first line)
        for(int i = 0; i < lrSeq.length; i++){  //For each char if 'R' then save 1 else save 0
            lrSeq[i] = fileInfo[0].charAt(i) == 'R' ? 1 : 0;   //L=0; R=1
        }

        //Read each line (line 3 on) as a node.  Save nodeID, leftId & rightID (Strings)
        node = new Node[len - 2];       //Create an array of Nodes
        for(int i = 2; i < len; i++){   //For each node (line) in fileInfo
            node[i - 2] = new Node(fileInfo[i]);    //Initialze a Node
        }

        for(int i = 0; i < node.length; i++){               //For each node
            node[i].dirIdx[0] = getNodeIdxFor(node[i].dirID[0]);    //convert left dirID (String) to node index
            node[i].dirIdx[1] = getNodeIdxFor(node[i].dirID[1]);    //convert right dirID (String) to node index
        }
    }

    /**
     * Get the index for the string nodeID.
     * @param srch nodeID to find
     * @return the index for srch
     */
    private static int getNodeIdxFor(String srch){
        for(int i = 0; i < node.length; i++) if(srch.equals(node[i].nodeID)) return i;
        return -1;
    }

    /**
     * Count the move from "AAA" to "ZZZ"
     * @return moves
     */
    private static long cntMovesToZZZ(){
        long moves = 0;
        int actNode = getNodeIdxFor("AAA");
        int actLR = 0;
        int lrLen = lrSeq.length;

        do{
            actNode = node[actNode].dirIdx[lrSeq[actLR++]];
            moves++;
            actLR %= lrLen;
        }while(!node[actNode].nodeID.equals("ZZZ"));

        return moves;
    }

    /**
     * Count the total moves from any mode ending in 'A' 
     * to all nodes ending in 'Z' simultaniously, AT THE SAME TIME.
     * <p>Find the LCM, least common multiplier, find the largest number to speed 
     * up the process.  Multiple the max move then modulo by each move and 
     * see if they ALL divide evenly, equals 0.
     * @return Simultanious moves
     */
    private static long calcMovesToAllxxZ(){
        long moves = 0;     //cheat :) start at 618000000
        int[] aNode = findAllIdxForxxA();       //Array to hold active node indexes
        int[] zNodes = new int[aNode.length];   //Array for info
        int[] aMoves = cntMovesToxxZ(aNode, zNodes);    //Array of moves 'A' to 'Z'

        boolean allZ;               //Used to check all nodes end in 'Z'

        int maxMove = 0;
        for(int i = 0; i < aMoves.length; i++) maxMove = Math.max(maxMove, aMoves[i]);

        do{
            allZ = true;
            moves++;
            for(int i = 0; i < aNode.length; i++){        //For each active node
                allZ &= (moves * maxMove) % aMoves[i] == 0;
                if(!allZ) break;    //Just to speed things up
            }
        }while(!allZ);  //Repeat wnile ALL nodes are not end with 'Z'
        pntInfoPart2(aNode, zNodes, aMoves);
        return moves * maxMove;
    }

    /**
     * Find all the nodes that end in 'A'.  Starting nodes for part 2.
     * <p>And cnt the moves to get from here "xxA" to "xxZ" for those nodes.  
     * Save in node[i].xxAPath Length
     * @return an array of nodes that end in 'A'
     */
    private static int[] findAllIdxForxxA(){
        int cnt = 0;
        for(Node n : node) if(n.nodeID.charAt(2) == 'A') cnt++; //Cnt 'A' nodes
        int[] aNode = new int[cnt];     //Create an array

        //Fill in the array with the node index.
        cnt = 0;
        for(int i = 0; i < node.length; i++){
            if(node[i].nodeID.charAt(2) == 'A'){
                aNode[cnt++] = i; 
            }
        }

        return aNode;
    }
 
    /**
     * 
     * @param aNodeIdxs
     * @param zNodeIdxs
     * @return
     */
    private static int[] cntMovesToxxZ(int[] aNodeIdxs, int[] zNodeIdxs){
        int actLR;
        int actNode;
        int[] aMoves = new int[aNodeIdxs.length];
        for(int i = 0; i < aNodeIdxs.length; i++){
            actNode = aNodeIdxs[i];
            aMoves[i] = 0;
            actLR = 0;
            do{
                actNode = node[actNode].dirIdx[lrSeq[actLR]];
                aMoves[i]++;                        //increment for this path only
                actLR = ++actLR % lrSeq.length;     //Do next L/R then repeat L/R list
           }while(node[actNode].nodeID.charAt(2) != 'Z');
           zNodeIdxs[i] = actNode;
        }
        return aMoves;
    }
 
    private static void pntInfoPart2(int[] aNodeIdxs, int[] zNodeIdxs, int[] aMoves){
        for(int i = 0; i < aNodeIdxs.length; i++){
            System.out.println("Starting at: " + node[aNodeIdxs[i]].nodeID + ", node index: " + aNodeIdxs[i]);
            System.out.println("\tfound: " + aMoves[i]);
            System.out.println("\tto Node: " + node[zNodeIdxs[i]].nodeID + ", node index: " + zNodeIdxs[i]);
        }
    }
 
}