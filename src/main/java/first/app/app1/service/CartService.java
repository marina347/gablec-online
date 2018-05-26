package first.app.app1.service;

import first.app.app1.models.Cart;
import first.app.app1.models.Food;

import java.util.List;

public interface CartService {
    Cart findCartSession(final String username);

    Cart getCart(final String username);

    boolean destroyCartSession(final String username);

    void putItemIntoCart(final String username, int foodId, int count);

    double calculateCartTotal(Cart cart);
}