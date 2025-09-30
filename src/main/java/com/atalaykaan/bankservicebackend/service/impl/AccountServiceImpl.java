package com.atalaykaan.bankservicebackend.service.impl;

import com.atalaykaan.bankservicebackend.dto.request.create.CreateAccountRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateAccountRequest;
import com.atalaykaan.bankservicebackend.exception.AccountNotFoundException;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.User;
import com.atalaykaan.bankservicebackend.repository.AccountRepository;
import com.atalaykaan.bankservicebackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final UserServiceImpl userService;

    public List<Account> findAllAccounts() {

        return Optional.of(accountRepository.findAll())
                .orElseThrow(() -> new AccountNotFoundException("No accounts were found"));
    }

    public Account findAccountById(Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }

    @Transactional
    public Account createAccount(CreateAccountRequest createAccountRequest) {

        User foundUser = userService.findUserById(createAccountRequest.getUserId());

        Account account = Account.builder()
                .user(foundUser)
                .createdAt(LocalDateTime.now())
                .build();

        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Long id, UpdateAccountRequest updateAccountRequest) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        User user = userService.findUserById(updateAccountRequest.getUserId());

        account.setUser(user);

        return accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Long id) {

        accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        accountRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllAccounts() {

        if(accountRepository.findAll().isEmpty()) {

            throw new AccountNotFoundException("No accounts were found");
        }

        accountRepository.deleteAll();
    }
}
