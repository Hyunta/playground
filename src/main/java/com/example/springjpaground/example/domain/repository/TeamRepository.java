package com.example.springjpaground.example.domain.repository;

import com.example.springjpaground.example.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
