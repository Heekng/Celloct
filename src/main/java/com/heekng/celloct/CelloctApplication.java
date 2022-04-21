package com.heekng.celloct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CelloctApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelloctApplication.class, args);
	}

}
