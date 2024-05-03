package problemsService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import problemsService.entities.Constrains;

@Repository
public interface ConstrainsRepository extends JpaRepository<Constrains,Long> {
}
