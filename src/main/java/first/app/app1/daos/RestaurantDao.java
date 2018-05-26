package first.app.app1.daos;

import first.app.app1.models.Food;
import first.app.app1.models.Phone;
import first.app.app1.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantDao extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findAll();
    Restaurant findById(int id);

}
