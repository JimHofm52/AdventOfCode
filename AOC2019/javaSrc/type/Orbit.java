package type;

/** Type Orbit2 */
public class Orbit{
    public String ctr;  //Center of this orbit
    public String orb;  //This Planet
    public int ctrIdx;  //Index to ctr to speed up counting
    public int cnt;     //cnt orbiting this planet
    
    public Orbit(){
        this.ctr = "";
        this.orb = "";
        this.ctrIdx = 0;
        this.cnt = 0;
    }
}
