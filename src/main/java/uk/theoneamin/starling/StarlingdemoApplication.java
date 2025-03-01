package uk.theoneamin.starling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "uk.theoneamin.starling")
@EnableJpaRepositories(basePackages = "uk.theoneamin.starling.repository")
public class StarlingdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarlingdemoApplication.class, args);
	}

}
