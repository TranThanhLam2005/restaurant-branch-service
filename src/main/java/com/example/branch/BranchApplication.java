package com.example.branch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;	

@SpringBootApplication
@EnableJpaRepositories
public class BranchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BranchApplication.class, args);
	}

}
