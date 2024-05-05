package problemsService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import problemsService.entities.TestCase;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase,Long> {
}
