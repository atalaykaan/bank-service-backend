package com.atalaykaan.bankservicebackend.mapper.impl;

import com.atalaykaan.bankservicebackend.dto.AccountDTO;
import com.atalaykaan.bankservicebackend.mapper.Mapper;
import com.atalaykaan.bankservicebackend.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountDTO> {

    public AccountDTO toDTO(Account account) {

        return new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getCurrency()
        );
    }

    public Account fromDTO(AccountDTO accountDTO) {

        return new Account(
                accountDTO.getId(),
                accountDTO.getBalance(),
                accountDTO.getCurrency(),
                null
        );
    }
}
