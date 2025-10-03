package com.atalaykaan.bankservicebackend.controller;

import com.atalaykaan.bankservicebackend.dto.request.update.WalletActionRequest;
import com.atalaykaan.bankservicebackend.dto.response.WalletDTO;
import com.atalaykaan.bankservicebackend.dto.request.create.CreateWalletRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateWalletRequest;
import com.atalaykaan.bankservicebackend.service.WalletService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/wallets")
    public ResponseEntity<List<WalletDTO>> getAllWallets() {

        List<WalletDTO> walletDTOs = walletService
                .findAllWallets()
                .stream()
                .toList();

        return ResponseEntity.ok(walletDTOs);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> getWalletById(@PathVariable Long id) {

        WalletDTO walletDTO = walletService.findWalletDtoById(id);

        return ResponseEntity.ok(walletDTO);
    }

    @PostMapping("/wallets")
    public ResponseEntity<WalletDTO> createWallet(@Valid @RequestBody CreateWalletRequest createWalletRequest) {

        WalletDTO walletDTO = walletService.createWallet(createWalletRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(walletDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(walletDTO);
    }

    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@PathVariable Long id, @Valid @RequestBody UpdateWalletRequest updateWalletRequest) {

        WalletDTO walletDTO = walletService.updateWallet(id, updateWalletRequest);

        return ResponseEntity.ok(walletDTO);
    }

    @PutMapping("/wallets/{id}/withdraw")
    public ResponseEntity<WalletDTO> withdrawMoney(@PathVariable Long id, @Valid @RequestBody WalletActionRequest walletActionRequest) {

        WalletDTO walletDTO = walletService.withdrawMoney(id, walletActionRequest);

        return ResponseEntity.ok(walletDTO);
    }

    @PutMapping("/wallets/{id}/deposit")
    public ResponseEntity<WalletDTO> depositMoney(@PathVariable Long id, @Valid @RequestBody WalletActionRequest walletActionRequest) {

        WalletDTO walletDTO = walletService.depositMoney(id, walletActionRequest);

        return ResponseEntity.ok(walletDTO);
    }

    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {

        walletService.deleteWallet(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/wallets")
    public ResponseEntity<Void> deleteAllWallets() {

        walletService.deleteAllWallets();

        return ResponseEntity.noContent().build();
    }
}
