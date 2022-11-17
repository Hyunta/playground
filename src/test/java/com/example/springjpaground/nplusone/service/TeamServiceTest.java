package com.example.springjpaground.nplusone.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springjpaground.nplusone.domain.Item;
import com.example.springjpaground.nplusone.domain.Member;
import com.example.springjpaground.nplusone.domain.MemberRepository;
import com.example.springjpaground.nplusone.domain.Team;
import com.example.springjpaground.nplusone.domain.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
class TeamServiceTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamService teamService;

    @BeforeEach
    void setUp() {
        List<Team> teams = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Team team = Team.builder()
                    .name("팀" + i)
                    .build();

            Member member = Member.builder()
                    .name("멤버" + i)
                    .build();
            team.addMember(member);

            Member subMember = Member.builder()
                    .name("교체멤버" + i)
                    .build();
            team.addMember(subMember);
            memberRepository.saveAll(List.of(member, subMember));



            team.addItem(Item.builder()
                    .name("아이템" + i)
                    .build());
            teams.add(team);
        }

        teamRepository.saveAll(teams);
    }

    @Test
    @DisplayName("팀에 소속된 모든 회원의 이름을 조회한다.")
    void findAllMembers() {
        log.info(">>>>>>>[전체 회원을 조회한다.]<<<<<<<<<");
        List<String> members = teamService.findAllMembers();

        assertThat(members).hasSize(10);
    }

    @Test
    @DisplayName("팀의 갯수는 멤버의 수만큼 늘어난다.")
    void findAllTeam() {
        log.info(">>>>>>>[전체 팀을 조회한다.]<<<<<<<<<");
        List<Team> teams2 = teamRepository.findAllEntityGraph();
        List<Team> teams = teamRepository.findAllJoinFetch();

        assertThat(teams).hasSize(20);
    }
}
