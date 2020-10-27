package first.app.app1.data.out;

import first.app.app1.models.CartItem;
import first.app.app1.models.Food;
import first.app.app1.models.Restaurant;
import first.app.app1.models.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RestaurantDto {

    HashMap<String, String> users;
    HashMap<Integer, CartItem> foodMap;
    Restaurant restaurant;
    boolean ordered;

    boolean delivered;

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public RestaurantDto(Restaurant restaurant) {
        this.restaurant = restaurant;
        users = new HashMap<>();
        foodMap = new HashMap<>();
    }

    public void recordItem(User user, Food food, int count)
    {
        putUser(user);
        putFood(food, count);
    }

    private void putUser(User user)
    {
        if(!users.containsKey(user.getUsername()))
        {
            users.put(user.getUsername(), user.getName() + " " + user.getSurname());
        }
    }

    private void putFood(Food food, int count)
    {
        if(!foodMap.containsKey(food.getId()))
        {
            foodMap.put(food.getId(), new CartItem(food));
        }
        foodMap.get(food.getId()).incrementCount(count);
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public Collection<String> getUsers() {
        return users.values();
    }
    public Set<String> getUsersUsername() {
        return users.keySet();
    }

    public Collection<CartItem> getFoodList() {
        return foodMap.values();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public double getTotal()
    {
        double sum = 0;
        for(CartItem cartItem: foodMap.values())
        {
            sum += cartItem.getFood().getPrice() * cartItem.getCount();
        }
        return sum;
    }
}

