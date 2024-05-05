package problemsService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import problemsService.entities.Example;

public interface ExampleRepository extends JpaRepository<Example,Long> {
}
