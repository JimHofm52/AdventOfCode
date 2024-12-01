package type;

public class City {
    private String primeCity;
    private String secondCity;
    private int dist;
    private static String[] tmp;
    /**
     * Save 1st City, 2nd city and distance.
     * London to Dublin = 464
     * @param cityInfo
     */
    public City(String cityInfo){
        tmp = cityInfo.split("\\s");
        primeCity = tmp[0];
        secondCity = tmp[2];
        dist = Integer.parseInt(tmp[4]);
    }

    public String getPrime(){ return primeCity; }
    public String getSecond(){ return secondCity; }
    public int getDist(){ return dist; }
}