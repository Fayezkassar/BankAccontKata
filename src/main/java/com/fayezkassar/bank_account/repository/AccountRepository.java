package com.fayezkassar.bank_account.repository;

import com.fayezkassar.bank_account.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
