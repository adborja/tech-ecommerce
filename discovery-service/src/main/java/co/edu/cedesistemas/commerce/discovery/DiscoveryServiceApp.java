package co.edu.cedesistemas.commerce.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
//import lombok
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApp {
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApp.class, args);
	}
}
