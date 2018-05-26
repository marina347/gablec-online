package first.app.app1.daos;

import first.app.app1.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeDao  extends JpaRepository<Office, Integer> {
    List<Office> findAll();
    Office findById(int id);
}
