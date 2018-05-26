package first.app.app1.controllers.rest;

import first.app.app1.data.in.OrderData;
import first.app.app1.data.out.NotificationDetails;
import first.app.app1.data.out.RestaurantDto;
import first.app.app1.models.Restaurant;
import first.app.app1.models.User;
import first.app.app1.models.UserOrder;
import first.app.app1.service.*;
import first.app.app1.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/rest/today-order")
@RestController
public class OrdersRestController {
    @Autowired
    UserOrderService userOrderService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    TodayOrdersService todayOrdersService;

    @PostMapping("/confirm")
    public boolean confirmOrder(@RequestBody OrderData request) {
        try{
            RestaurantDto restaurantDto= todayOrdersService.getRestaurantDto(request.getId());
            Set<String> users = restaurantDto.getUsersUsername();
            todayOrdersService.makeOrderFromRestaurant(request.getId());

            NotificationDetails notificationDetails=new NotificationDetails();
            notificationDetails.setTitle("Order");
            notificationDetails.setBody("Food from "+restaurantDto.getRestaurant().getName()+" has been ordered!");
            notificationDetails.setIcon("img/ordered.jpg");
            notificationService.pushBulkMessages(users, notificationDetails);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @PostMapping("/arrived")
    public boolean orderArrived(@RequestBody OrderData request) {
        try{
            RestaurantDto restaurantDto = todayOrdersService.getRestaurantDto(request.getId());
            Set<String> users = restaurantDto.getUsersUsername();
            todayOrdersService.setDeliveredForRestaurant(request.getId());

            NotificationDetails notificationDetails=new NotificationDetails();
            notificationDetails.setTitle("Delivery");
            notificationDetails.setBody("Food from "+restaurantDto.getRestaurant().getName()+" has been delivered!");
            notificationDetails.setIcon("img/food-arrived.jpg");
            notificationService.pushBulkMessages(users, notificationDetails);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @PostMapping("/SMS")
    public boolean sendSMS(@RequestBody OrderData request) {
        try{
            RestaurantDto restaurantDto= todayOrdersService.getRestaurantDto(request.getId());
            Set<String> users = restaurantDto.getUsersUsername();
            todayOrdersService.makeOrderFromRestaurant(request.getId());
            SMSService smsService=new SMSService();
            smsService.sendSMS(restaurantDto.getFoodList());
            NotificationDetails notificationDetails=new NotificationDetails();
            notificationDetails.setTitle("Order");
            notificationDetails.setBody("Food from "+restaurantDto.getRestaurant().getName()+" has been ordered!");
            notificationDetails.setIcon("img/ordered.jpg");
            notificationService.pushBulkMessages(users, notificationDetails);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
