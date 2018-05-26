package first.app.app1.daos;

import first.app.app1.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface CategoryDao extends JpaRepository<Category, Integer> {

}
