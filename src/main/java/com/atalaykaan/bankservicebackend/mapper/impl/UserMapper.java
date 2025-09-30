package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.UserDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDTO> {

    private final AccountMapper accountMapper;

    public User fromDTO(UserDTO userDTO) {

        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .account(accountMapper.fromDTO(userDTO.getAccountDTO()))
                .birthDate(userDTO.getBirthDate())
                .build();

    }

    public UserDTO toDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .accountDTO(accountMapper.toDTO(user.getAccount()))
                .birthDate(user.getBirthDate())
                .build();
    }
}
