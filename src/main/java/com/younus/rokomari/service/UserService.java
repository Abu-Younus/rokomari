package com.younus.rokomari.service;

import com.younus.rokomari.domain.UserDto;
import com.younus.rokomari.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

public interface UserService extends UserDetailsService {
    //save user method
    String saveUser(UserDto userDto, BindingResult bindingResult);

    //find user by username method
    UserEntity findByUserName(String username);
}
