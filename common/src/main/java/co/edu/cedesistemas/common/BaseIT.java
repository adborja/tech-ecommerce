package co.edu.cedesistemas.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.ParameterizedType;

@SpringBootTest
@Slf4j
public class BaseIT<T> {
    @Autowired protected MongoTemplate mongoTemplate;

    protected Class<T> _class;

    public BaseIT() {
        this._class = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @BeforeEach
    public void setup() {
        log.info("dropping collection  {} ", _class);
        mongoTemplate.dropCollection(_class);
    }

    @AfterEach
    public void tearDown() {
        log.info("dropping collection: {}", _class);
        mongoTemplate.dropCollection(_class);
    }

    protected void dropCollection(String collectionName) {
        log.info("dropping collection: {}", collectionName);
        mongoTemplate.dropCollection(collectionName);
    }
}