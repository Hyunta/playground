package com.example.springjpaground.example.application;

import com.example.springjpaground.example.domain.Team;
import com.example.springjpaground.example.domain.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void save(Team team) {
        teamRepository.save(team);
    }
}
