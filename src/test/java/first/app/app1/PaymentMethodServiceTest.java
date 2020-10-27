package first.app.app1;

import first.app.app1.daos.PaymentMethodDao;
import first.app.app1.models.PaymentMethod;
import first.app.app1.service.PaymentMethodService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PaymentMethodServiceTest {
    @Mock
    PaymentMethodDao paymentMethodDao;

    @InjectMocks
    PaymentMethodService paymentMethodService;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllTest(){
        List<PaymentMethod> listTest=new ArrayList<>(1);
        listTest.add(new PaymentMethod());
        when(paymentMethodDao.findAll()).thenReturn(listTest);
        List<PaymentMethod> listReturn= paymentMethodService.findAll();
        assertEquals(listTest,listReturn);
    }
}
