package com.fayezkassar.bank_account.service;

import com.fayezkassar.bank_account.model.Account;
import com.fayezkassar.bank_account.model.Operation;
import com.fayezkassar.bank_account.model.OperationType;
import com.fayezkassar.bank_account.model.dto.OperationDTO;
import com.fayezkassar.bank_account.repository.OperationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {

    @Mock
    OperationRepository operationRepository;

    @Mock
    AccountService accountService;

    @InjectMocks
    OperationService operationService;

    @Test
    public void when_deposit_return_operation() {
        Account account = new Account();
        account.setBalance(15.0);

        OperationDTO dto = new OperationDTO();
        dto.setAccountNumber("12345");
        dto.setOperationType(OperationType.DEPOSIT);
        dto.setOperationAmount(10.0);

        when(accountService.getAccountByAccountNumber(any(String.class))).thenReturn(account);
        when(operationRepository.save(any(Operation.class))).thenReturn(new Operation(dto.getOperationType(), dto.getOperationAmount(),account.getBalance(), account, new Date()));
        Assert.assertEquals(10.0, operationService.performOperation(dto).getOperationAmount(), 0);
    }

    @Test
    public void when_withdraw_return_operation() {
        Account account = new Account();
        account.setBalance(15.0);

        OperationDTO dto = new OperationDTO();
        dto.setAccountNumber("12345");
        dto.setOperationType(OperationType.WITHDRAW);
        dto.setOperationAmount(10.0);

        when(accountService.getAccountByAccountNumber(any(String.class))).thenReturn(account);
        when(operationRepository.save(any(Operation.class))).thenReturn(new Operation(dto.getOperationType(), dto.getOperationAmount(),account.getBalance(), account, new Date()));

        Assert.assertEquals(10.0, operationService.performOperation(dto).getOperationAmount(), 0);
    }

    @Test
    public void when_withdraw_invalid_amount_throwError() {
        Account account = new Account();
        account.setBalance(15.0);

        OperationDTO dto = new OperationDTO();
        dto.setAccountNumber("12345");
        dto.setOperationType(OperationType.WITHDRAW);
        dto.setOperationAmount(20.0);

        when(accountService.getAccountByAccountNumber(any(String.class))).thenReturn(account);

        Assert.assertThrows(ResponseStatusException.class, () -> operationService.performOperation(dto));
    }
}
