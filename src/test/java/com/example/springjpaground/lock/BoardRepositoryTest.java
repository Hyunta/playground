package com.example.springjpaground.lock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    @DisplayName("낙관적 락이 잘 작동한다.")
    void optimistic_lock() throws InterruptedException {
        Thread thread1 = new Thread(() -> boardService.update(1L));
        Thread thread2 = new Thread(() -> boardService.update(1L));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
