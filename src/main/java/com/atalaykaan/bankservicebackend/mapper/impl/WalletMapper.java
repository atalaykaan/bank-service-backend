package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.response.WalletDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WalletMapper implements Mapper<Wallet, WalletDTO> {

    public Wallet fromDTO(WalletDTO walletDTO) {

        return Wallet.builder()
                .id(walletDTO.getId())
                .balance(walletDTO.getBalance())
                .currency(walletDTO.getCurrency())
                .build();
    }

    public WalletDTO toDTO(Wallet wallet) {

        return WalletDTO.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .accountDtoId(Optional.ofNullable(wallet.getAccount())
                        .map(Account::getId)
                        .orElse(null))
                .build();
    }
}
