package first.app.app1.service;

import first.app.app1.daos.DailyMenuDao;
import first.app.app1.daos.OrderItemDao;
import first.app.app1.models.DailyMenu;
import first.app.app1.models.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderItemService {
    @Autowired
    OrderItemDao orderItemDao;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public OrderItem save(OrderItem orderItem)
    {
        return orderItemDao.save(orderItem);
    }
}
