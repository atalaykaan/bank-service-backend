package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.response.UserDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.User;
import com.atalaykaan.bankservicebackend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDTO> {

//    private final AccountService accountService;
//
//    private final AccountMapper accountMapper;

    public User fromDTO(UserDTO userDTO) {

        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
//                .account(accountMapper.fromDTO(accountService.findAccountDtoById(userDTO.getAccountDtoId())))
                .birthDate(userDTO.getBirthDate())
                .build();

    }

    public UserDTO toDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .accountDtoId(Optional.ofNullable(user.getAccount())
                        .map(Account::getId)
                        .orElse(null))
                .birthDate(user.getBirthDate())
                .build();
    }
}
