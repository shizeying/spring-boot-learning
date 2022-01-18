package com.example.jwt.dao;

import com.example.jwt.bean.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer>,
    CrudRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
  Optional<UserEntity> findByUsername(String username);
}
