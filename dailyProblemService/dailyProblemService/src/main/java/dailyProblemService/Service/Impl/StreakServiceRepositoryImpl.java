package dailyProblemService.Service.Impl;

import dailyProblemService.Model.Entities.Streak;
import dailyProblemService.Repository.StreakRepository;
import dailyProblemService.Service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StreakServiceRepositoryImpl implements StreakService {

    @Autowired
    private StreakRepository streakRepository;

    @Override
    public void addStreak(String userId) {
        Streak streak = streakRepository.findByUserId(userId);
        LocalDate today = LocalDate.now();

        if (streak != null) {
            if (streak.getLastUpdateStreak().isBefore(today)) {
                streak.setStreak(streak.getStreak() + 1);
                streak.setLastUpdateStreak(today);
            }
        } else {
            streak = Streak.builder()
                    .userId(userId)
                    .date(today)
                    .lastUpdateStreak(today)
                    .streak(1L) // Initial streak value
                    .build();
        }

        streakRepository.save(streak);
    }

    @Override
    public Long getStreak(String userId) {
        final Streak streakByUserId = streakRepository.findStreakByUserId(userId);
        return streakByUserId.getStreak();

    }
}
