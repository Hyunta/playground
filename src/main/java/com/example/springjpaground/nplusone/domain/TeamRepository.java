package com.example.springjpaground.nplusone.domain;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * 1.joinFetch로 해결한다.
     */
    @Query("select t from Team t join fetch t.members")
    List<Team> findAllJoinFetch();

    /**
     * 2. batchSize와 fetch를 같이 사용해서 @OneToMany가 여러개일 경우 해결
     */
    @Modifying(clearAutomatically = true)
    @Query("select t from Team t join fetch t.items")
    List<Team> findAllJoinFetchWithItem();

    /**
     * 3. @EntityGraph 사용
     */
    @EntityGraph(attributePaths = "members")
    @Query("select t from Team t")
    List<Team> findAllEntityGraph();
}
