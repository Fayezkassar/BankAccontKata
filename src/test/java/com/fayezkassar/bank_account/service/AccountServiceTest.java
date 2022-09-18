package com.fayezkassar.bank_account.service;

import com.fayezkassar.bank_account.model.Account;
import com.fayezkassar.bank_account.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    public void when_get_empty_account_throw_exception() {
        when(accountRepository.findByAccountNumber(any(String.class))).thenReturn(Optional.empty());
        Assert.assertThrows(ResponseStatusException.class, () -> accountService.getAccountByAccountNumber("12345"));
    }

    @Test
    public void when_get_account_return_account() {
        String accountNumber = "12345";
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        when(accountRepository.findByAccountNumber(any(String.class))).thenReturn(Optional.of(account));
        Assert.assertEquals(accountNumber, accountService.getAccountByAccountNumber("12345").getAccountNumber());
    }
}
