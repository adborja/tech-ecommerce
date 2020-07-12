package co.edu.cedesistemas.commerce.config;

import co.edu.cedesistemas.commerce.model.converter.*;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@Slf4j
public class MongoConfiguration {
    @Bean
    public MongoCustomConversions customConversions() {
        log.info("adding mongo custom converters ...");
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new StoreWriteConverter());
        converters.add(new AddressConverter());
        converters.add(new OrderConverter());
        converters.add(new ProductConverter());
        converters.add(new UserConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    public MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClients.create(MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .build());
    }
}
