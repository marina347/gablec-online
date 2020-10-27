package first.app.app1;

import first.app.app1.data.out.RestaurantDto;
import first.app.app1.models.*;
import first.app.app1.service.TodayOrdersService;
import first.app.app1.service.UserOrderService;
import first.app.app1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodayOrdersServiceTest {
    @Mock
    UserService userService;

    @Mock
    UserOrderService userOrderService;

    @InjectMocks
    TodayOrdersService todayOrdersService;

    User user;
    Food food;
    Restaurant restaurant;
    OrderItem orderItem;
    List<OrderItem> list;
    UserOrder userOrder;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        user=new User();
        user.setUsername("test");
        user.setName("test");
        user.setSurname("test");

        food=new Food();
        food.setId(1);

        restaurant=new Restaurant();
        restaurant.setId(1);
        food.setRestaurant(restaurant);
        orderItem=new OrderItem();
        orderItem.setFood(food);
        orderItem.setCount(2);
        list=new ArrayList<>(1);
        list.add(orderItem);
        userOrder=new UserOrder();

        userOrder.setOrderItemList(list);
        userOrder.setUser(user);
        userOrder.setId(1);
    }

    @Test
    public void getRestaurantDtoTest(){
        int id=1;
        RestaurantDto dto=todayOrdersService.getRestaurantDto(id);
        assertNull(dto);

        todayOrdersService.recordUserOrder(userOrder);
        RestaurantDto dto1= todayOrdersService.getRestaurantDto(food.getRestaurant().getId());
        assertNotNull(dto1);
    }
    @Test
    public void recordUserOrder(){
        when(userOrderService.getOrderById(1)).thenReturn(new UserOrder());
        todayOrdersService.recordUserOrder(1);
        verify(userOrderService,times(1)).getOrderById(1);
    }
    @Test
    public void recordUserOrder1(){
        todayOrdersService.recordUserOrder(userOrder);
    }


}
