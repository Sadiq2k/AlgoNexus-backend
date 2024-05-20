package com.TestCaseService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestCaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCaseServiceApplication.class, args);
	}

}
