package com.yanki.redis.util;


import com.yanki.redis.domain.Yunki;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class YunkiPreLoader {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Yunki> yunkiOps;

    public YunkiPreLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Yunki> yunkiOps) {
        this.factory = factory;
        this.yunkiOps = yunkiOps;
    }

    @PostConstruct
    public void loadData() {
        // Just fill our Redis with some predefined data
        factory.getReactiveConnection().serverCommands().flushAll().thenMany(

                Flux.just("yanki", "transferencia", "yanki")
                        .map(name -> new Yunki())
                        .flatMap(yunki -> yunkiOps.opsForValue().set(yunki.getId(), yunki)))
                .thenMany(yunkiOps.keys("*")
                        .flatMap(yunkiOps.opsForValue()::get))
                .subscribe(System.out::println);
    }
}