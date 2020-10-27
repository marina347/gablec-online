package first.app.app1.service;

import first.app.app1.daos.FoodDao;
import first.app.app1.models.Category;
import first.app.app1.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodService {
    @Autowired
    private FoodDao foodDao;

    public List<Food> findByIdIn(int[] foodIds){
        return foodDao.findByIdIn(foodIds);
    }
}
