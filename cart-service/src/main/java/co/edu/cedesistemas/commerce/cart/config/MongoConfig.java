package co.edu.cedesistemas.commerce.cart.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {
    @Value("${app.mongo.host:localhost}")
    private String host;

    @Value("${app.mongo.port:27017}")
    private Integer port;

    @Value("${app.mongo.dbname}")
    private String dbname;

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(String.format("mongodb://%s:%s", host, port));
    }

    @Override
    protected String getDatabaseName() {
        return dbname;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}
