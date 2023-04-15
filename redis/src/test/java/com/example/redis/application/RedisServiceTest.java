package com.example.redis.application;

import static org.assertj.core.api.Assertions.assertThat;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisStringReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RedisServiceTest {

    private RedisService redisService = new RedisService();
    private StatefulRedisConnection<String, String> connection = redisService.connection;
    private RedisCommands<String, String> syncCommands = connection.sync();
    private RedisAsyncCommands<String, String> asyncCommands = connection.async();
    private RedisStringReactiveCommands<String, String> reactiveCommands = connection.reactive();

    @BeforeEach
    void setUp() {
        syncCommands.flushall();
    }

    @Test
    void syncCommand_set() {
        String value = "Hello, Redis!";
        String key = "key";
        syncCommands.set(key, value);

        String result = syncCommands.get(key);
        assertThat(result).isEqualTo(value);
    }

    @Test
    void syncCommand_hset() {
        String key = "key";
        String firstName = "FirstName";
        String lastName = "LastName";
        syncCommands.hset(key, firstName, "Hyuntae");
        syncCommands.hset(key, lastName, "Kim");

        Map<String, String> record = syncCommands.hgetall(key);
        System.out.println("record = " + record);
        assertThat(record).containsEntry("FirstName", "Hyuntae");
    }

    @Test
    void asyncCommand_get() throws ExecutionException, InterruptedException {
        String value = "Hello, Redis!";
        String key = "key";
        asyncCommands.set(key, value);

        RedisFuture<String> result = asyncCommands.get("key");
        Assertions.assertThat(result.get()).isEqualTo(value);
    }
}
