package com.fayezkassar.bank_account.service;

import com.fayezkassar.bank_account.model.Account;
import com.fayezkassar.bank_account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Resource
    AccountRepository accountRepository;

    public Account createOrUpdateAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        var optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            log.error("No records found with accountNumber {}", accountNumber);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Data not found"
            );
        }
    }
}
