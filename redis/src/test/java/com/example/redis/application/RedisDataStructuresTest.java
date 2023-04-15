package com.example.redis.application;

import static org.assertj.core.api.Assertions.assertThat;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RedisDataStructuresTest {
    private RedisAsyncCommands<String, String> asyncCommands = new RedisService().connection.async();

    @BeforeEach
    void setUp() {
        asyncCommands.flushall();
    }

    @Test
    void list_rpop() throws ExecutionException, InterruptedException {
        String firstTask = "firstTask";
        String secondTask = "secondTask";

        asyncCommands.lpush("tasks", firstTask);
        asyncCommands.lpush("tasks", secondTask);
        RedisFuture<String> tasks = asyncCommands.rpop("tasks");

        assertThat(tasks.get()).isEqualTo(firstTask);
    }

    @Test
    void list_lpop() throws ExecutionException, InterruptedException {
        String firstTask = "firstTask";
        String secondTask = "secondTask";

        asyncCommands.lpush("tasks", firstTask);
        asyncCommands.lpush("tasks", secondTask);
        RedisFuture<String> tasks = asyncCommands.lpop("tasks");

        assertThat(tasks.get()).isEqualTo(secondTask);
    }

    @Test
    void set() throws ExecutionException, InterruptedException {
        asyncCommands.sadd("pets", "dog");
        asyncCommands.sadd("pets", "cat");
        asyncCommands.sadd("pets", "cat");

        RedisFuture<Set<String>> pets = asyncCommands.smembers("pets");
        RedisFuture<Boolean> exists = asyncCommands.sismember("pets", "dog");
        assertThat(pets.get().size()).isEqualTo(2);
        assertThat(exists.get()).isTrue();
    }

    @Test
    void hashes() throws ExecutionException, InterruptedException {
        String key = "key";
        String firstName = "FirstName";
        String lastName = "LastName";

        asyncCommands.hset(key, firstName, "Hyuntae");
        asyncCommands.hset(key, lastName, "Kim");

        String result = asyncCommands.hget(key, lastName).get();
        Map<String, String> resultMap = asyncCommands.hgetall(key).get();
        System.out.println("resultMap = " + resultMap);
        assertThat(result).isEqualTo("Kim");
        assertThat(resultMap.containsKey(firstName)).isTrue();
    }
}
