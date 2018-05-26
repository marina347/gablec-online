package first.app.app1.daos;

import first.app.app1.models.OrderItem;
import first.app.app1.models.User;
import first.app.app1.models.UserOrder;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface OrderDao extends JpaRepository<UserOrder, Integer> {
    List<UserOrder> getOrdersByOrderDate(java.util.Date orderDate);

    List<UserOrder> getUserOrdersByUser(User user);
}
