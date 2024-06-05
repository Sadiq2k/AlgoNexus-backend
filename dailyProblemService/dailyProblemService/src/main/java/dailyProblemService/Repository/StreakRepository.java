package dailyProblemService.Repository;

import dailyProblemService.Model.Entities.Streak;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StreakRepository extends MongoRepository<Streak,String> {

    Streak findByUserId(String userId);

    @Query(value = "{ 'userId' : ?0 }", fields = "{ 'streak' : 1 }")
    Streak findStreakByUserId(String userId);

    @Query(value = "{ 'userId' : ?0 }", fields = "{ 'dailyStreak' : 1 }")
    Streak findDailyStreakByUserId(String userId);

}
