package com.example.springjpaground.fetchjoin;

import com.example.springjpaground.nplusone.domain.Item;
import com.example.springjpaground.nplusone.domain.Member;
import com.example.springjpaground.nplusone.domain.MemberRepository;
import com.example.springjpaground.nplusone.domain.Team;
import com.example.springjpaground.nplusone.domain.TeamRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class fetchJoinTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        List<Team> teams = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
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
        em.flush();
        em.clear();
    }

    @Test
    void fetchJoin() {
        String jpql = "select t from Team t join fetch t.members";
        List<Team> teams = em.createQuery(jpql, Team.class)
                .setFirstResult(0)
                .setMaxResults(2)
                .getResultList();

        for (Team team : teams) {
            System.out.println("team = " + team.getName());
            for (Member member : team.getMembers()) {
                System.out.println("-> member = " + member.getName());
            }
        }
    }
}
