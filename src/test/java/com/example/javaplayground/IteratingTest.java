package com.example.javaplayground;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IteratingTest {

    @Test
    @DisplayName("null값으로 for문을 작성하면 NPE가 발생한다.")
    void fail_forWithNull() {
        Integer a = null;
        Assertions.assertThatThrownBy(() -> {
            for (int i = 0; i < a; i++) {
                System.out.println("i = " + i);
            }
        }).isInstanceOf(NullPointerException.class);
    }
}
