package dailyProblemService.Service.Impl;

import dailyProblemService.Model.Entities.Streak;
import dailyProblemService.Repository.StreakRepository;
import dailyProblemService.Service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class StreakServiceImpl implements StreakService {

    @Autowired
    private StreakRepository streakRepository;

    @Override
    public void addStreak(String userId) {
        Streak streak = streakRepository.findByUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        if (streak != null) {
            LocalDateTime lastUpdateStreak = streak.getLastUpdateStreak();

            // Check if the last update date is before today
            if (lastUpdateStreak.toLocalDate().isBefore(now.toLocalDate())) {
                // If more than 24 hours have passed since the last update, reset daily streak
                if (ChronoUnit.DAYS.between(lastUpdateStreak.toLocalDate(), now.toLocalDate()) > 1) {
                    streak.setDailyStreak(1L); // Reset daily streak to 1
                } else {
                    streak.setDailyStreak(streak.getDailyStreak() + 1);
                }
                streak.setStreak(streak.getStreak() + 1);
                streak.setLastUpdateStreak(now);
            } else {
                return;
            }
        } else {
            streak = Streak.builder()
                    .userId(userId)
                    .lastUpdateStreak(now)
                    .streak(1L) // Initial streak value
                    .dailyStreak(1L) // Initial daily streak value
                    .build();
        }

        // Save the streak record
        streakRepository.save(streak);
    }

    @Override
    public Long getStreak(String userId) {
        final Streak streakByUserId = streakRepository.findStreakByUserId(userId);
        if (streakByUserId != null) {
            return streakByUserId.getStreak();
        } else {
            return 0L;
        }

    }

    @Override
    public Long getDailyStreak(String userId) {
        final Streak dailyStreakByUserId = streakRepository.findDailyStreakByUserId(userId);
        if (dailyStreakByUserId != null) {
            return dailyStreakByUserId.getDailyStreak();
        } else {
            return 0L;
        }
    }
}
