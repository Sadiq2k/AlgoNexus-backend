package dailyProblemService.Service;

public interface StreakService {
    void addStreak(String userId);

    Long getStreak(String userId);

    Long getDailyStreak(String userId);
}
