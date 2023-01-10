package com.example.login.repository;

import com.example.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("FROM UserEntity WHERE userName = ?1")
    public UserEntity findByUserName(String userName);

}
