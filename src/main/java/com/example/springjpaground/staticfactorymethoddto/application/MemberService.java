//package com.example.springjpaground.staticfactorymethoddto.application;
//
//import com.example.springjpaground.staticfactorymethoddto.application.dto.MemberResponseWithFrom;
//import com.example.springjpaground.staticfactorymethoddto.application.dto.MemberResponseWithNoStaticFactory;
//import com.example.springjpaground.staticfactorymethoddto.domain.Member;
//import com.example.springjpaground.staticfactorymethoddto.application.dto.MemberResponseWithOf;
//import com.example.springjpaground.staticfactorymethoddto.domain.Team;
//import com.example.springjpaground.staticfactorymethoddto.domain.repository.MemberRepository;
//import org.springframework.stereotype.Service;
//
////@Service
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    public void save(Member member) {
//        memberRepository.save(member);
//    }
//
//    public MemberResponseWithOf findByTeamWithOf(Team team) {
//        return memberRepository.findByTeamOf(team);
//    }
//
//    public MemberResponseWithFrom findByTeamWithFrom(Team team) {
//        return memberRepository.findByTeamFrom(team);
//    }
//
//    public MemberResponseWithNoStaticFactory findByTeamWithNoStaticFactoryMethod(Team team) {
//        return memberRepository.findByTeamNoStaticFactory(team);
//    }
//
//    public Member get(Long id) {
//        return memberRepository.findById(id)
//            .orElseThrow();
//    }
//}
