package com.atalaykaan.bankservicebackend.service;

import com.atalaykaan.bankservicebackend.dto.request.update.WalletActionRequest;
import com.atalaykaan.bankservicebackend.dto.response.WalletDTO;
import com.atalaykaan.bankservicebackend.dto.request.create.CreateWalletRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateWalletRequest;
import com.atalaykaan.bankservicebackend.exception.InsufficientFundsException;
import com.atalaykaan.bankservicebackend.exception.WalletNotFoundException;
import com.atalaykaan.bankservicebackend.exception.WalletWithCurrencyAlreadyExistsException;
import com.atalaykaan.bankservicebackend.mapper.impl.WalletMapper;
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

    private final WalletMapper walletMapper;

    private final AccountService accountService;

    public List<WalletDTO> findAllWallets() {

        return Optional.of(walletRepository.findAll())
                .filter(list -> !list.isEmpty())
                .map(wallets -> wallets.stream().map(walletMapper::toDTO).toList())
                .orElseThrow(() -> new WalletNotFoundException("No wallets were found"));
    }

    public WalletDTO findWalletDtoById(Long id) {

        return walletRepository
                .findById(id)
                .map(walletMapper::toDTO)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));
    }

    @Transactional
    public WalletDTO createWallet(CreateWalletRequest createWalletRequest) {

        Account foundAccount = accountService.findAccountById(createWalletRequest.getAccountId());

        Predicate<Wallet> checkIfWalletWithCurrencyAlreadyExists =
                wallet -> wallet.getCurrency().equals(createWalletRequest.getCurrency());

        foundAccount.getWallets()
                .stream()
                .filter(checkIfWalletWithCurrencyAlreadyExists)
                .findFirst()
                .ifPresent((wallet) -> {
                    throw new WalletWithCurrencyAlreadyExistsException("Wallet with currency " + createWalletRequest.getCurrency() + " already exists for account with id: " + createWalletRequest.getAccountId());
                });

        Wallet savedWallet = walletRepository.save(
                Wallet.builder()
                        .balance(createWalletRequest.getBalance())
                        .currency(createWalletRequest.getCurrency())
                        .account(foundAccount)
                        .build()
        );

        return walletMapper.toDTO(savedWallet);
    }

    @Transactional
    public WalletDTO updateWallet(Long id, UpdateWalletRequest updateWalletRequest) {

        Wallet foundWallet = walletRepository
                .findById(id).orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        Account account = accountService.findAccountById(updateWalletRequest.getAccountId());

        account.getWallets()
                .stream()
                .filter(wallet -> wallet.getCurrency().equals(updateWalletRequest.getCurrency()))
                .findFirst()
                .ifPresent((wallet) -> {
                    throw new WalletWithCurrencyAlreadyExistsException("Wallet with currency " + updateWalletRequest.getCurrency() + " already exists for account with id: " + updateWalletRequest.getAccountId());
                });

        foundWallet.setBalance(updateWalletRequest.getBalance());
        foundWallet.setCurrency(updateWalletRequest.getCurrency());
        foundWallet.setAccount(account);

        return walletMapper.toDTO(walletRepository.save(foundWallet));
    }

    @Transactional
    public WalletDTO depositMoney(Long id, WalletActionRequest walletActionRequest) {

        Wallet wallet = walletRepository
                .findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        wallet.setBalance(wallet.getBalance() + walletActionRequest.getAmount());

        return walletMapper.toDTO(walletRepository.save(wallet));
    }

    @Transactional
    public WalletDTO withdrawMoney(Long id, WalletActionRequest walletActionRequest) {

        Wallet wallet = walletRepository
                .findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        if(wallet.getBalance() < walletActionRequest.getAmount()) {

            throw new InsufficientFundsException("Withdraw amount cannot exceed the balance");
        }

        wallet.setBalance(wallet.getBalance() - walletActionRequest.getAmount());

        return walletMapper.toDTO(walletRepository.save(wallet));
    }

    @Transactional
    public void deleteWallet(Long id) {

        walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));

        walletRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllWallets() {

        if(walletRepository.findAll().isEmpty()) {

            throw new WalletNotFoundException("No wallets were found");
        }

        walletRepository.deleteAll();
    }

    protected Wallet findWalletById(Long id) {

        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with id: " + id));
    }
}
