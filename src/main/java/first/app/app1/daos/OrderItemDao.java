package first.app.app1.daos;

import first.app.app1.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {


}
