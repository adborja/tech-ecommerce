package co.edu.cedesistemas.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CommerceApp {
    public static void main(String[] args) {
        SpringApplication.run(CommerceApp.class, args);
    }
}
