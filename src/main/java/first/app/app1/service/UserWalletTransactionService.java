package first.app.app1.service;

import first.app.app1.daos.UserWalletTransactionDao;
import first.app.app1.models.User;
import first.app.app1.models.UserWalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWalletTransactionService {
    @Autowired
    UserWalletTransactionDao userWalletTransactionDao;

    public void saveTransaction(UserWalletTransaction userWalletTransaction){
        userWalletTransactionDao.save(userWalletTransaction);
    }
    public List<UserWalletTransaction> getByFromUser(User user, int completed){
        return userWalletTransactionDao.findByUserFromAndCompleted(user,completed);
    }
    public List<UserWalletTransaction> getByToUser(User user, int completed){
        return userWalletTransactionDao.findByUserToAndCompleted(user,completed);
    }
    public UserWalletTransaction getById(int id){
        return userWalletTransactionDao.getById(id);
    }
}
