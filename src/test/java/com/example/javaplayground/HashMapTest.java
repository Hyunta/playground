package com.example.javaplayground;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HashMapTest {

    @Test
    @DisplayName("해쉬맵의 key와 value에 null을 넣을 수 있다.")
    void put_nullKeyAndValue() {
        //given
        Map<Object, Object> hashMap = new HashMap();

        //when & then
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> hashMap.put("key", null)),
                () -> assertThatNoException().isThrownBy(() -> hashMap.put(null, "value"))
        );
    }

    @Test
    @DisplayName("해쉬맵의 키가 null로 저장된 값을 불러올 수 있다.")
    void get_nullKey() {
        //given
        Map<Object, Object> hashMap = new HashMap();
        Object value = "value";
        hashMap.put(null, value);

        //when & then
        assertThat(hashMap.get(null)).isEqualTo(value);
    }

    @Test
    @DisplayName("해쉬맵의 값이 null로 저장됐을 때 불러올 수 있다.")
    void get_nullValue() {
        //given
        Map<Object, Object> hashMap = new HashMap();
        Object key = "key";
        hashMap.put(key, null);

        //when & then
        assertThat(hashMap.get(key)).isEqualTo(null);
    }

    @Test
    @DisplayName("concurrentHashMap에 key 혹은 value에 null을 집어넣으면 NPE가 발생한다.")
    void concurr_putNullKeyOrNullValue() {
        Map<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();

        Assertions.assertAll(
                () -> assertThatThrownBy(() -> concurrentHashMap.put("key", null))
                        .isInstanceOf(NullPointerException.class),
                () -> assertThatThrownBy(() -> concurrentHashMap.put(null, "value"))
                        .isInstanceOf(NullPointerException.class)
        );
    }
}
