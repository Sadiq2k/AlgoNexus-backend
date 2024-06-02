package problemSubmission.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import problemSubmission.Model.Entities.Submissions;
import problemSubmission.Model.Response.AllSubmissionResponse;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends MongoRepository<Submissions,String> {

    List<Submissions> findByProblemId(String problemId);

//    @Query(value = "{ 'userId': ?0, 'isSolved': true }", fields = "{ 'problemId': 1 }")
//    List<Submissions> findSolvedSubmissionsByUserId(String userId);

    @Query("{ 'userId': ?0, 'isSolved': true }")
    List<Submissions> findSolvedSubmissionsByUserId(String userId);

    @Query("{ 'userId': ?0 }")
    List<Submissions> findRecentSubmissionsByUserId(String userId, Pageable pageable);

    Page<Submissions> findSubmissionsByUserId(String userId, Pageable pageable);

    Optional<Submissions> findByUserIdAndProblemIdAndIsSolved(String userId, String problemId, boolean b);

    @Aggregation(pipeline = {
            "{ $match: { submission: 'ACCEPTED' } }",
            "{ $group: { _id: { userId: '$userId', problemId: '$problemId' }, count: { $sum: 1 } } }",
            "{ $group: { _id: null, totalAcceptances: { $sum: 1 } } }",
            "{ $project: { _id: 0, totalAcceptances: 1 } }"
    })
    Long countUniqueAcceptedProblems();

}
