package first.app.app1.daos;

import first.app.app1.models.Category;
import first.app.app1.models.Food;
import first.app.app1.models.Restaurant;
import first.app.app1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FoodDao extends JpaRepository<Food, Integer> {
    List<Food> getByRestaurant(Restaurant restaurant);
    Category getCategoryById(int id);
    Food findById(int id);
    List<Food> findByIdIn(int[] foodIds);


}
