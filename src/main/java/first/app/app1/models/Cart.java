package first.app.app1.models;

import java.util.HashMap;
import java.util.List;

public class Cart {
    private HashMap<Integer, CartItem> foodList;

    public Cart() {
        this.foodList = new HashMap<>(3);
    }

    public HashMap<Integer, CartItem> getFoodList() {
        return foodList;
    }

    public void setFoodList(HashMap<Integer, CartItem> foodList) {
        this.foodList = foodList;
    }
}
