package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Refrigerator implements Serializable {
    ArrayList<Food> foodList;

    public Refrigerator() {
        foodList = new ArrayList<>();
    }

    public void add(Food food){
        foodList.add(food);
    }

    // Sortierung nach Lebensmittelname
    public void sortByName() {
        Collections.sort(foodList, new Comparator<Food>() {
            @Override
            public int compare(Food food1, Food food2) {
                return food1.foodName.compareTo(food2.foodName);
            }
        });
    }
    //Sortierung nach Mindesthaltbarkeitsdatum
    public void sortByMHD() {
        Collections.sort(foodList, new Comparator<Food>() {
            @Override
            public int compare(Food food1, Food food2) {
                return food1.bestBeforeDate.compareTo(food2.bestBeforeDate);
            }
        });
    }
}
