package days22;

import java.io.IOException;

import util.ReadInput;

public class Day08 {
    private static String fileInfo[];
    private static int len;
    private static int width;
    private static int tree[][][];

    /** Constructor, not needed but used for standards. */
    private Day08(){}

    public static void update() throws IOException {
        String fNum = "08"; //Part1- 1669   Part2- 331344
        // String fNum = "081";//Part1- 21   Part2- 8
        fileInfo = ReadInput.getInputStr(fNum);   //Get input in an array for 1
        len = fileInfo.length;          //Length of input array
        width = fileInfo[0].length();   //Width of input array
        tree = new int[len][width][3]; //Heigth[0] Q1-RLUD[1] ~ 8421, Q2-trees seen[2]

        parceInput(fileInfo, tree);

        question1();    //Confirmed: 08- 1669   081- 21
        question2();    //Confirmed: 08- 331344   081- 8
    }

    /**
     * Question 1: Count the number of trees visable from the outside.
     */
    private static void question1() {
        int treeViewable = len * width;
        for(int r = 1; r < len - 1; r++){
            for(int c = 1; c < fileInfo[0].length() - 1; c++){
                chkViewDir(r, c);   //Chk all dir RLUD for GTE trees. RLUD ~ 8421
                if(tree[r][c][1] == 15) treeViewable--;
            }
        }
        //Track ,  Confirmed: 08- 1669   081- 21
        System.out.println("\nPart 1: Trees viewable: " + treeViewable);
    }

    /**
     * Count the number of trees visable from the outside for a tree in 4 direction.
     * Global array tree used to get height of tree [0] and return value [1].
     * @param rv row of tree, height value
     * @param cv col of tree, height value
     */
    private static void chkViewDir(int rv, int cv){
        int val = tree[rv][cv][0];
        // Chk Right view
        for(int c = cv + 1; c < fileInfo[0].length(); c++){
            if(val <= tree[rv][c][0]){
                tree[rv][cv][1] = 8;
                break;
            }
        }
        // Chk Left view
        for(int c = cv - 1; c >= 0; c--){
            if(val <= tree[rv][c][0]){
                tree[rv][cv][1] += 4;
                break;
            }
        }
        // Chk Up view
        for(int r = rv - 1; r >= 0; r--){
            if(val <= tree[r][cv][0]){
                tree[rv][cv][1] += 2;
                break;
            }
        }
        // Chk Down view
        for(int r = rv + 1; r < fileInfo[0].length(); r++){
            if(val <= tree[r][cv][0]){
                tree[rv][cv][1] += 1;
                break;
            }
        }
    }

    /*-------------------------  Part 2  -------------------------------- */
    /**
     * Question 2: Count the number of trees seen from another tree 
     * before another tree blocks your view.
     */
    private static void question2() {
        int treesSeeable = 0;
        for(int r = 1; r < len - 1; r++){
            for(int c = 1; c < fileInfo[0].length() - 1; c++){
                chkSeeDir(r, c);   //Chk all dir RLUD for GTE trees. RLUD ~ 8421
                if(tree[r][c][2] > treesSeeable) treesSeeable = tree[r][c][2];
            }
        }
        //Track ,  Confirmed: 08- 331344   081- 8
        System.out.println("\nPart 2: Most trees seen: " + treesSeeable);
    }

    /**
     * Count the number of trees seen from another tree before another tree blocks your view.
     * Global array tree used to get height of tree [0] and return value [2].
     * @param rv row of tree, height value
     * @param cv col of tree, height value
     */
    private static void chkSeeDir(int rv, int cv){
        int val = tree[rv][cv][0];
        int[] cntDir = new int[4];
        for(int i : cntDir) i = 0;
        // Chk Right
        for(int c = cv + 1; c < fileInfo[0].length(); c++){
            cntDir[0]++;
            if(val <= tree[rv][c][0]) break;
        }
        // Chk Left
        for(int c = cv - 1; c >= 0; c--){
            cntDir[1]++;
            if(val <= tree[rv][c][0]) break;
        }
        // Chk Up
        for(int r = rv - 1; r >= 0; r--){
            cntDir[2]++;
            if(val <= tree[r][cv][0]) break;
        }
        // Chk Down
        for(int r = rv + 1; r < fileInfo[0].length(); r++){
            cntDir[3]++;
            if(val <= tree[r][cv][0]) break;
        }
        if(rv == 3 && cv == 2){
            int x = 0;
        }
        val = 1;
        for(int tot : cntDir) val *= tot;
        tree[rv][cv][2] = val;
    }

    /**
     * Parce the input file for tree height [0].
     * @param inp
     * @param tInfo
     */
    private static void parceInput(String[] inp, int[][][] tInfo){
        for(int r = 0; r < len; r++){
            for(int c = 0; c < width; c++){
                tInfo[r][c][0] = fileInfo[r].charAt(c) - 48; //Heigth
            }
        }
    }
}