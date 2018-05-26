package first.app.app1.service;

import first.app.app1.daos.PaymentMethodDao;
import first.app.app1.models.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMethodService {
    @Autowired
    PaymentMethodDao paymentMethodDao;

    public List<PaymentMethod> findAll(){
        return paymentMethodDao.findAll();
    }
}
