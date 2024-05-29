package problemsService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import problemsService.Model.entities.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
}
