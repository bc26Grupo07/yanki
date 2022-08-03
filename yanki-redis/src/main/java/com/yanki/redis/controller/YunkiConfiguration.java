package com.yanki.redis.controller;

import com.yanki.redis.domain.Yunki;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class YunkiConfiguration {

/*    @Bean
    public ReactiveRedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }*/

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("yunkis:queue");
    }

    @Bean
    ReactiveRedisMessageListenerContainer container(ReactiveRedisConnectionFactory factory) {

        ReactiveRedisMessageListenerContainer container = new ReactiveRedisMessageListenerContainer(factory);
        container.receive(topic());

        return container;
    }

    @Bean
    ReactiveRedisOperations<String, Yunki> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Yunki> serializer = new Jackson2JsonRedisSerializer<>(Yunki.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Yunki> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Yunki> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}