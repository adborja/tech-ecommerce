package co.edu.cedesistemas.commerce.registration.config;

import co.edu.cedesistemas.commerce.registration.model.converter.UserConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class MongoConfiguration {
    @Bean
    public MongoCustomConversions customConversions() {
        log.info("adding mongo custom converters ...");
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new UserConverter());
        return new MongoCustomConversions(converters);
    }
}
