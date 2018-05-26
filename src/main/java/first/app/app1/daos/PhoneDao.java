package first.app.app1.daos;

import first.app.app1.models.Phone;
import first.app.app1.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneDao extends JpaRepository<Phone, Integer> {
    List<Phone> getByRestaurant(Restaurant restaurant);
}
