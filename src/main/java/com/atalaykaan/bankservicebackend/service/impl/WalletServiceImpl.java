package com.atalaykaan.bankservicebackend.service.impl;

import com.atalaykaan.bankservicebackend.dto.request.create.CreateWalletRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateWalletRequest;
import com.atalaykaan.bankservicebackend.exception.InsufficientFundsException;
import com.atalaykaan.bankservicebackend.exception.WalletNotFoundException;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.Wallet;
import com.atalaykaan.bankservicebackend.repository.WalletRepository;
import com.atalaykaan.bankservicebackend.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    private AccountServiceImpl accountService;

    public List<Wallet> findAllWallets() {

        return Optional.of(walletRepository
                .findAll())
                .orElseThrow(() -> new WalletNotFoundException("No wallets were found"));
    }

    public Wallet findWalletById(Long id) {

        return walletRepository
                .findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));
    }

    @Transactional
    public Wallet createWallet(CreateWalletRequest createWalletRequest) {

        Account foundAccount = accountService.findAccountById(createWalletRequest.getAccountId());

        return Wallet.builder()
                .balance(createWalletRequest.getBalance())
                .currency(createWalletRequest.getCurrency())
                .account(foundAccount)
                .build();
    }

    public Wallet updateWallet(Long id, UpdateWalletRequest updateWalletRequest) {

        Wallet wallet = walletRepository
                .findById(updateWalletRequest.getId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        Account account = accountService.findAccountById(updateWalletRequest.getAccountId());

        wallet.setBalance(updateWalletRequest.getBalance());
        wallet.setCurrency(updateWalletRequest.getCurrency());
        wallet.setAccount(account);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet depositMoney(Long id, Double amount) {

        Wallet wallet = walletRepository
                .findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        wallet.setBalance(wallet.getBalance() + amount);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet withdrawMoney(Long id, Double amount) {

        Wallet wallet = walletRepository
                .findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        if(wallet.getBalance() < amount) {

            throw new InsufficientFundsException("Withdraw amount cannot exceed the balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);

        return walletRepository.save(wallet);
    }

    @Transactional
    public void deleteWallet(Long id) {

        walletRepository
                .findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        walletRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllWallets() {

        if(walletRepository.findAll().isEmpty()) {

            throw new WalletNotFoundException("No wallets were found");
        }
    }
}
