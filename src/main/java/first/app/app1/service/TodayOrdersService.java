package first.app.app1.service;

import first.app.app1.daos.OrderDao;
import first.app.app1.data.out.RestaurantDto;
import first.app.app1.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TodayOrdersService {

    @Autowired
    UserService userService;

    @Autowired
    UserOrderService userOrderService;

    private static boolean ordersPrepared;

    private static HashMap<Integer, RestaurantDto> restaurantByIdMap;

    public TodayOrdersService()
    {
        if (restaurantByIdMap == null)
        {
            ordersPrepared = false;
            restaurantByIdMap = new HashMap<>();
        }
    }

    public void recordUserOrder(UserOrder userOrder)
    {
        User user = userOrder.getUser();
        for(OrderItem orderItem: userOrder.getOrderItemList())
        {
            recordOrderItem(orderItem, user);
        }
    }
    public void recordUserOrder(int  userOrderId)
    {
        recordUserOrder(userOrderService.getOrderById(userOrderId));

    }

    private RestaurantDto getRestaurantDto(Integer id, Restaurant restaurant)
    {
        if(!restaurantByIdMap.containsKey(id))
        {
            restaurantByIdMap.put(id, new RestaurantDto(restaurant));
        }
        return restaurantByIdMap.get(id);
    }

    public RestaurantDto getRestaurantDto(Integer id)
    {
        return restaurantByIdMap.containsKey(id)?
            restaurantByIdMap.get(id):
            null;
    }

    private void recordOrderItem(OrderItem orderItem, User user)
    {
        Restaurant restaurant = orderItem.getFood().getRestaurant();
        RestaurantDto restaurantDto = getRestaurantDto(restaurant.getId(), restaurant);
        restaurantDto.recordItem(user, orderItem.getFood(), orderItem.getCount());
    }

    public void makeOrderFromRestaurant(Integer restaurantId)
    {
        restaurantByIdMap.get(restaurantId).setOrdered(true);
    }

    public void setDeliveredForRestaurant(Integer restaurantId)
    {
        restaurantByIdMap.get(restaurantId).setDelivered(true);
    }


    public static HashMap<Integer, RestaurantDto> getRestaurantByIdMap() {
        return restaurantByIdMap;
    }

    public void prepareOrders()
    {
        if(!ordersPrepared){
            List<UserOrder> orderItems = userOrderService.getTodayOrders();
            for(UserOrder userOrder: orderItems)
            {
                recordUserOrder(userOrder);
            }
            ordersPrepared = true;
        }

    }
}
