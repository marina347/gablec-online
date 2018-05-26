package first.app.app1.daos;

import first.app.app1.models.DailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyMenuDao extends JpaRepository<DailyMenu, Integer> {
    DailyMenu getById(int day);
}
