package com.fayezkassar.bank_account.controller;

import com.fayezkassar.bank_account.model.Operation;
import com.fayezkassar.bank_account.model.dto.OperationDTO;
import com.fayezkassar.bank_account.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {

    private static final Logger log = LoggerFactory.getLogger(OperationController.class);

    @Resource
    OperationService operationService;

    @PostMapping()
    ResponseEntity<Operation> createOperation(@Valid @RequestBody OperationDTO operationDTO) {
        log.info("Create Account - START");
        Operation operationCreated = operationService.performOperation(operationDTO);
        log.info("Create Account - END");
        return new ResponseEntity<>(operationCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    ResponseEntity<List<Operation>> getAccountByAccountNumber(@PathVariable String accountNumber) {
        log.info("GET Account - START");
        List<Operation> operations = operationService.getOperationsByAccountNumber(accountNumber);
        log.info("GET Account - END");
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }
}
