package com.fayezkassar.bank_account.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(unique = true)
    @NotBlank()
    private String accountNumber;
    @Column
    @Min(value = 0)
    private double balance;
}
