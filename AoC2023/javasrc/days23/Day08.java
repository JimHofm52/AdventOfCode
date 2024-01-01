package days23;

import java.io.IOException;

import type.Node;
import util.ReadInput;

public class Day08 {
    private static String fileInfo[];
    private static int len;
    private static int[] lrSeq;        //Array of left/right seq, 0=Left 1=Right
    private static Node[] node;    

    /** Constructor, not needed but used for standards. */
    private Day08(){}

    public static void update() throws IOException {
        String fNum = "08"; //Part1- 12643   Part2- ??? 8281151997014995039 Hi 48823243223 Lo
        // String fNum = "081";//Part1- 2   Part2- na
        // String fNum = "082";//Part1- 6   Part2- na
        // String fNum = "083";//Part1- na   Part2- 6
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        parceInput();

        // question1();    //Confirmed: 08- 12643   081- 2    082- 6
        question2();    //Confirmed: 08- ??? 8281151997014995039 Hi 48823243223 Lo 083- 6
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
        long tmp = cntMovesTo_xxZ();
        //Track ,  Confirmed: 08- ??? 8281151997014995039 Hi 48823243223 Lo 083- 6
        System.out.println("\nPart 2: Simultaneous Moves to get to all Z: " + tmp);
    }

    /**
     * Parce the fileInfo input to:
     * <p>1. Left/Right array of L(0) or R(1) directions.
     * <p>2. Create array of Nodes & initialize
     * <p>3. Convert nodeID & dirID's (Strings) to nodeIdx & dirIdx
     */
    private static void parceInput(){
        lrSeq = new int[fileInfo[0].length()]; //Get left/right seq, RL | LLR | ,,,
        for(int i = 0; i < lrSeq.length; i++){
            lrSeq[i] = fileInfo[0].charAt(i) == 'R' ? 1 : 0;   //L=0; R=1
        }

        node = new Node[len - 2];       //Create an array of Nodes
        for(int i = 2; i < len; i++){   //For each node (line) in fileInfo
            node[i - 2] = new Node(fileInfo[i]);    //Initialze a Node
        }

        for(int i = 0; i < node.length; i++){               //For each node
            node[i].dirIdx[0] = getNodeIdxFor(node[i].dirID[0]);    //convert left dirID (String) to node index
            node[i].dirIdx[1] = getNodeIdxFor(node[i].dirID[1]);    //convert right dirID (String) to node index
        }
        int a = 0;
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
     * Count the moves from any mode ending in 'A' 
     * to all nodes ending in 'Z' simultaniously, AT THE SAME TIME.
     * @return Simultanious moves
     */
    private static long cntMovesTo_xxZSave(){
        long moves = 0;
        int[] actNode = findAllIdxFor_xxA();    //Array to hold active node indexes
        int actLR = 0;              //Active L/R index
        int lrLen = lrSeq.length;   //Lemgth of L/R array, L=0, R=1
        boolean allZ;               //Used to check all nodes end in 'Z'

        do{
            allZ = true;
            for(int i = 0; i < actNode.length; i++){        //For each active node
                actNode[i] = node[actNode[i]].dirIdx[lrSeq[actLR]]; //Update to next node using L/R cmd
                allZ &= node[actNode[i]].nodeID.charAt(2) == 'Z';   //Check if ends in 'Z'
            }
            moves++;                    //Increment moves
            actLR = ++actLR % lrLen;    //Do next L/R then repeat L/R list
            // System.out.println(moves);
            // System.out.println(lrSeq[actLR]);
        }while(!allZ);  //Repeat wnile ALL nodes are not end with 'Z'
        // for(int i = 0; i< actNode.length; i++){ 
        //     System.out.println(node[actNode[i]].str);
        // }
        return moves;
    }

    /**
     * Count the moves from any mode ending in 'A' 
     * to all nodes ending in 'Z' simultaniously, AT THE SAME TIME.
     * <p>Count each individual path 'A' to 'Z' then do some math.
     * <p>Product (too Hi)  8,281,151,997,014,995,039
     * <p>Divide each by 269 all primes (too Lo)  48,823,243,223
     * <p> AssUmes the path repeats.
     * TO DO: Need to check.
     * @return
     */
    private static long cntMovesTo_xxZSave2(){
        int[] actNode = findAllIdxFor_xxA();    //Array to hold active node indexes
        long[] moves = new long[actNode.length];//Array for moves for each xxA nodes
        int actLR = 0;
        int lrLen = lrSeq.length;
        boolean allZ;

        for(int i = 0; i < actNode.length; i++){    //For each path 'A'
            actLR = 0;  //Set L/R index to 0
            do{
                actNode[i] = node[actNode[i]].dirIdx[lrSeq[actLR]]; //Update to next node using L/R cmd
                moves[i]++;                 //increment for this path only
                actLR = ++actLR % lrLen;    //Do next L/R then repeat L/R list
            }while(node[actNode[i]].nodeID.charAt(2) != 'Z');
        }

        long tmp = 1L;
        for(int i = 0; i < actNode.length; i++){
            moves[i] = (moves[i] / 269L);  //Try divide by 269, ALL primes.  L/R seq 269. HMMmmm?
            tmp *= moves[i];        //Product of all path individual moves
        }
        return tmp;
    }

    /**
     * Count the moves from any mode ending in 'A' 
     * to all nodes ending in 'Z' simultaniously, AT THE SAME TIME.
     * <p>Count each individual path 'A' to 'Z' then do some math.
     * <p>Product (too Hi)  8,281,151,997,014,995,039
     * <p>Divide each by 269 all primes (too Lo)  48,823,243,223
     * <p>Math is wrong? /1000 = 19,918,080,000,000,000,000,000,000  Nope.
     * <p> AssUmes the path repeats.
     * 
     * Added print to check and end of each path.
     * @return
     */
    private static long cntMovesTo_xxZ(){
        int[] actNode = findAllIdxFor_xxA();    //Array to hold active node indexes
        long[] moves = new long[actNode.length];//Array for moves for each xxA nodes
        int actLR = 0;
        int lrLen = lrSeq.length;
        boolean allZ;
        char chz;
        int cnt;
        long prvMoves;

        for(int i = 0; i < actNode.length; i++){    //For each path 'A'
            actLR = 0;  //Set L/R index to 0
            cnt = 0;
            prvMoves = 0;
            System.out.print("From Node Idx: " + actNode[i] + " " + node[actNode[i]].nodeID + "\t");
            do{
                actNode[i] = node[actNode[i]].dirIdx[lrSeq[actLR]]; //Update to next node using L/R cmd
                moves[i]++;                 //increment for this path only
                actLR = ++actLR % lrLen;    //Do next L/R then repeat L/R list
                chz = node[actNode[i]].nodeID.charAt(2);
                if(chz == 'Z'){
                    System.out.print("To Idx: " + actNode[i] + " " + node[actNode[i]].str);
                    System.out.print("Moves: " + moves[i] + " Prv Moves: " + (moves[i] - prvMoves));
                    System.out.println(" L/R Idx: " + actLR + "\t Cmd: " + lrSeq[actLR]);
                    cnt++;
                    prvMoves = moves[i];
                }
            }while(!((chz == 'Z') && (cnt == 5)));
        }

        long tmp = 1L;
        for(int i = 0; i < actNode.length; i++){
            moves[i] = ((moves[i] + 500L) / 1000L);  //Try divide by 269, ALL primes.  L/R seq 269. HMMmmm?
            tmp *= moves[i];        //Product of all path individual moves
        }
        return tmp;
    }

    /**
     * Find all the nodes that end in 'A'.  Starting nodes for part 2.
     * @return an array of nodes that end in 'A'
     */
    private static int[] findAllIdxFor_xxA(){
        int cnt = 0;
        for(Node n : node) if(n.nodeID.charAt(2) == 'A') cnt++;
        int[] aNode = new int[cnt];

        cnt = 0;
        for(int i = 0; i < node.length; i++){
            if(node[i].nodeID.charAt(2) == 'A'){
                aNode[cnt++] = i; 
            }
        }
        return aNode;
    }
}