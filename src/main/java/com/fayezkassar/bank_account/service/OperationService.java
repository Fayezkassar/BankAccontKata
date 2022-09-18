package com.fayezkassar.bank_account.service;

import com.fayezkassar.bank_account.model.Account;
import com.fayezkassar.bank_account.model.Operation;
import com.fayezkassar.bank_account.model.OperationType;
import com.fayezkassar.bank_account.model.dto.OperationDTO;
import com.fayezkassar.bank_account.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OperationService {

    @Autowired
    AccountService accountService;

    @Resource
    OperationRepository operationRepository;

    public Operation performOperation(OperationDTO operationDTO) {
        Account account = accountService.getAccountByAccountNumber(operationDTO.getAccountNumber());
        if (operationDTO.getOperationType() == OperationType.DEPOSIT) {
            account.setBalance(account.getBalance() + operationDTO.getOperationAmount());
        } else {
            if (account.getBalance() - operationDTO.getOperationAmount() >= 0) {
                account.setBalance(account.getBalance() - operationDTO.getOperationAmount());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No sufficient Balance");
            }
        }
        Operation operationCreated = new Operation(operationDTO.getOperationType(), operationDTO.getOperationAmount(),account.getBalance(), account, new Date());
        accountService.createOrUpdateAccount(account);
        return operationRepository.save(operationCreated);
    }

    public List<Operation> getOperationsByAccountNumber(String accountNumber) {
        return operationRepository.getAllByAccountAccountNumber(accountNumber);
    }
}
