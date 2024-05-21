package problemSubmission.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import problemSubmission.Model.Entities.Submissions;

import java.util.List;

public interface SubmissionRepository extends MongoRepository<Submissions,String> {

    List<Submissions> findByProblemId(String problemId);

}
