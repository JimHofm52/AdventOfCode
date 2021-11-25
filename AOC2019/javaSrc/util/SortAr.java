package util;

public class SortAr {

    public static void Sort(int inAr[]){
        int ndx = 0;
        int iTmp = 0;
        boolean done = false;

        do{
            done = true;
            for(ndx = 0; ndx < inAr.length - 1; ndx++ ){
                if(inAr[ndx] > inAr[ndx + 1]){
                    iTmp = inAr[ndx + 1];
                    inAr[ndx + 1] = inAr[ndx];
                    inAr[ndx] = iTmp;
                    done = false;
                }
            }
        }while(!done);
    }

}