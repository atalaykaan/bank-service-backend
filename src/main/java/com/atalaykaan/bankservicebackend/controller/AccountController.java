package com.atalaykaan.bankservicebackend.controller;

import com.atalaykaan.bankservicebackend.dto.AccountDTO;
import com.atalaykaan.bankservicebackend.dto.request.create.CreateAccountRequest;
import com.atalaykaan.bankservicebackend.mapper.impl.AccountMapper;
import com.atalaykaan.bankservicebackend.service.impl.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountMapper accountMapper;

    private final AccountServiceImpl accountServiceImpl;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {

        List<AccountDTO> accountDTOs = accountServiceImpl
                .findAllAccounts()
                .stream()
                .map(accountMapper::toDTO)
                .toList();

        return ResponseEntity.ok(accountDTOs);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {

        AccountDTO accountDTO = accountMapper.toDTO(accountServiceImpl.findAccountById(id));

        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {

        AccountDTO accountDTO = accountMapper.toDTO(accountServiceImpl.createAccount(createAccountRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
    }
}
