package co.edu.cedesistemas.commerce.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableCaching
public class RegistrationApp {
    public static void main(String[] args) {
        SpringApplication.run(RegistrationApp.class, args);
    }
}
