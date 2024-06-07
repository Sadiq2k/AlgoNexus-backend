package socialMediaSerivce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SocialMediaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaServiceApplication.class, args);
	}

}
