package com.example.redis.application;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.async.RedisAsyncCommands;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RedisTransactionTest {
    private RedisAsyncCommands<String, String> asyncCommands = new RedisService().connection.async();

    @BeforeEach
    void setUp() {
        asyncCommands.flushall();
    }

    @Test
    void transaction_do_not_rollback() throws ExecutionException, InterruptedException {
        asyncCommands.multi();

        RedisFuture<String> result1 = asyncCommands.set("key1", "1");
        RedisFuture<Long> result2 = asyncCommands.incr("key1");
        RedisFuture<String> result3 = asyncCommands.set("key3", "value3");

        RedisFuture<TransactionResult> execResult = asyncCommands.exec();

        TransactionResult transactionResult = execResult.get();

        String firstResult = transactionResult.get(0);
        String secondResult = transactionResult.get(0);
        String thirdResult = transactionResult.get(0);
    }
}
