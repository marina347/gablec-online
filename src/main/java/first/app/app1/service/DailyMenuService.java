package first.app.app1.service;

import first.app.app1.daos.DailyMenuDao;
import first.app.app1.models.DailyMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailyMenuService {
    @Autowired
    DailyMenuDao dailyMenuDao;

    public DailyMenu getCurrentDay(int day){
        if(day == 0)
            day = 7;
        return dailyMenuDao.getById(day);
    }
}
