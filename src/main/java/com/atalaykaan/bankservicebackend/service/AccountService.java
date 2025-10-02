package com.atalaykaan.bankservicebackend.service;

import com.atalaykaan.bankservicebackend.dto.response.AccountDTO;
import com.atalaykaan.bankservicebackend.dto.response.UserDTO;
import com.atalaykaan.bankservicebackend.dto.request.create.CreateAccountRequest;
import com.atalaykaan.bankservicebackend.dto.request.update.UpdateAccountRequest;
import com.atalaykaan.bankservicebackend.exception.AccountNotFoundException;
import com.atalaykaan.bankservicebackend.exception.UserWithAccountExistsException;
import com.atalaykaan.bankservicebackend.mapper.impl.AccountMapper;
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

    private final AccountMapper accountMapper;

    public List<AccountDTO> findAllAccounts() {

        return Optional.of(accountRepository.findAll())
                .filter(list -> !list.isEmpty())
                .map(accounts -> accounts.stream().map(accountMapper::toDTO).toList())
                .orElseThrow(() -> new AccountNotFoundException("No accounts were found"));
    }

    public AccountDTO findAccountDtoById(Long id) {

        return accountRepository.findById(id)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }

    @Transactional
    public AccountDTO createAccount(CreateAccountRequest createAccountRequest) {

        User foundUser = userService.findUserById(createAccountRequest.getUserId());

        if(foundUser.getAccount() != null) {

            throw new UserWithAccountExistsException("This user already has an account");
        }

        Account account = Account.builder()
                .user(foundUser)
                .createdAt(LocalDateTime.now())
                .build();

        userService.addAccountToUser(account, foundUser);

        return accountMapper.toDTO(accountRepository.save(account));
    }

    @Transactional
    public AccountDTO updateAccount(Long id, UpdateAccountRequest updateAccountRequest) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        User user = userService.findUserById(updateAccountRequest.getUserId());

        account.setUser(user);

        userService.addAccountToUser(account, user);

        return accountMapper.toDTO(accountRepository.save(account));
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

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

        account.getUser().setAccount(null);

        accountRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllAccounts() {

        if(accountRepository.findAll().isEmpty()) {

            throw new AccountNotFoundException("No accounts were found");
        }

        accountRepository.findAll()
                .forEach(account -> account.getUser().setAccount(null));

        accountRepository.deleteAll();
    }

    protected Account findAccountById(Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }
}
