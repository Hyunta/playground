package com.example.springjpaground.example.application.dto;

import com.example.springjpaground.example.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberResponseWithOf {

    private Long id;
    private String memberName;
    private String teamName;

    public MemberResponseWithOf(Long id, String memberName, String teamName) {
        this.id = id;
        this.memberName = memberName;
        this.teamName = teamName;
    }

    public static MemberResponseWithOf of(Member member) {
        return new MemberResponseWithOf(
                member.getId(),
                member.getName(),
                member.getTeam().getName()
        );
    }

    @Override
    public String toString() {
        return "MemberResponse{" +
                "id=" + id +
                ", memberName='" + memberName + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
