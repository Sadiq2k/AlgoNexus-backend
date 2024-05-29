package problemSubmission.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import problemSubmission.Model.Entities.Submissions;

import java.util.List;

public interface SubmissionRepository extends MongoRepository<Submissions,String> {

    List<Submissions> findByProblemId(String problemId);

//    @Query(value = "{ 'userId': ?0, 'isSolved': true }", fields = "{ 'problemId': 1 }")
//    List<Submissions> findSolvedSubmissionsByUserId(String userId);

    @Query("{ 'userId': ?0, 'isSolved': true }")
    List<Submissions> findSolvedSubmissionsByUserId(String userId);

    @Query("{ 'userId': ?0 }")
    List<Submissions> findRecentSubmissionsByUserId(String userId, Pageable pageable);

}
