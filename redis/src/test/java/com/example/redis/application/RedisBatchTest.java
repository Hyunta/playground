package com.example.redis.application;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RedisBatchTest {
    private StatefulRedisConnection<String, String> connection = new RedisService().connection;
    private RedisAsyncCommands<String, String> asyncCommands = connection.async();

    @BeforeEach
    void setUp() {
        asyncCommands.flushall();
    }

    @Test
    void batch_test() {
        connection.setAutoFlushCommands(false);
        this.asyncCommands = connection.async();

        List<RedisFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(asyncCommands.set("key-" + i, "value-" + i));
        }
        connection.flushCommands();

        boolean result = LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futures.toArray(new RedisFuture[0]));
        System.out.println("result = " + result);
    }
}
