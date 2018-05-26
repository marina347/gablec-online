package first.app.app1.utils;

import first.app.app1.models.*;
import first.app.app1.service.UserOrderService;
import first.app.app1.service.RestaurantService;
import first.app.app1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RestaurantPopularity {
    @Autowired
    UserOrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    RestaurantService restaurantService;

    HashMap<Integer,Integer> restaurants= new HashMap<>();

    public List<Restaurant> getRestaurants(){
        User user=userService.findByUsername(UserSessionUtils.getUserUsername());
        List<UserOrder> userOrderList=orderService.getUserOrdersByUser(user);
        if(userOrderList.size()==0) return restaurantService.getAllRestaurants();

        List<OrderItem> orderItems=new ArrayList<>();
        List<Food> foodList=new ArrayList<>();

        for(int i=0;i<userOrderList.size();i++){
            for(int j=0;j<userOrderList.get(i).getOrderItemList().size();j++) {
                orderItems.add(userOrderList.get(i).getOrderItemList().get(j));
            }
        }
        int currentRestaurant;
        int currentCount;
        for(int i=0;i<orderItems.size();i++){
            currentRestaurant=orderItems.get(i).getFood().getRestaurant().getId();
            if(restaurants.get(currentRestaurant)==null) currentCount=orderItems.get(i).getCount();
            else currentCount=restaurants.get(currentRestaurant)+orderItems.get(i).getCount();
            restaurants.put(currentRestaurant,currentCount);
        }
        List<Integer> restaurantIdList=new ArrayList<>();

        while(restaurants.size()!=0) {
            int resId=restaurants.keySet().stream().findFirst().get();
            int min=restaurants.get(resId);
            Iterator it = restaurants.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (min < (int) pair.getValue()) {
                    min = (int) pair.getValue();
                    resId = (int) pair.getKey();
                }
            }
            restaurants.remove(resId);
            restaurantIdList.add(resId);
        }
        List<Restaurant> forReturn=new ArrayList<>();
        List<Restaurant> restaurantList=restaurantService.getAllRestaurants();
        for(int i=0;i<restaurantIdList.size();i++){
            forReturn.add(restaurantService.getRestaurantById(restaurantIdList.get(i)));
        }
        for(int i=0;i<forReturn.size();i++){
            for(int j=0;j<restaurantList.size();j++){
                if(forReturn.get(i).getId()==restaurantList.get(j).getId()) restaurantList.remove(j);
            }
        }
        for(int i=0;i<restaurantList.size();i++){
            forReturn.add(restaurantList.get(i));
        }
        return forReturn;

    }


}
