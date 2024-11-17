package type;

/**
 * The pipes are arranged in a two-dimensional grid of tiles:
 *                                                               if pipe is: right  left  above  below
 * | is a vertical pipe north and south if on top or bottom (go N | S).       n/a | n/a  | 1,0  | -1,0
 * - is a horizontal pipe east and west if left or right (go E | W).          0,1 | 0,-1 | n/a  | n/a
 * L is a 90-degree connecting north and east (go NW [left] | SE [below]).    n/a | 1,-1 | n/a  | -1,1
 * J is a 90-degree connecting north and west (go NE [right] | SW [below]).   1,1 | n/a  | n/a  |-1,-1
 * 7 is a 90-degree connecting south and west (go NW [above] | SE [right]).  -1,1 | n/a  | 1,-1 | n/a
 * F is a 90-degree connecting south and east (go NE [above] | SW [left]).    n/a |-1,-1 | 1,1  | n/a
 * . is ground; there is no pipe in this tile      (go no where).             n/a | n/a  | n/a  | n/a
 * S is the starting position of the animal; there is a pipe on this tile, 
 * but your sketch doesn't show what shape the pipe has.
 */

public class Pipe {
    //if pipe is: right[0] left[1] above[2] below[3] move north(1), south(-1) and east(1), west(-1)
    // int[][] n = {{ 0,0 },{ 0,0 },{ 1,0 },{-1,0 }};
    // int[][] e = {{ 0,1 },{ 0,-1},{ 0,0 },{ 0,0 }};
    // int[][] l = {{ 0,0 },{ 1,-1},{ 0,0 },{-1,1 }};
    // int[][] j = {{ 1,1 },{ 0,0 },{ 0,0 },{-1,-1}};
    // int[][] s = {{-1,1 },{ 0,0 },{ 1,-1},{ 0,0 }};
    // int[][] f = {{ 0,0 },{-1,-1},{ 1,1 },{ 0,0 }};
    // int[][] g = {{ 0,0 },{ 0,0 },{ 0,0 },{ 0,0 }};

    /**
     * Movement thru the pipe, XY.  X[0] is East/West, +X is east, -X is west.
     * <p>Y[1] is North/South, +Y is South, -Y is North.
     * <p>[0] is pipe connection type. '|', '-', 'L', 'J' '7', 'F' & '.'
     * <p>[1] is pipe orientation to present loc: right(0) left(1) above(2) below(3)
     * <p>[2]Move X, Move Y
     */
    public static final // right   left    above   below
    int[][][] pipe =    {{{ 0,0 },{ 0,0 },{ 0,-2},{ 0,2 }},    // '|' North & South
                         {{ 2,0 },{-2,0 },{ 0,0 },{ 0,0 }},    // '-' East & West
                         {{ 0,0 },{-1,-1},{ 0,0 },{ 1,1 }},    // 'L' North & East
                         {{ 1,-1},{ 0,0 },{ 0,0 },{-1,1 }},    // 'J' North & West
                         {{ 1,1 },{ 0,0 },{-1,-1},{ 0,0 }},    // '7' South & West
                         {{ 0,0 },{-1,1 },{ 1,-1},{ 0,0 }},    // 'F' South & East
                         {{ 0,0 },{ 0,0 },{ 0,0 },{ 0,0 }},    // '.' Ground
                         {{ 1,-5},{-1,-5},{-5,-1},{-5,1 }}};   // 'S' Start (End)

    /**
     * Allowable direction to chk for next move.
     * <p>Bit map, 1111 Left(8), Right(4), Up(2), Dn(1)
     * <p>                          |  -   L   J  7  F  .   S  
     */
    public static int[] pipeChk = { 3, 12, 6, 10, 9, 5, 0, 15};

    /**
     * List of possible pipe types, follows Pipe,pipe.
     */
    public static final String pType = "|-LJ7F.S";

    /**
     * 
     * @param ch pipe type, '|'', '-', 'L', 'J', '7' F', and '.'
     * @param dir orentation of pipe to present location: right[0] left[1] above[2] below[3]
     * @return XY move. if invalid ch or dir returns [-10,-10]
     */
    public static int[] getPipeMove(char ch, int dir){
        int move[] = {-10, -10};    //Initialize with illegal move
        int pIdx = pType.indexOf(ch);
        if(pIdx < 0 || (dir < 0 || dir > 3)) return move;

        return pipe[pIdx][dir];
    }
    
    public static int getMovesToChk(char ch){
        return pipeChk[pType.indexOf(ch)];
    }
    
}
