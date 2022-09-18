package com.fayezkassar.bank_account.model.dto;

import com.fayezkassar.bank_account.model.OperationType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OperationDTO {
    @NotNull
    private OperationType operationType;
    @NotBlank()
    private String accountNumber;
    @Min(value = 1)
    private double operationAmount;
}
