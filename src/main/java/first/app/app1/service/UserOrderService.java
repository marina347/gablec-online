package first.app.app1.service;

import first.app.app1.daos.OrderDao;
import first.app.app1.daos.OrderItemDao;
import first.app.app1.data.OrdersByRestaurant;
import first.app.app1.models.Cart;
import first.app.app1.models.OrderItem;
import first.app.app1.models.User;
import first.app.app1.models.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

@Component
public class UserOrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDao orderItemDao;

    public List<UserOrder> getTodayOrders(){
        return orderDao.getOrdersByOrderDate(Calendar.getInstance().getTime());
    }

    public UserOrder getOrderById(int id)
    {
        return orderDao.findOne(id);
    }
    public void saveOrder(UserOrder order){
        orderDao.save(order);
    }

    public Collection<OrdersByRestaurant> getTodayOrdersByRestaurant()
    {
        List<UserOrder> userOrders = getTodayOrders();
        HashMap<Integer, OrdersByRestaurant> ordersByRestaurants = new HashMap<>();
        for (UserOrder userOrder : userOrders)
        {
            List<OrderItem> orderItems = userOrder.getOrderItemList();
            for(OrderItem orderItem : orderItems)
            {
                int orderRestaurantId = orderItem.getFood().getRestaurant().getId();
                if(!ordersByRestaurants.containsKey(orderRestaurantId))
                {
                    OrdersByRestaurant ordersByRestaurant = new OrdersByRestaurant();
                    ordersByRestaurant.setRestaurant(orderItem.getFood().getRestaurant());
                    ordersByRestaurants.put(orderRestaurantId, ordersByRestaurant);

                }
                ordersByRestaurants.get(orderRestaurantId).getOrderItems().add(orderItem);
            }

        }

        return ordersByRestaurants.values();
    }

    public void completeOrder(int orderId){
        //orderDao.updateOrderState(orderId);
    }
    public List<UserOrder> getUserOrdersByUser(User user){
        return orderDao.getUserOrdersByUser(user);
    }


    public UserOrder makeUserOrderFromCart(User user, Cart cart)
    {
        return orderDao.save( new UserOrder(user, cart));
    }
}
