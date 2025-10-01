package com.atalaykaan.bankservicebackend.service;

import com.atalaykaan.bankservicebackend.dto.request.create.CreateWalletRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateWalletRequest;
import com.atalaykaan.bankservicebackend.exception.InsufficientFundsException;
import com.atalaykaan.bankservicebackend.exception.WalletNotFoundException;
import com.atalaykaan.bankservicebackend.exception.WalletWithCurrencyAlreadyExistsException;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.Wallet;
import com.atalaykaan.bankservicebackend.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private final AccountService accountService;

    public List<Wallet> findAllWallets() {

        return Optional.of(walletRepository.findAll())
                .filter(list -> !list.isEmpty())
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

        Predicate<Wallet> checkIfWalletWithCurrencyAlreadyExists =
                wallet -> wallet.getCurrency().equals(createWalletRequest.getCurrency());

        if(foundAccount.getWallets()
                .stream()
                .anyMatch(checkIfWalletWithCurrencyAlreadyExists)) {

            throw new WalletWithCurrencyAlreadyExistsException("Wallet with currency " + createWalletRequest.getCurrency() + " already exists for account with id: " + createWalletRequest.getAccountId());
        }

        return Wallet.builder()
                .balance(createWalletRequest.getBalance())
                .currency(createWalletRequest.getCurrency())
                .account(foundAccount)
                .build();
    }

    @Transactional
    public Wallet updateWallet(Long id, UpdateWalletRequest updateWalletRequest) {

        Wallet foundWallet = findWalletById(updateWalletRequest.getId());

        Account foundAccount = accountService.findAccountById(updateWalletRequest.getAccountId());

        Predicate<Wallet> checkIfWalletWithCurrencyAlreadyExists =
                wallet -> wallet.getCurrency().equals(updateWalletRequest.getCurrency());

        if(foundAccount.getWallets()
                .stream()
                .anyMatch(checkIfWalletWithCurrencyAlreadyExists)) {

            throw new WalletWithCurrencyAlreadyExistsException("Wallet with currency " + updateWalletRequest.getCurrency() + " already exists for account with id: " + updateWalletRequest.getAccountId());
        }

        foundWallet.setBalance(updateWalletRequest.getBalance());
        foundWallet.setCurrency(updateWalletRequest.getCurrency());
        foundWallet.setAccount(foundAccount);

        return walletRepository.save(foundWallet);
    }

    @Transactional
    public Wallet depositMoney(Long id, Double amount) {

        Wallet wallet = findWalletById(id);

        wallet.setBalance(wallet.getBalance() + amount);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet withdrawMoney(Long id, Double amount) {

        Wallet wallet = findWalletById(id);

        if(wallet.getBalance() < amount) {

            throw new InsufficientFundsException("Withdraw amount cannot exceed the balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);

        return walletRepository.save(wallet);
    }

    @Transactional
    public void deleteWallet(Long id) {

        findWalletById(id);

        walletRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllWallets() {

        if(walletRepository.findAll().isEmpty()) {

            throw new WalletNotFoundException("No wallets were found");
        }
    }
}
