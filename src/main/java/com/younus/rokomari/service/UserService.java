package com.younus.rokomari.service;

import com.younus.rokomari.domain.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

public interface UserService extends UserDetailsService {
    String saveUser(UserDto userDto, BindingResult bindingResult);
}
