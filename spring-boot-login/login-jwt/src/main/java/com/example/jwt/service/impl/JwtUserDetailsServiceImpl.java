package com.example.jwt.service.impl;


import com.example.jwt.bean.UserEntity;
import com.example.jwt.bean.UserEntity_;
import com.example.jwt.bean.dto.UserDTO;
import com.example.jwt.dao.UserDao;
import com.example.jwt.service.JwtUserDetailsService;
import com.example.utils.CustomerBeanUtils;
import io.vavr.control.Try;
import java.util.Collections;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private PasswordEncoder bcryptEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDao.findByUsername(username)
        .map(userEntity -> new User(userEntity.getUsername(), userEntity.getPassword(),
            Collections.emptyList()))
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));

  }

  @Override
  public UserDTO save(UserDTO user) {
    user.setPassword(bcryptEncoder.encode(user.getPassword()));
    return Optional.ofNullable(CustomerBeanUtils.updateConvert(UserEntity.class).apply(user))
        .map(userEntity -> Try.of(() -> userDao.save(userEntity))
            .getOrElseThrow(() -> new UsernameNotFoundException("用户名已存在")))
        .map(CustomerBeanUtils.updateConvert(UserDTO.class))
        .orElse(null);
  }

  @Override
  public Optional<UserEntity> findByUsername(String username) {
    return userDao.findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(
        UserEntity_.USERNAME), username))

        ;
  }
}
