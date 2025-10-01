package com.atalaykaan.bankservicebackend.controller;

import com.atalaykaan.bankservicebackend.dto.AccountDTO;
import com.atalaykaan.bankservicebackend.dto.request.create.CreateAccountRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateAccountRequest;
import com.atalaykaan.bankservicebackend.mapper.impl.AccountMapper;
import com.atalaykaan.bankservicebackend.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {

        List<AccountDTO> accountDTOs = accountService
                .findAllAccounts()
                .stream()
                .map(accountMapper::toDTO)
                .toList();

        return ResponseEntity.ok(accountDTOs);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {

        AccountDTO accountDTO = accountMapper.toDTO(accountService.findAccountById(id));

        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {

        AccountDTO accountDTO = accountMapper.toDTO(accountService.createAccount(createAccountRequest));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accountDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(accountDTO);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody UpdateAccountRequest updateAccountRequest) {

        AccountDTO accountDTO = accountMapper.toDTO(accountService.updateAccount(id, updateAccountRequest));

        return ResponseEntity.ok(accountDTO);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<Void> deleteAllAccounts() {

        accountService.deleteAllAccounts();

        return ResponseEntity.noContent().build();
    }
}
