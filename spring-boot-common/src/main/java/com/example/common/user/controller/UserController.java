package com.example.common.user.controller;

import com.example.common.common.validate.group.Add;
import com.example.common.common.validate.group.Delete;
import com.example.common.common.validate.group.Update;
import com.example.common.user.dto.UserDTO;
import com.example.common.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/get/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id){
        UserDTO dto = userService.getUserById(id);
        log.info("{}",dto);
        return dto;

    }

    @PostMapping(value="/add")
    public UserDTO add(@RequestBody @Validated({Add.class}) UserDTO userDTO){
        log.info("{}",userDTO);
        return userService.save(userDTO);
    }

    @PostMapping(value="/update")
    public UserDTO update(@RequestBody @Validated({Update.class}) UserDTO userDTO){
        log.info("{}",userDTO);
        return userService.save(userDTO);
    }

    @DeleteMapping(value="/detele")
    public boolean delete(@Validated({Delete.class}) UserDTO userDTO){
        log.info("id：{}",userDTO.getId());
        return userService.delete(userDTO.getId());
    }
}
