package co.edu.cedesistemas.gateway.config;

import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.SpringDataMongo3Driver;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.github.cloudyrock.spring.v5.MongockSpring5;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongockConfig {
    @Bean
    public MongockSpring5.MongockInitializingBeanRunner mongock(ApplicationContext springContext, MongoTemplate mongoTemplate) {
        return MongockSpring5.builder()
                .setDriver(new SpringDataMongo3Driver(mongoTemplate, 4, 4, 3))
                .addChangeLogsScanPackage("co.edu.cedesistemas.gateway.changeset")
                .setSpringContext(springContext)
                .buildInitializingBeanRunner();
    }
}
