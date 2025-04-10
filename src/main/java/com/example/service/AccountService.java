package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    public Optional<Account> register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) return Optional.empty();
        if (account.getPassword() == null || account.getPassword().length() < 4) return Optional.empty();
        if (accountRepo.findByUsername(account.getUsername()).isPresent()) return Optional.of(new Account(-1, "", ""));

        return Optional.of(accountRepo.save(account));
    }

    public Optional<Account> login(Account account) {
        return accountRepo.findByUsername(account.getUsername())
                .filter(acc -> acc.getPassword().equals(account.getPassword()));
    }
}
