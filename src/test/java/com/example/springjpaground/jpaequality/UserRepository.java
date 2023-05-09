package com.example.springjpaground.jpaequality;

import com.example.springjpaground.jpaequality.EqualityTest.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
