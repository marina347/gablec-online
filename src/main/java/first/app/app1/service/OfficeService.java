package first.app.app1.service;

import first.app.app1.daos.OfficeDao;
import first.app.app1.daos.UserDao;
import first.app.app1.models.Office;
import first.app.app1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfficeService {
    @Autowired
    OfficeDao officeDao;

    @Autowired
    UserDao userDao;

    public List<Office> getAllOffices(){
        return officeDao.findAll();
    }

    public void assignOfficeRequest(String username, int officeId){
        User user=userDao.findByUsername(username);
        Office office=officeDao.findById(officeId);
        user.setOffice(office);
        userDao.save(user);
    }


    public void approveOfficeRequest(int userId){
        User user=userDao.findById(userId);
        user.setOdobreno(1);
        userDao.save(user);
    }
}
