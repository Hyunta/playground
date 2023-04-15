package com.example.redis.application;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.RedisURI.Builder;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisService {

    protected final RedisClient redisClient;
    protected final StatefulRedisConnection<String, String> connection;

    public RedisService() {
//        this.redisClient = RedisClient.create("redis://password@localhost:6379/");
        RedisURI redisURI = Builder
                .redis("localhost")
                .withPassword("password")
                .withDatabase(1)
                .build();
        this.redisClient = RedisClient.create(redisURI);
        this.connection = redisClient.connect();
    }
}
