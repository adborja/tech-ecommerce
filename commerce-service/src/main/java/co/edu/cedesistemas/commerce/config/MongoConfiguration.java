package co.edu.cedesistemas.commerce.config;

import co.edu.cedesistemas.commerce.model.converter.*;
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
        converters.add(new StoreWriteConverter());
        converters.add(new AddressWriteConverter());
        converters.add(new ProductWriteConverter());
        converters.add(new UserWriterConverter());
        converters.add(new OrderWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
