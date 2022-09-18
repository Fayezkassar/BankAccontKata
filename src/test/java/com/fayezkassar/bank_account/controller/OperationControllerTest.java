package com.fayezkassar.bank_account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fayezkassar.bank_account.model.Account;

import com.fayezkassar.bank_account.model.Operation;
import com.fayezkassar.bank_account.model.OperationType;
import com.fayezkassar.bank_account.model.dto.OperationDTO;
import com.fayezkassar.bank_account.service.OperationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = OperationController.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@RunWith(SpringRunner.class)
public class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationService operationService;

    private Account account;
    private Operation operation;
    private ObjectMapper mapper;
    private OperationDTO dto;

    @Before()
    public void init() {
        mapper = new ObjectMapper();

        account = new Account();
        account.setBalance(10.0);
        account.setAccountNumber("12345");

        operation = new Operation(OperationType.DEPOSIT, 10.0, account.getBalance(), account, new Date());

        dto = new OperationDTO();
        dto.setOperationType(OperationType.DEPOSIT);
        dto.setAccountNumber("12345");
        dto.setOperationAmount(10.0);
    }

    @Test
    public void whenNullOperationType_thenReturns400() throws Exception {
        dto.setOperationType(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/operation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenNegativeValue_thenReturns400() throws Exception {
        dto.setOperationAmount(-1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/operation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenNullAccountNumber_thenReturns400() throws Exception {
        dto.setAccountNumber(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/operation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenCreateNewOperation_thenReturns201() throws Exception {
        double operationAmount = 20.0;
        operation.setOperationAmount(operationAmount);
        when(operationService.performOperation(any(OperationDTO.class))).thenReturn(operation);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/operation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.operationAmount").value(operationAmount));
    }

    @Test
    public void whenGetOperations_thenReturns200() throws Exception {
        List operations = new ArrayList<>();
        operations.add(operation);
        when(operationService.getOperationsByAccountNumber(any(String.class))).thenReturn(operations);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/operation/12345")
                .accept(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
