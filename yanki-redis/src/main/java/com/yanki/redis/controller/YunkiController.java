package com.yanki.redis.controller;

import com.yanki.redis.domain.Yunki;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class YunkiController {

    private static final Logger LOG = LoggerFactory.getLogger(YunkiController.class);

    private final ReactiveRedisOperations<String, Yunki> yunkiOps;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveTemplate;

    @Autowired
    private ReactiveRedisMessageListenerContainer reactiveMsgListenerContainer;

    @Autowired
    private ChannelTopic topic;

    YunkiController(ReactiveRedisOperations<String, Yunki> yunkiOps) {
        this.yunkiOps = yunkiOps;
    }

    @GetMapping("/yunkis")
    public Flux<Yunki> all() {
        LOG.info("Receiving all Yunkis from Redis.");
        return yunkiOps.keys("*")
                .flatMap(yunkiOps.opsForValue()::get);
    }

    @PostMapping("/yunkis")
    public Mono<Boolean> save(@RequestBody Yunki yunki) {
        LOG.info("Nuevo '" + yunki + "' added to Redis.");
        yunki.setId(UUID.randomUUID().toString());
        return yunkiOps.opsForValue().set(yunki.getId(), yunki);
    }

}
