package co.edu.cedesistemas.commerce.inventory.config;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    private static Map<String, RedisCacheConfiguration> cacheMap = Maps.newHashMap();

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60))
                .disableCachingNullValues();

        for (MyCaches cache : MyCaches.values()) {
            cacheMap.put(cache.name(), RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(cache.getTtl())
                    .disableCachingNullValues()
            );
        }

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheMap)
                .transactionAware()
                .build();
    }

    private RedisSerializationContext.SerializationPair<String> keyPair() {
        RedisSerializationContext.SerializationPair<String> keyPair =
                RedisSerializationContext.SerializationPair.fromSerializer(keySerializer());
        return keyPair;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    @Getter
    private enum MyCaches {
        defaultCache(Duration.ofDays(1)),
        MyCaches(Duration.ofMinutes(10));

        MyCaches(Duration ttl) {
            this.ttl = ttl;
        }
        /** Failure time */
        private Duration ttl = Duration.ofHours(1);
    }
}
