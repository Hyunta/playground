package com.example.springjpaground.jpaequality;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springjpaground.lock.Board;
import com.example.springjpaground.lock.BoardRepository;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class EqualityTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    void name() {
        User userA = new User(null, "userA");

        userRepository.save(userA);
        User user1 = userRepository.findById(1L).get();
        User user2 = userRepository.findById(1L).get();

//        assertThat(userA.equals(user)).isTrue();
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.equals(user2)).isTrue();
//        assertThat(user1 == user2).isTrue();
    }

    @Test
    @Transactional
    void board() {
        Board board = new Board(1L, "boardA");

        Board boardA = new Board(1L, "boardA");
        boardRepository.save(boardA);
        boardRepository.save(board);

        Board boardB = boardRepository.findById(1L).get();
        assertThat(boardB == board).isTrue();
    }

    @Entity
    @NoArgsConstructor
    @Getter
    @ToString
    protected static class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return Objects.equals(id, user.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
