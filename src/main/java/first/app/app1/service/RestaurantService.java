package first.app.app1.service;

import first.app.app1.daos.FoodDao;
import first.app.app1.daos.PhoneDao;
import first.app.app1.daos.RestaurantDao;
import first.app.app1.models.Food;
import first.app.app1.models.Phone;
import first.app.app1.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantService {
    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    FoodDao foodDao;

    @Autowired
    PhoneDao phoneDao;


    public List<Restaurant> getAllRestaurants(){
        return restaurantDao.findAll();
    }
    public Restaurant getRestaurantById(int id){
        return  restaurantDao.findById( id);
    }
    public void saveRestaurant(Restaurant res){
        restaurantDao.save(res);
    }


}
