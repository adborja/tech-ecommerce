package co.edu.cedesistemas.commerce.loyalty.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Slf4j
public class LoyaltyConfig {

    @Value("${app.loyalty.points-conversion-rate}")
    public int pointConversionRate;

    @Bean
    public int getPointConversionRate(){
        log.info("************ pointConversions from LoyaltyConfig" + pointConversionRate);
        return pointConversionRate;
    }
}

