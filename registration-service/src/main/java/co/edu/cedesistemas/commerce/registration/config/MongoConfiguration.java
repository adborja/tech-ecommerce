package co.edu.cedesistemas.commerce.registration.config;

import co.edu.cedesistemas.commerce.registration.model.converter.UserWriteConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration {
    @Bean
    public MongoCustomConversions customConversions(){
        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(new UserWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
