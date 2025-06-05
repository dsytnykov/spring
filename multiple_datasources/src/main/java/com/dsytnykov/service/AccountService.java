package com.dsytnykov.service;

import com.dsytnykov.mysql.model.Account;
import com.dsytnykov.mysql.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Account> findAll() { return accountRepository.findAll(); }
}
