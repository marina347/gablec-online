package first.app.app1.models;

import com.google.gson.annotations.Expose;

public class CartItem {
    @Expose
    private Food food;
    @Expose
    private int count;

    public CartItem() {
    }

    public CartItem(Food food) {
        this.food = food;
    }

    public CartItem(Food food, int count) {
        this.food = food;
        this.count = count;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount()
    {
        this.count++;
    }
    public void decrementCount()
    {
        this.count--;
    }



    public void incrementCount(int quantity)
    {
        this.count+=quantity;
    }
}
