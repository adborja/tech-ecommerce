package co.edu.cedesistemas.commerce.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
public class SocialServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(SocialServiceApp.class, args);
	}

}
