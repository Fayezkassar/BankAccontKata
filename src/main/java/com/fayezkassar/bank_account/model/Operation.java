package com.fayezkassar.bank_account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer id;
    @Column
    private OperationType operationType;
    @Column
    private double operationAmount;
    @Column
    private double balance;
    @ManyToOne()
    @JoinColumn(name="account_id", nullable=false)
    @JsonIgnore()
    private Account account;
    @Column
    private Date operationDate;

    public Operation(){}

    public Operation(OperationType operationType, double operationAmount, double balance, Account account, Date operationDate) {
        this.operationType = operationType;
        this.operationAmount = operationAmount;
        this.balance = balance;
        this.account = account;
        this.operationDate = operationDate;
    }
}
