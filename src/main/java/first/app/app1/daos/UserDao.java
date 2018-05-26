package first.app.app1.daos;

import first.app.app1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface UserDao extends JpaRepository<User, Integer>{

    User findById(int id);
    User findByUsername(String username);
    List<User> findByOdobreno(int odobreno);
    List<User> findByOdobrenoAndOfficeIsNotNull(int odobreno);
    List<User> findByOfficeId(int officeId);
    List<User> findByUsernameIn(Set<String> usernameList);
}
