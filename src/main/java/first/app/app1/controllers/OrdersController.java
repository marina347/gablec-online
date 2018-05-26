package first.app.app1.controllers;

import first.app.app1.data.out.RestaurantDto;
import first.app.app1.models.User;
import first.app.app1.models.UserOrder;
import first.app.app1.service.TodayOrdersService;
import first.app.app1.service.UserOrderService;
import first.app.app1.service.UserService;
import first.app.app1.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


@Controller
public class OrdersController {
    @Autowired
    UserOrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    TodayOrdersService todayOrdersService;

    @GetMapping("/admin/orders")
    public String showOrders(Model model)
    {
        List<UserOrder> orders = orderService.getTodayOrders();
        model.addAttribute("orders", orders);
        HashMap<Integer, RestaurantDto> restaurantDtoHashMap = TodayOrdersService.getRestaurantByIdMap();
        model.addAttribute("ordersByRestaurant", restaurantDtoHashMap.values());
        return "admin/today-orders";
    }


    @GetMapping("/history")
    public String showUserOrders(Model model){
        User user = userService.findByUsername(UserSessionUtils.getUserUsername());
        List<UserOrder> orderList= orderService.getUserOrdersByUser(user);

        //sorting obrnuto
        orderList.sort((o1, o2) -> {
            if (o1.getOrderDate() == null || o2.getOrderDate() == null)
                return 0;
            return o2.getOrderDate().compareTo(o1.getOrderDate());
        });

        model.addAttribute("orders",orderList);
        return "history";
    }
}
