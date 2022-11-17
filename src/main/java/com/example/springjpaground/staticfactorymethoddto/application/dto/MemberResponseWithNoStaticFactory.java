//package com.example.springjpaground.staticfactorymethoddto.application.dto;
//
//import com.example.springjpaground.staticfactorymethoddto.domain.Member;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class MemberResponseWithNoStaticFactory {
//
//    private Long id;
//    private String memberName;
//    private String teamName;
//
//    public MemberResponseWithNoStaticFactory(Long id, String memberName, String teamName) {
//        this.id = id;
//        this.memberName = memberName;
//        this.teamName = teamName;
//    }
//
//    public static MemberResponseWithNoStaticFactory notStaticFactory(Member member) {
//        return new MemberResponseWithNoStaticFactory(
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
