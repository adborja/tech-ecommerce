package co.edu.cedesistemas.commerce.config;

import co.edu.cedesistemas.commerce.model.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommerceConfig {
    @Bean
    public Map<String, Store> getStoreMap() {
        return new HashMap<>();
    }
}
