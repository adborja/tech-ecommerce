package co.edu.cedesistemas.commerce.registration.config;


import co.edu.cedesistemas.commerce.registration.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommerceConfig {
    @Bean
    public Map<String, User> getStoreMap() {
        return new HashMap<>();
    }
}
