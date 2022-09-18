package com.fayezkassar.bank_account.repository;

import com.fayezkassar.bank_account.model.Operation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Integer> {
    List<Operation> getAllByAccountAccountNumber(String accountNumber);
}
