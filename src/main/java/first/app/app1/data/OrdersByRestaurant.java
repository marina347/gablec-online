package first.app.app1.data;

import first.app.app1.models.OrderItem;
import first.app.app1.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrdersByRestaurant {
    Restaurant restaurant;
    List<OrderItem> orderItems;

    public OrdersByRestaurant() {
        orderItems = new ArrayList<>();
    }

    public OrdersByRestaurant(Restaurant restaurant, List<OrderItem> orderItems) {
        this.restaurant = restaurant;
        this.orderItems = orderItems;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
