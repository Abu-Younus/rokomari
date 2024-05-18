package com.younus.rokomari.service;

import com.younus.rokomari.domain.UserDto;
import com.younus.rokomari.entity.RoleEntity;
import com.younus.rokomari.entity.UserEntity;
import com.younus.rokomari.repository.RoleRepository;
import com.younus.rokomari.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String saveUser(UserDto userDto, BindingResult bindingResult) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userDto", "password", "The password doesn't matched!"));
        }
        if(bindingResult.hasErrors()){
            return "pages/auth/register";
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setRoles(Arrays.asList(new RoleEntity("ROLE_USER")));

        userRepository.save(userEntity);

        return "redirect:/register?success";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid email or password!");
        }

        return new User(user.getEmail(), user.getPassword(), mapRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Collection<RoleEntity> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
