package com.example.javaplayground;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JavaTest {

    @Test
    @DisplayName("null은 iterating이 불가능하다. 접근해서 어떤 메서드든 실행하면 안된다.")
    void nullIterator() {
        Object[] a = null;
        for (int i = 0; i < a.length; i++) {
            System.out.println("i = " + i);
        }
    }

    @Test
    @DisplayName("맵에 key 혹은 value를 put할 경우 NPE가 발생한다.")
    void map_put_null() {
        Assertions.assertThatThrownBy(
            () -> ((Map<String, String>) new ConcurrentHashMap()).put("key", null)
        ).isInstanceOf(NullPointerException.class);
    }
}
