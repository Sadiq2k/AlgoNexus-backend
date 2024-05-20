package problemsService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import problemsService.Model.entities.Problem;

@Repository
public interface ProblemRepository extends MongoRepository<Problem,String> {

    boolean existsByTitleIgnoreCase(String title);
}
