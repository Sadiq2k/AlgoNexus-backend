package problemsService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import problemsService.Model.Dto.ProblemTitleDto;
import problemsService.Model.entities.Problem;

import java.util.Optional;

@Repository
public interface ProblemRepository extends MongoRepository<Problem,String> {

    boolean existsByTitleIgnoreCase(String title);

    Optional<Problem> findTitleByProblemId(String problemId);
}
