package co.edu.cedesistemas.commerce.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableCaching
public class SocialServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(SocialServiceApp.class, args);
    }
}
