package com.example.springjpaground.lock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        boardRepository.save(new Board("board1"));
        boardRepository.save(new Board("board2"));
        entityManager.clear();
    }

    @Test
    void test() throws InterruptedException {
        Thread thread1 = new Thread(() -> boardService.update(1L));
        Thread thread2 = new Thread(() -> boardService.update(1L));

        thread1.start();
        thread2.start();


        thread1.join();
        thread2.join();
    }
}
