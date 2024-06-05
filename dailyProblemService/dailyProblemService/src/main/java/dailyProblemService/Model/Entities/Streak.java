package dailyProblemService.Model.Entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "streak")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Streak {

    @Id
    private String id;
    private String userId;
    private Long streak;
    private LocalDate date;
    private LocalDateTime lastUpdateStreak;
    private Long dailyStreak;
}
