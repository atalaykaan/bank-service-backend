package com.atalaykaan.bankservicebackend.service.impl;

import com.atalaykaan.bankservicebackend.dto.AccountDTO;
import com.atalaykaan.bankservicebackend.exception.AccountNotFoundException;
import com.atalaykaan.bankservicebackend.mapper.impl.AccountMapper;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.repository.AccountRepository;
import com.atalaykaan.bankservicebackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public List<AccountDTO> findAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .toList();
    }

    public AccountDTO findAccountById(Long id) {

        return accountRepository.findById(id)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {

        Account account = accountMapper.fromDTO(accountDTO);

        account.setCreatedAt(LocalDateTime.now());

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDTO(savedAccount);
    }
}
