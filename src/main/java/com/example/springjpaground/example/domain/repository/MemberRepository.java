package com.example.springjpaground.example.domain.repository;

import com.example.springjpaground.example.application.dto.MemberResponseWithFrom;
import com.example.springjpaground.example.application.dto.MemberResponseWithNoStaticFactory;
import com.example.springjpaground.example.application.dto.MemberResponseWithOf;
import com.example.springjpaground.example.domain.Member;
import com.example.springjpaground.example.domain.Team;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m "
            + "FROM Member m "
            + "JOIN FETCH m.team "
            + "WHERE m.team = :team")
    MemberResponseWithOf findByTeamOf(@Param("team") Team team);

    @Query("SELECT m "
            + "FROM Member m "
            + "JOIN FETCH m.team "
            + "WHERE m.team = :team")
    MemberResponseWithFrom findByTeamFrom(@Param("team") Team team);

    @Query("SELECT m "
            + "FROM Member m "
            + "JOIN FETCH m.team "
            + "WHERE m.team = :team")
    MemberResponseWithNoStaticFactory findByTeamNoStaticFactory(@Param("team") Team team);

    default Member get(Long id){
        return findById(id)
            .orElseThrow(IllegalAccessError::new);
    }
}
