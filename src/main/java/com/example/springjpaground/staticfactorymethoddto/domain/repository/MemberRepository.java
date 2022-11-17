//package com.example.springjpaground.staticfactorymethoddto.domain.repository;
//
//import com.example.springjpaground.staticfactorymethoddto.application.dto.MemberResponseWithFrom;
//import com.example.springjpaground.staticfactorymethoddto.application.dto.MemberResponseWithNoStaticFactory;
//import com.example.springjpaground.staticfactorymethoddto.application.dto.MemberResponseWithOf;
//import com.example.springjpaground.staticfactorymethoddto.domain.Member;
//import com.example.springjpaground.staticfactorymethoddto.domain.Team;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
////@Repository
//public interface MemberRepository extends JpaRepository<Member, Long> {
//
//    @Query("SELECT m "
//            + "FROM Member m "
//            + "JOIN FETCH m.team "
//            + "WHERE m.team = :team")
//    MemberResponseWithOf findByTeamOf(@Param("team") Team team);
//
//    @Query("SELECT m "
//            + "FROM Member m "
//            + "JOIN FETCH m.team "
//            + "WHERE m.team = :team")
//    MemberResponseWithFrom findByTeamFrom(@Param("team") Team team);
//
//    @Query("SELECT m "
//            + "FROM Member m "
//            + "JOIN FETCH m.team "
//            + "WHERE m.team = :team")
//    MemberResponseWithNoStaticFactory findByTeamNoStaticFactory(@Param("team") Team team);
//
//    default Member get(Long id){
//        return findById(id)
//            .orElseThrow(IllegalAccessError::new);
//    }
//}
