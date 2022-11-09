# Java & Spring & JPA PlayGround



## 1. JPA Repository에서 반환하는 DTO에 정적팩터리 메서드가 존재하면 변환을 해준다.



우연히  DTO를 그대로 반환하다가 오류가 났어야 하는데 오류가 안나길래 궁금해서 테스트해봤다.

정적팩터리메서드 of, from 같은 값이 있으면 변환을 해주는 것 같다.

of, from의 이름을 다른걸로 바꿔봤더니 작동하지 않았다.

```java
    @Query("SELECT m "
            + "FROM Member m "
            + "JOIN FETCH m.team "
            + "WHERE m.team = :team")
    MemberResponseWithOf findByTeamOf(@Param("team") Team team);
```

```java
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

```



## 2. OneToOne에서 연관관계의 주인 쪽은 Lazy가 가능하지만, 연관관계 주인이 아닌쪽은 Eager로 작동한다.

추가로 findById는 fetch로 나가지만  findAll을 했을 경우 fetch하지 않는다.

추측으로는 JPQL에서 select를 보낼 때 조회하는 엔티티의 연관관계를 고려하지 않기때문에 엔티티를 생성한 뒤 연관관계의 엔티티를 가져온다고 생각했다.

```sql
## 연관관계의 주인인 Product를 조회하는 쿼리
Hibernate: 
    select
        product0_.id as id1_2_0_,
        product0_.product_price as product_2_2_0_ 
    from
        product product0_ 
    where
        product0_.id=?


## 연관관계의 주인이 아닌 ProductPrice를 조회하는 쿼리

Hibernate: 
    select
        productpri0_.id as id1_3_,
        productpri0_.price as price2_3_ 
    from
        product_price productpri0_

Hibernate: 
    select
        product0_.id as id1_2_0_,
        product0_.product_price as product_2_2_0_ 
    from
        product product0_ 
    where
        product0_.product_price=?
```



## 3. 낙관적 락은 @Version만 있어도 작동한다.

- 낙관적 락은 트랜잭션 충돌이 발생하지 않는다고 가정하고 사용해야한다.
- 만약 충돌이 발생하면 실제객체에서 잡지 못하고 외부로 응답하는 Transaction 프록시에서 잡아준다.
- delete, update 또한 낙관적 lockdㅡㄹ 걸어준다.



우리의 추측으로

- 비관적 락은 DB lock을 가져오는 개념이므로 더 간단하다.
- 낙관적 락이 더 늦게 나왔을 것이다 생각한다.
- 낙관적 락의 장점은 WAS가 DB에서 Lock이 걸린 데이터와 관련 없는 데이터를 바라볼 때 원활할거라 생각한다.

```java
    @Test
    @DisplayName("낙관적 락이 잘 작동한다.")
    void optimistic_lock() throws InterruptedException {
        Thread thread1 = new Thread(() -> boardService.update(1L));
        Thread thread2 = new Thread(() -> boardService.update(1L));

        thread1.start();
        thread2.start();
        
        thread1.join();
        thread2.join();
    }

```



```shell
Exception in thread "Thread-2" org.springframework.orm.ObjectOptimisticLockingFailureException: Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1; statement executed: update board set name=?, version=? where id=? and version=?; nested exception is org.hibernate.StaleStateException: Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1; statement executed: update board set name=?, version=? where id=? and version=?
```

