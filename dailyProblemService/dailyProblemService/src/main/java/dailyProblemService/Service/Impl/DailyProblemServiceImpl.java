package dailyProblemService.Service.Impl;

import dailyProblemService.FeingClient.ProblemServiceFeignClient;
import dailyProblemService.Model.Dto.ProblemDto;
import dailyProblemService.Model.Entities.DailyProblem;
import dailyProblemService.Model.Response.DailyProblemResponse;
import dailyProblemService.Repository.DailyProblemRepository;
import dailyProblemService.Service.DailyProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class DailyProblemServiceImpl implements DailyProblemService {

//    @Autowired
//    private MongoTemplate mongoTemplate;

//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private ProblemServiceFeignClient serviceFeignClient;

    @Autowired
    private DailyProblemRepository dailyProblemRepository;

    private static final int MAX_RETRIES = 10;

    @Override
    public DailyProblemResponse getProblemForDate(LocalDate date) {
        final DailyProblem byDate = dailyProblemRepository.findByDate(date);
        DailyProblemResponse dailyProblemResponse = DailyProblemResponse.builder()
                .problemId(byDate.getProblem().getProblemId())
                .date(byDate.getDate())
                .build();
        return dailyProblemResponse;

    }

    @Override
    public void assignDailyProblem() {
        LocalDate today = LocalDate.now();
        DailyProblem existingDailyProblem = dailyProblemRepository.findByDate(today);

        if (existingDailyProblem == null) {
            ProblemDto randomProblem;
            DailyProblem newDailyProblem;
            int attempts = 0;

            do {
                randomProblem = serviceFeignClient.getRandomProblem();
                newDailyProblem = new DailyProblem(today, randomProblem);
                attempts++;
                log.info("Attempt {}: Checking problem with ID {}", attempts, randomProblem.getProblemId());
            } while (problemAlreadyAssigned(randomProblem.getProblemId()) && attempts < MAX_RETRIES);

            if (!problemAlreadyAssigned(randomProblem.getProblemId())) {
                dailyProblemRepository.save(newDailyProblem);
                log.info("Assigned new daily problem with ID {} for date {}", randomProblem.getProblemId(), today);
            } else {
                log.error("Could not find a unique problem for today after {} attempts", MAX_RETRIES);
                throw new RuntimeException("Could not find a unique problem for today after " + MAX_RETRIES + " attempts.");
            }
        } else {
            log.info("Daily problem for date {} already exists", today);
        }
    }

    @Override
    public boolean isTodayProblem(String problemId, LocalDate date) {
        return dailyProblemRepository.existsByProblemProblemIdAndDate(problemId, date);
    }


    @Override
    public Page<DailyProblem> getAllDailyProblems(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "date"));
        Page<DailyProblem> dailyAllProblems = dailyProblemRepository.findAll(pageable);
        List<DailyProblem> allTopics = dailyAllProblems.getContent();
        Page<DailyProblem> responsePage = new PageImpl<>(allTopics, pageable, dailyAllProblems.getTotalElements());
        return responsePage;
    }

    private boolean problemAlreadyAssigned(String problemId) {
        return dailyProblemRepository.existsByProblemProblemId(problemId);
    }
}
