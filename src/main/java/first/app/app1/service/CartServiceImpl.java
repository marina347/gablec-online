package first.app.app1.service;

import first.app.app1.daos.FoodDao;
import first.app.app1.models.Cart;
import first.app.app1.models.CartItem;
import first.app.app1.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CartServiceImpl implements CartService {

    @Autowired
    FoodDao foodService;

    private static Map<String, Cart> cartByUsername;

    public CartServiceImpl()
    {
        if (cartByUsername == null)
        {
            cartByUsername = new ConcurrentHashMap<>();
        }
    }

    @Override
    public Cart findCartSession(String username) {
        return cartByUsername.get(username);
    }

    @Override
    public Cart getCart(String username) {
        Cart cart;
        if(cartByUsername.containsKey(username))
        {
            cart = cartByUsername.get(username);
        }
        else
        {
            cart = new Cart();
            cartByUsername.put(username, cart);
        }
        return cart;
    }


    @Override
    public void putItemIntoCart(String username, int foodId, int count)
    {
        Cart cart = getCart(username);
        HashMap<Integer, CartItem> items = cart.getFoodList();
        if(items.containsKey(foodId))
        {
            items.get(foodId).incrementCount(count);
        }
        else
        {
            Food food = foodService.findById(foodId);
            CartItem cartItem= new CartItem(food, count);
            items.put(food.getId(), cartItem);
        }
    }

    public void incrementItemCount(String username, int foodId) throws Exception
    {
        getCart(username).getFoodList().get(foodId).incrementCount();
    }

    public void decrementItemCount(String username, int foodId) throws Exception
    {
        getCart(username).getFoodList().get(foodId).decrementCount();
    }

    public void removeItemFromCart(String username, int foodId) throws Exception
    {
        getCart(username).getFoodList().remove(foodId);
    }


    @Override
    public boolean destroyCartSession(String username) {
        if (cartByUsername.containsKey(username))
        {
            cartByUsername.remove(username);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public double calculateCartTotal(Cart cart)
    {
        double sum = 0;
        for(CartItem cartItem: cart.getFoodList().values())
        {
            sum += cartItem.getFood().getPrice()*cartItem.getCount();
        }
        return sum;
    }
}
