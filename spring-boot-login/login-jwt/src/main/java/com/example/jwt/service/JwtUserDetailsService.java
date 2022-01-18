package com.example.jwt.service;


import com.example.jwt.bean.UserEntity;
import com.example.jwt.bean.dto.UserDTO;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtUserDetailsService extends UserDetailsService {

  public UserDTO save(UserDTO user);
  Optional<UserEntity> findByUsername(String username);
}
