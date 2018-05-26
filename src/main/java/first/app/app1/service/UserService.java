package first.app.app1.service;

import first.app.app1.daos.UserDao;
import first.app.app1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserService {
    @Autowired
    UserDao userDao;

    public User findByUserId(int id){
        return userDao.findById(id);
    }
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    public List<User> getRequestsForOffices(int odobreno){
        return userDao.findByOdobrenoAndOfficeIsNotNull(odobreno);
    }
    public void saveUser(User user){
        userDao.save(user);
    }

    public List<User> getUsersByOfficeId(int officeId){
        return userDao.findByOfficeId(officeId);
    }
    public List<User> findByUsernames(Set<String> usernames){
        return userDao.findByUsernameIn(usernames);
    }
}
