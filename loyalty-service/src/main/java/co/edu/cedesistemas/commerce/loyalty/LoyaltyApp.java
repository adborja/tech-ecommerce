package co.edu.cedesistemas.commerce.loyalty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LoyaltyApp {
    public static void main(String[] args) {
        SpringApplication.run(LoyaltyApp.class, args);
    }
}
