package co.edu.cedesistemas.commerce.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShippingServiceApp {
	public static void main(String[] args) {
		SpringApplication.run(ShippingServiceApp.class, args);
	}
}
