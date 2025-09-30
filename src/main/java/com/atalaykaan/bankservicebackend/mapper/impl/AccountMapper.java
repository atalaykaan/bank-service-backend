package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.AccountDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper implements Mapper<Account, AccountDTO> {

    private final UserMapper userMapper;

    private final WalletMapper walletMapper;

    public Account fromDTO(AccountDTO accountDTO) {

        return Account.builder()
                .id(accountDTO.getId())
                .user(userMapper.fromDTO(accountDTO.getUserDTO()))
                .wallets(accountDTO.getWalletDTOs().stream().map(walletMapper::fromDTO).toList())
                .createdAt(accountDTO.getCreatedAt())
                .build();
    }

    public AccountDTO toDTO(Account account) {

        return AccountDTO.builder()
                .id(account.getId())
                .userDTO(userMapper.toDTO(account.getUser()))
                .walletDTOs(account.getWallets().stream().map(walletMapper::toDTO).toList())
                .createdAt(account.getCreatedAt())
                .build();
    }

}
