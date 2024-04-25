package dev.iyanu.Book.Rental.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public RedisProperties properties(){
        //this gets the properties that have been setup in application.prop
        return new RedisProperties();
    }

    @Bean
    public LettuceConnectionFactory connectionFactory(){
        //this is to create a way of connecting to the redis DB using the properties that have been set
        RedisProperties properties = properties();
        RedisStandaloneConfiguration configuration= new RedisStandaloneConfiguration();
        configuration.setHostName(properties.getHost());
        configuration.setPort(properties.getPort());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        //this is just like the jpa repository, allows us to connect to use JAVA objects with redis
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

}
