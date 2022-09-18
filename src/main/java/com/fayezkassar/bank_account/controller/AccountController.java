package com.fayezkassar.bank_account.controller;


import com.fayezkassar.bank_account.model.Account;
import com.fayezkassar.bank_account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Resource
    AccountService accountService;

    @PostMapping("")
    ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        log.info("Create Account - START");
        Account accountCreated = accountService.createOrUpdateAccount(account);
        log.info("Create Account - END");
        return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber) {
        log.info("GET Account - START");
        Account accountCreated = accountService.getAccountByAccountNumber(accountNumber);
        log.info("GET Account - END");
        return new ResponseEntity<>(accountCreated, HttpStatus.OK);
    }

}
