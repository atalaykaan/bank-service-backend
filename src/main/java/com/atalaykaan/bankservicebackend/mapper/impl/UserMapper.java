package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.response.UserDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDTO> {

    public User fromDTO(UserDTO userDTO) {

        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .birthDate(userDTO.getBirthDate())
                .build();
    }

    public UserDTO toDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .accountDtoId(Optional.ofNullable(user.getAccount())
                        .map(Account::getId)
                        .orElse(null))
                .birthDate(user.getBirthDate())
                .build();
    }
}
