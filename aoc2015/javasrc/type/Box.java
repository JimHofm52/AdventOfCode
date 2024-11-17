package type;
/**
 * Every present is a box in the shape of a perfect right rectangular prism.
 * Ex. 29x13x26 (lxwxh)
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
        String[] valStr = strIn.split("x");
        length = Integer.parseInt(valStr[0]);
        width = Integer.parseInt(valStr[1]);
        height = Integer.parseInt(valStr[2]);

        areaSideLxW = 2 * length * width;
        areaSideLxH = 2 * length * height;
        areaSideWxH = 2 * width * height;

        minSide = Math.min(Math.min(length, width), height);

        minAreaSide = (Math.min(Math.min(areaSideLxW, areaSideLxH), areaSideWxH)) / 2;

        minPerim = 2 * (minSide + minAreaSide / minSide);

        totalArea = areaSideLxW + areaSideLxH + areaSideWxH + minAreaSide;

        totalVol = length * width * height;
    }

    /** @return the total surface area of the perfect right rectangular prism. */
    public int getTotalArea(){ return totalArea; }
    /** @return the perimeter of the side with the smallest area. */
    public int getMinPerim(){ return minPerim; }
    /** @return the total volumn of the perfect right rectangular prism. */
    public int getTotalVol(){ return totalVol; }
}
