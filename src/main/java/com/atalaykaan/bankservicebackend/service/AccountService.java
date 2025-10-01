package com.atalaykaan.bankservicebackend.service;

import com.atalaykaan.bankservicebackend.dto.request.create.CreateAccountRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateAccountRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateUserRequest;
import com.atalaykaan.bankservicebackend.exception.AccountNotFoundException;
import com.atalaykaan.bankservicebackend.model.Account;
import com.atalaykaan.bankservicebackend.model.User;
import com.atalaykaan.bankservicebackend.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final UserService userService;

    public List<Account> findAllAccounts() {

        return Optional.of(accountRepository.findAll())
                .filter(list -> !list.isEmpty())
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

        foundUser.setAccount(account);

        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Long id, UpdateAccountRequest updateAccountRequest) {

        Account account = findAccountById(id);

        User user = userService.findUserById(updateAccountRequest.getUserId());

        account.setUser(user);

        return accountRepository.save(account);
    }

//    @Transactional
//    public Account addWalletToAccount(Long id, Wallet wallet) {
//
//        Account account = findAccountById(id);
//
//        Predicate<Wallet> checkIfWalletAlreadyExists = w -> w.getId().equals(wallet.getId());
//
//        if(account.getWallets()
//                .stream()
//                .anyMatch(checkIfWalletAlreadyExists)) {
//
//            throw new WalletAlreadyExistsException();
//        }
//
//        account.getWallets().add(wallet);
//
//
//    }

    @Transactional
    public void deleteAccount(Long id) {

        findAccountById(id);

        accountRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllAccounts() {

        findAllAccounts();

        accountRepository.deleteAll();
    }
}
