package first.app.app1;

import first.app.app1.daos.OrderItemDao;
import first.app.app1.models.OrderItem;
import first.app.app1.service.OrderItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderItemServiceTest {
    @Mock
    OrderItemDao orderItemDao;

    @InjectMocks
    OrderItemService orderItemService;

    @Before
    public void setUp() throws IOException
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest(){
        OrderItem orderItem=new OrderItem();
        orderItemService.save(orderItem);
        verify(orderItemDao,times(1)).save(orderItem);
    }
}
