package com.softtech.finalproject.dao;

import com.softtech.finalproject.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Long> {
    boolean existsByUserName(String userName);
    UserEntity findByUserName(String userName);
}
