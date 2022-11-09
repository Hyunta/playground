package com.example.springjpaground.lock;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findById(Long id);
}
