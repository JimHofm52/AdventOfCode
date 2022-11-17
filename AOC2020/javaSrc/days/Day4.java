package javasrc.days;

import java.io.IOException;
import javasrc.util.ReadInput;

public class Day4 {

    private static String[] fileInfo;
    private static int len;
    private static String[] ppKey = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"};

    /** Constructor, no action.  Standard only. */
    private Day4(){}

    public static void update() throws IOException {
        fileInfo = ReadInput.getInputStrSpS(4); //Get input in an array for Day4
        len = fileInfo.length;                  //as record per line.  Total pswds.
        System.out.println();
        question1();                            //Puzzle answer was 208.
        question2();                            //Puzzle answer was 167.
    }

    /**
     * Count how many passports meet criteria.  cid is optional.
     * byr (Birth Year)     iyr (Issue Year)    eyr (Expiration Year)
     * hgt (Height)         hcl (Hair Color)    ecl (Eye Color)
     * pid (Passport ID)    cid (Country ID [optional])
     */
    private static void question1(){
        displayInfo(1, chkPP1());
    }

    /**
     * Count how many passports meet criteria & data range.  cid is optional.
     * <p>All numberic values are inclusive
     * <p>byr (Birth Year) 1920 - 2002.
     * <p>iyr (Issue Year) 2010 - 2020.
     * <p>eyr (Expir Year) 2020 - 2030.
     * <p>hgt (Height) - a number followed by either cm (150 - 193) or in (59 - 76):
     * <p>hcl (Hair Color) - # followed 6 characters 0-9 or a-f.
     * <p>ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
     * <p>pid (Passport ID) - a nine-digit number, including leading zeroes.
     * <p>cid (Country ID) - ignored, missing or not.
     */
    private static void question2(){
        displayInfo(2, chkPP2());
    }

    /**
     * Check passport for criteria.  Has all keys.
     */
    private static int chkPP1(){
        int cntPP = 0;
        int cntTag = 0;
        for(String s : fileInfo){
            for(String tag : ppKey){    //chk against list of criteria tags
                if( s.contains(tag) && tag != "cid") cntTag++;  //ignore cid
            }
            if(cntTag > 6) cntPP++;     //OK PP if matched at least 7 criteria
            cntTag = 0;
        }
        return cntPP;
    }

    /**
     * Check passport for criteria and data.  Has keys and values fits criteria.
     */
    private static int chkPP2(){
        String keyVal = "";
        int keyValOK;  //7 bits, ignore cid bit 7
        int ppOK = 0;
        int end = 0;
        for(String rcd : fileInfo){     //Get a record
            keyValOK = 0b10000000;      //7 bits, ignore cid bit 7
            do{
                end = rcd.indexOf(" ");     //Chk for end of key:palue pair
                if(end > 0){                //Not at EOL (end = -1)
                    keyVal = rcd.substring(0, end); //Get next key:value pair
                    rcd = rcd.substring(end + 1);   //remove from rcd
                }else{
                    keyVal = rcd;                   //Last pair
                }
                keyValOK += chkKeyVal(keyVal);  //add bit for key called if val OK
            }while(end > 0);    //not at EOL(-1)
            if(keyValOK == 0b11111111) ppOK++;  //All bits set?  PP OK.
        }
        return ppOK;    //return cnt of OK passports
    }

    /**
     * Check validity of key:value pair.
     * <p>key = {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"};
     * @param keyVal
     * @return
     */
    private static int chkKeyVal( String keyVal){
        String key = keyVal.substring(0, 3);
        String val = keyVal.substring(4);
        int i = 0;
        for(; i < ppKey.length; i++) if(key.equals(ppKey[i])) break; //convert ppTag to int.
        switch(i){
            case 0:     //byr (Birth Year) 1920 - 2002 inclusive.
            i = Integer.parseInt(val);
            return (i >= 1920 && i <= 2002) ? 1 : 0;
            case 1:     //iyr (Issue Year) 2010 - 2020 inclusive.
            i = Integer.parseInt(val);
            return (i >= 2010 && i <= 2020) ? 2 : 0;
            case 2:     //eyr (Expir Year) 2020 - 2030 inclusive.
            i = Integer.parseInt(val);
            return (i >= 2020 && i <= 2030) ? 4 : 0;
            case 3:     //hgt (Height) - a number followed by either cm (150 - 193) or in (59 - 76):
            if(val.length() < 4) return 0;  //78, 150, 
            return chkHgt(val) ? 8 : 0;
            case 4:     //hcl (Hair Color) - # followed 6 characters 0-9 or a-f.
            return chkHcl(val) ? 16 : 0;
            case 5:     //ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            return (val.contains("amb") || val.contains("blu") || val.contains("brn") || val.contains("gry") ||
                    val.contains("grn") || val.contains("hzl") || val.contains("oth")) ? 32 : 0;
            case 6:     //pid (Passport ID) - a nine-digit number, including leading zeroes.
            i = 0;
            for(int c = 0; c < val.length(); c++){
                if(Character.isDigit(val.charAt(c))) i++;
            }
            return i == 9 ? 64 : 0;
            case 7:     //cid (Country ID) - ignored, missing or not.
            return false ? 128 : 0;
            default:
            System.out.println("Invalid key - " + key + "\tValue - " + val);
            return 0;
        }
    }

    /**
     * hcl (Hair Color) - # followed 6 characters 0-9 or a-f.
     * @param val
     * @return
     */
    private static boolean chkHcl(String val){
        char ch;
        //Quick check that it has # + 6 chars and starts with a '#'
        if(!((val.length() == 7) && (val.charAt(0) == '#'))) return false;
        //Check if last 6 chars are NOT 0-9 or a-f
        for(int c = 1; c < val.length(); c++){
            ch = val.charAt(c);
            if(!(Character.isDigit(ch) || (ch >='a' && ch <= 'f' ))) return false;
        }
        return true;    //else return true.
    }

    /**
     * hgt (Height) - a number followed by either cm (150 - 193) or in (59 - 76):
     * @param val
     * @return
     */
    private static boolean chkHgt(String val){
        int hgt = 0;
        String units = "";
        if(Character.isDigit(val.charAt(2))){   //true then cm else in
            hgt = Integer.parseInt(val.substring(0, 3));    //cm
            units = val.substring(3);
            return (hgt >= 150 && hgt <= 193 && units.equals("cm"));
        }else{
            hgt = Integer.parseInt(val.substring(0, 2));    //in
            units = val.substring(2);
            return (hgt >= 59 && hgt <= 76 && units.equals("in"));
        }
    }

   /**************************************************************************
     * Print the Answsers
     */
    private static void displayInfo(int num, long cnt){
        System.out.println("OK passports, puzze " + num + "\tValue - " + cnt);
    }
}