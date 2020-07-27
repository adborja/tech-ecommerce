package co.edu.cedesistemas.commerce.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RegistrationApp {
    public static void main(String[] args) {
        SpringApplication.run(RegistrationApp.class, args);
    }
}
