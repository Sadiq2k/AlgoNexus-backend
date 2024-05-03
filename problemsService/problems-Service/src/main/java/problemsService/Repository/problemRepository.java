package problemsService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import problemsService.entities.Problem;

@Repository
public interface problemRepository extends JpaRepository<Problem,Long> {

}
