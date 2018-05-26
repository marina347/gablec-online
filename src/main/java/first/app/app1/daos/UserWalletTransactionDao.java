package first.app.app1.daos;

import first.app.app1.models.User;
import first.app.app1.models.UserWalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWalletTransactionDao  extends JpaRepository<UserWalletTransaction, Integer> {
     List<UserWalletTransaction> findByUserFromAndCompleted(User user, int completed);
     List<UserWalletTransaction> findByUserToAndCompleted(User user, int completed);
     UserWalletTransaction getById(int id);
}
