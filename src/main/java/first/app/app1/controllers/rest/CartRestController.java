package first.app.app1.controllers.rest;

import first.app.app1.daos.OrderItemDao;
import first.app.app1.data.in.CartItemInData;
import first.app.app1.models.*;
import first.app.app1.service.*;
import first.app.app1.utils.UserSessionUtils;
import first.app.app1.watson.DiscoveryManager;
import first.app.app1.watson.UserOrderDiscoveryModel;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@RestController
public class CartRestController {

    @Autowired
    UserService userService;

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    OrderItemService orderItemService;

    @Autowired
    TodayOrdersService todayOrdersService;

    @Value("${discovery.environmentId}")
    private String environmentId;

    @Value("${discovery.collectionId}")
    private String collectionId;

    @Value("${discovery.username}")
    private String username;

    @Value("${discovery.password}")
    private String password;



    @PostMapping()
    public boolean newCart(@RequestBody List<CartItemInData> items) {
        try
        {
            for (CartItemInData cartItemInData :
                    items) {
                cartService.putItemIntoCart(UserSessionUtils.getUserUsername(), cartItemInData.getFoodId(), cartItemInData.getCount());
            }
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    private void pushOrderToWatsonDiscovery(User user, UserOrder order)
    {


        DiscoveryManager discoveryManager = new DiscoveryManager(environmentId,collectionId,username,password);
        discoveryManager.initManager();
        //discoveryManager.addDocumentToWatson(new UserOrderDiscoveryModel(1,1,2,3));
        for(OrderItem orderItem: order.getOrderItemList())
        {
            UserOrderDiscoveryModel discoveryModel = new UserOrderDiscoveryModel(
                    user.getId(),
                    orderItem.getFood().getId(),
                    orderItem.getCount(),
                    Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==1?
                            7:
                            Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1
            );
            discoveryManager.addDocumentToWatson(discoveryModel);
        }
    }

    @PostMapping("/make-order")
    public ResponseEntity<Object> makeOrder() {
        String username = UserSessionUtils.getUserUsername();
        User user = userService.findByUsername(username);
        try
        {
            Cart cart = cartService.getCart(username);
            double totalCartCost = cartService.calculateCartTotal(cart);
            Iterator it = cart.getFoodList().entrySet().iterator();
            String [] times=java.time.LocalTime.now().toString().split(":");
            int hour=Integer.parseInt(times[0]);
            int minutes=Integer.parseInt(times[1]);
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Object food=pair.getValue();
                CartItem cartI=(CartItem) food;
                String [] resTime=cartI.getFood().getRestaurant().getOrderTime().toString().split(":");
                int hourRes=Integer.parseInt(resTime[0]);
                int minutesRes=Integer.parseInt(resTime[1]);
                /*
                if(hourRes<hour) {
                    return new ResponseEntity<>(false, HttpStatus.OK);
                }
                 if(hourRes==hour){
                    if(minutesRes<=minutes){
                        return new ResponseEntity<>(false, HttpStatus.OK);
                   }
                }
                */


            }
            if(user.getLunchWallet() < totalCartCost)
            {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
            else
            {
                UserOrder freshOrder = userOrderService.makeUserOrderFromCart(user, cart);
                int   id = freshOrder.getId();

                todayOrdersService.recordUserOrder(id);
                user.setLunchWallet(user.getLunchWallet()-(float)totalCartCost);
                userService.saveUser(user);
                cartService.destroyCartSession(username);
                pushOrderToWatsonDiscovery(user, freshOrder);
                return new ResponseEntity<>(id != 0, HttpStatus.OK);
            }
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/put-item")
    public boolean putItemInCart(@RequestParam(value="foodId") int foodId, @RequestParam(value="count") int count)
    {
        try
        {
            cartService.putItemIntoCart(UserSessionUtils.getUserUsername(), foodId, count);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    @PostMapping("/increase-count-item")
    public boolean increaseCountItem(@RequestParam(value="foodId") int foodId){
        try
        {
            cartService.incrementItemCount(UserSessionUtils.getUserUsername(), foodId);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    @PostMapping("/decrease-count-item")
    public boolean decreaseCountItem(@RequestParam(value="foodId") int foodId){
        try
        {
            cartService.decrementItemCount(UserSessionUtils.getUserUsername(), foodId);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    @PostMapping("/remove-item")
    public boolean removeItem(@RequestParam(value="foodId") int foodId){
        try
        {
            cartService.removeItemFromCart(UserSessionUtils.getUserUsername(), foodId);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    @PutMapping("/clear-cart")
    public boolean clearCart(){
        try
        {
            cartService.destroyCartSession(UserSessionUtils.getUserUsername());
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }


}
