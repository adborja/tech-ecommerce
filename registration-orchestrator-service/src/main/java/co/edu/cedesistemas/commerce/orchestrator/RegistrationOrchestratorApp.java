package co.edu.cedesistemas.commerce.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RegistrationOrchestratorApp {
    public static void main(String[] args) {
        SpringApplication.run(RegistrationOrchestratorApp.class, args);
    }
}
