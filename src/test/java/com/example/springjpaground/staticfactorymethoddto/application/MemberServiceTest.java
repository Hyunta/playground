//package com.example.springjpaground.staticfactorymethoddto.application;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//
//import com.example.springjpaground.staticfactorymethoddto.application.dto.MemberResponseWithFrom;
//import com.example.springjpaground.staticfactorymethoddto.domain.Member;
//import com.example.springjpaground.staticfactorymethoddto.domain.Team;
//import com.example.springjpaground.staticfactorymethoddto.domain.repository.MemberRepository;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.convert.ConverterNotFoundException;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class MemberServiceTest {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private TeamService teamService;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    @DisplayName("정적 팩터리 메서드 of가 있는 Dto로 변환 가능하다.")
//    void findByTeam_withOf() {
//        Team team = new Team(null, "team1");
//        teamService.save(team);
//
//        Member member = new Member(null, "memberA", team);
//        memberService.save(member);
//
//        Assertions.assertDoesNotThrow(
//                () -> memberService.findByTeamWithOf(team)
//        );
//
//    }
//
//    @Test
//    @DisplayName("정적 팩터리 메서드 from이 있는 Dto로 변환 가능하다.")
//    void findByTeam_withFrom() {
//        Team team = new Team(null, "team1");
//        teamService.save(team);
//
//        Member member = new Member(null, "memberA", team);
//        memberService.save(member);
//
//        MemberResponseWithFrom byTeamWithFrom = memberService.findByTeamWithFrom(team);
//
//        Assertions.assertDoesNotThrow(
//                () -> memberService.findByTeamWithFrom(team)
//        );
//    }
//
//    @Test
//    @DisplayName("Dto의 정적 팩터리 메서드가 없으면 예외를 던진다.")
//    void findByTeam_withNoStaticFactoryMethod() {
//        Team team = new Team(null, "team1");
//        teamService.save(team);
//
//        Member member = new Member(null, "memberA", team);
//        memberService.save(member);
//
//        memberService.findByTeamWithNoStaticFactoryMethod(team);
//
//        assertThatThrownBy(() -> memberService.findByTeamWithNoStaticFactoryMethod(team))
//                .isInstanceOf(ConverterNotFoundException.class);
//    }
//
//    @Test
//    void proxy_team_Test() {
//        Team team = new Team(null, "team1");
//        teamService.save(team);
//
//        Member member = new Member(null, "memberA", null);
//        memberService.save(member);
//
//        entityManager.flush();
//        entityManager.clear();
//
//        System.out.println(member.getId());
//        Member member1 = memberRepository.get(member.getId());
//        System.out.println(member1.getTeam());
//        String checknull = checknull(member1);
//        System.out.println("checknull = " + checknull);
//    }
//
//    private String checknull(Member member1) {
//        if (member1.getTeam() == null) {
//            return "nullTeam";
//        }
//        return member1.getTeam().getName();
//    }
//}
