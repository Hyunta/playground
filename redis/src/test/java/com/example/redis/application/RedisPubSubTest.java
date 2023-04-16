package com.example.redis.application;

import com.example.redis.pubsub.Listener;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RedisPubSubTest {
    private RedisClient redisClient = new RedisService().redisClient;
    private StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
    private RedisPubSubAsyncCommands<String, String> async = connection.async();

    @BeforeEach
    void setUp() {
        async.flushall();
        connection.addListener(new Listener());
        async.subscribe("channel");
    }

    @Test
    void pubsub_test() throws InterruptedException {
        async.publish("channel", "Hello, Redis PubSub!!");

        Thread.sleep(1000);
    }
}
