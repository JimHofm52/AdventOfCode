package type;

public class Design {
    private int id;
    private String designID;
    private int leftCorner;
    private int topCorner;
    private int width;
    private int length;
    private boolean overlap = false;

    public Design(String designData){
        designID = designData;  //Ex.  #1 @ 338,764: 20x24

        int beg = 1;
        int end = designID.indexOf(' ');
        id = Integer.parseInt(designID.substring(beg, end));

        beg = designID.indexOf('@') + 2;
        end = designID.indexOf(',');
        leftCorner = Integer.parseInt(designID.substring(beg, end));
        beg = end + 1;
        end = designID.indexOf(':');
        topCorner = Integer.parseInt(designID.substring(beg, end));

        beg = end + 2;
        end = designID.indexOf('x');
        width = Integer.parseInt(designID.substring(beg, end));
        beg = end + 1;
        length = Integer.parseInt(designID.substring(beg));
    }

    public int getID(){ return id; }
    public int getLeft(){ return leftCorner; }
    public int getTop(){ return topCorner; }
    public int getWidth(){ return width; }
    public int getLength(){ return length; }

    public void setOverlap(){ overlap = true; }
    public boolean getOverlap(){ return overlap; }
}
