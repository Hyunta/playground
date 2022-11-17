package com.example.springjpaground.nplusone.service;

import com.example.springjpaground.nplusone.domain.Team;
import com.example.springjpaground.nplusone.domain.TeamRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public List<String> findAllMembers() {
//        return extractMembers(teamRepository.findAll());
//        return extractMembers(teamRepository.findAllJoinFetch());
//        return extractMembers(teamRepository.findAllJoinFetchWithItem());
        return extractMembers(teamRepository.findAllEntityGraph());
    }

    @Transactional(readOnly = true)
    public List<String> findAllMembersBtFetchJoin() {
//        return extractMembers(teamRepository.findAll());
        return extractMembers(teamRepository.findAllJoinFetch());
//        return extractMembers(teamRepository.findAllJoinFetchWithItem());
//        return extractMembers(teamRepository.findAllEntityGraph());
    }

    /**
     * Lazy Loading을 수행하기 위한 메서드
     */
    private List<String> extractMembers(List<Team> teams) {
        log.info(">>>>>>>[모든 회원을 추출한다.]<<<<<<<<<");
        log.info("Team Size : {}", teams.size());

        return teams.stream()
                .map(it -> it.getMembers().get(0).getName())
                .collect(Collectors.toList());
    }
}
