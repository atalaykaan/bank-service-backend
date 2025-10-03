package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.response.AccountDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.User;
import com.atalaykaan.bankservicebackend.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapper implements Mapper<Account, AccountDTO> {

    public Account fromDTO(AccountDTO accountDTO) {

        return Account.builder()
                .id(accountDTO.getId())
                .email(accountDTO.getEmail())
                .createdAt(accountDTO.getCreatedAt())
                .build();
    }

    public AccountDTO toDTO(Account account) {

        return AccountDTO.builder()
                .id(account.getId())
                .userDtoId(Optional.ofNullable(account.getUser()).map(User::getId).orElse(null))
                .email(account.getEmail())
                .walletIdList(Optional.ofNullable(account.getWallets())
                        .map(wallets -> wallets.stream().map(Wallet::getId).toList())
                        .orElse(null))
                .createdAt(account.getCreatedAt())
                .build();
    }
}