package com.dsytnykov.controller;

import com.dsytnykov.mysql.model.Account;
import com.dsytnykov.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

}
