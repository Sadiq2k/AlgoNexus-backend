package problemsService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import problemsService.Model.Dto.TestCase;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCase, String> {
}
