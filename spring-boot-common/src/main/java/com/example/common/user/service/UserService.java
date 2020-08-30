package com.example.common.user.service;


import com.example.common.user.dto.UserDTO;

public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO save(UserDTO userDTO);

    boolean delete(Long id);
}
