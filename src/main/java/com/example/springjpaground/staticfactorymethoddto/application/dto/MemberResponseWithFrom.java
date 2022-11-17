//package com.example.springjpaground.staticfactorymethoddto.application.dto;
//
//import com.example.springjpaground.staticfactorymethoddto.domain.Member;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class MemberResponseWithFrom {
//
//    private Long id;
//    private String memberName;
//    private String teamName;
//
//    public MemberResponseWithFrom(Long id, String memberName, String teamName) {
//        this.id = id;
//        this.memberName = memberName;
//        this.teamName = teamName;
//    }
//
//    public static MemberResponseWithFrom from(Member member) {
//        return new MemberResponseWithFrom(
//                member.getId(),
//                member.getName(),
//                member.getTeam().getName()
//        );
//    }
//
//    @Override
//    public String toString() {
//        return "MemberResponse{" +
//                "id=" + id +
//                ", memberName='" + memberName + '\'' +
//                ", teamName='" + teamName + '\'' +
//                '}';
//    }
//}
