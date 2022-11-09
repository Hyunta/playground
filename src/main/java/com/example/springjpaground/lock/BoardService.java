package com.example.springjpaground.lock;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void update(final Long id) {
        try {
            Board board = boardRepository.findById(id).get();
            board.update("new Name");
        } catch (ObjectOptimisticLockingFailureException e) {
            System.out.println("optimistic lock failed");
        }
    }
}
