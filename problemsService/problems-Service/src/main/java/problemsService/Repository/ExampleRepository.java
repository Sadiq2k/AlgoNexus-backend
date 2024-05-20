package problemsService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import problemsService.Model.Dto.Example;

public interface ExampleRepository extends MongoRepository<Example,Long> {
}
