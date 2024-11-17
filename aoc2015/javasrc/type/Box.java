package type;
/**
 * Ex. 29x13x26
 */
public class Box {
    private String strIn;
    private int length;
    private int width;
    private int height;
    private int areaSideLxW;
    private int areaSideWxH;
    private int areaSideLxH;
    private int minSide;
    private int minPerim;
    private int minAreaSide;
    private int totalArea;
    private int totalVol;


    private static int beg;
    private static int end;

    /**
     * Constructor to take in string (Ex. 29x13x26) and parce
     * to length, width & height.
     * 
     * @param dim String of Ex. 29x13x26, lxwxh.
     */
    public Box(String dim){
        strIn = dim;
        beg = 0;
        end = dim.indexOf('x');
        length = Integer.parseInt(dim.substring(beg, end));
        beg = end + 1;
        end = dim.indexOf('x', beg);
        width = Integer.parseInt(dim.substring(beg, end));
        beg = end + 1;
        height = Integer.parseInt(dim.substring(beg));

        areaSideLxW = 2 * length * width;
        areaSideLxH = 2 * length * height;
        areaSideWxH = 2 * width * height;

        minSide = Math.min(Math.min(length, width), height);

        minAreaSide = (Math.min(Math.min(areaSideLxW, areaSideLxH), areaSideWxH)) / 2;

        minPerim = 2 * (minSide + minAreaSide / minSide);

        totalArea = areaSideLxW + areaSideLxH + areaSideWxH + minAreaSide;

        totalVol = length * width * height;
    }

    public int getTotalArea(){ return totalArea; }
    public int getMinPerim(){ return minPerim; }
    public int getTotalVol(){ return totalVol; }

}
