package dailyProblemService.Repository;

import dailyProblemService.Model.Entities.DailyProblem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface DailyProblemRepository  extends MongoRepository<DailyProblem, String> {

    DailyProblem findByDate(LocalDate date);
    boolean existsByProblemProblemId(String problemId);

    boolean existsByProblemProblemIdAndDate(String problemId, LocalDate date);
}
