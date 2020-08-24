package co.edu.cedesistemas.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.hystrix.EnableHystrix;
//import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableHystrix
//@EnableHystrixDashboard
@EnableCaching

public class CommerceApp {
    public static void main(String[] args) {
        SpringApplication.run(CommerceApp.class, args);
    }
}
