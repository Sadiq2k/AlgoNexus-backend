package problemsService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import problemsService.Model.entities.Difficulties;

@Repository
public interface DifficultyRepository extends MongoRepository<Difficulties,String> {
}
