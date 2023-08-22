package com.undefined.undefined;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UndefinedApplication {

	public static void main(String[] args) {
		SpringApplication.run(UndefinedApplication.class, args);
	}

}
