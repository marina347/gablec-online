package first.app.app1;

import first.app.app1.daos.OrderDao;
import first.app.app1.daos.OrderItemDao;
import first.app.app1.models.Cart;
import first.app.app1.models.User;
import first.app.app1.models.UserOrder;
import first.app.app1.service.UserOrderService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserOrderServiceTest {
    @Mock
    OrderDao orderDao;

    @Mock
    OrderItemDao orderItemDao;

    @InjectMocks
    UserOrderService userOrderService;

    private List<UserOrder> listTest;
    private UserOrder userTest;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        listTest=new ArrayList<>();
        listTest.add(new UserOrder());
        userTest=new UserOrder();
    }


    @Test
    public void getTodayOrdersTest(){
        //Date date= Calendar.getInstance().getTime();
        when(orderDao.getOrdersByOrderDate(any())).thenReturn(listTest);
        List<UserOrder> listReturn=userOrderService.getTodayOrders();
        assertEquals(listTest,listReturn);
    }

    @Test
    public void getOrderByIdTest(){
        int id=1;
        when(orderDao.findOne(id)).thenReturn(userTest);
        UserOrder userReturn=userOrderService.getOrderById(id);
        assertEquals(userTest,userReturn);
    }

    @Test
    public void getUserOrdersByUserTest(){
        User user=new User();
        when(orderDao.getUserOrdersByUser(user)).thenReturn(listTest);
        List<UserOrder> listReturn=userOrderService.getUserOrdersByUser(user);
        assertEquals(listTest,listReturn);
    }

    @Test
    public void makeUserOrderFromCartTest(){
        User user=new User();
        Cart cart=new Cart();
        userOrderService.makeUserOrderFromCart(user,cart);
        verify(orderDao,times(1)).save(any(UserOrder.class));

    }
}
