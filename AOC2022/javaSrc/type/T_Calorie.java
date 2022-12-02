package type;

import java.util.Arrays;

/**
 * Type to hold an array of item calories and their total.
 */
public class T_Calorie {
    public int[] item;
    public int total = 0;

    public T_Calorie(){
        this.item = new int[0];
        this.total = 0;
    }

    /**
     * Add the item calories to the array and to the total. 
     * @param cal Calorie value to be added.
     */
    public void addItem(int cal){
        total += cal;
        item = Arrays.copyOf(item, item.length + 1);
        item[item.length - 1] = cal;
    }
}
