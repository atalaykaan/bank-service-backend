package com.atalaykaan.bankservicebackend.controller;

import com.atalaykaan.bankservicebackend.dto.AccountDTO;
import com.atalaykaan.bankservicebackend.service.impl.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountServiceImpl;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {

        return ResponseEntity.ok(accountServiceImpl.findAllAccounts());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {

        return ResponseEntity.ok(accountServiceImpl.findAccountById(id));
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(accountServiceImpl.createAccount(accountDTO));
    }
}
