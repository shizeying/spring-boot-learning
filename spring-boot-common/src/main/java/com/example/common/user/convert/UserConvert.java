package com.example.common.user.convert;

import com.example.common.user.dto.UserDTO;
import com.example.common.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConvert {

    UserDTO convertDO2DTO(User user);

    User convertDTO2DO(UserDTO userDTO);
}