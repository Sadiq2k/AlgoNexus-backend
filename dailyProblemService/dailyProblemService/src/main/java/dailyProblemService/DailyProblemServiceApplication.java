package dailyProblemService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DailyProblemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyProblemServiceApplication.class, args);
	}

}
