package com.example.springjpaground.example.application;

import com.example.springjpaground.example.application.dto.MemberResponseWithFrom;
import com.example.springjpaground.example.application.dto.MemberResponseWithNoStaticFactory;
import com.example.springjpaground.example.domain.Member;
import com.example.springjpaground.example.application.dto.MemberResponseWithOf;
import com.example.springjpaground.example.domain.Team;
import com.example.springjpaground.example.domain.repository.MemberRepository;
import net.bytebuddy.asm.Advice.Local;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public MemberResponseWithOf findByTeamWithOf(Team team) {
        return memberRepository.findByTeamOf(team);
    }

    public MemberResponseWithFrom findByTeamWithFrom(Team team) {
        return memberRepository.findByTeamFrom(team);
    }

    public MemberResponseWithNoStaticFactory findByTeamWithNoStaticFactoryMethod(Team team) {
        return memberRepository.findByTeamNoStaticFactory(team);
    }

    public Member get(Long id) {
        return memberRepository.findById(id)
            .orElseThrow();
    }
}
