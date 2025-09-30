package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.WalletDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletMapper implements Mapper<Wallet, WalletDTO> {

    private final AccountMapper accountMapper;

    public Wallet fromDTO(WalletDTO walletDTO) {

        return Wallet.builder()
                .id(walletDTO.getId())
                .balance(walletDTO.getBalance())
                .currency(walletDTO.getCurrency())
                .account(accountMapper.fromDTO(walletDTO.getAccountDTO()))
                .build();
    }
    public WalletDTO toDTO(Wallet wallet) {

        return WalletDTO.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .accountDTO(accountMapper.toDTO(wallet.getAccount()))
                .build();
    }
}
