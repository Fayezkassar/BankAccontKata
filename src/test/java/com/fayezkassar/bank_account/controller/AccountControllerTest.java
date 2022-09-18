package com.fayezkassar.bank_account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fayezkassar.bank_account.model.Account;
import com.fayezkassar.bank_account.service.AccountService;
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

@SpringBootTest(classes = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@RunWith(SpringRunner.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private ObjectMapper mapper;
    private Account account;

    @Before()
    public void init() {
        mapper = new ObjectMapper();

        account = new Account();
        account.setAccountNumber("12345");
        account.setBalance(10.0);

    }

    @Test
    public void whenNegativeAmount_thenReturns400() throws Exception {
        account.setBalance(-10.0);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapper.writeValueAsString(account)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenNullAccountNumber_thenReturns400() throws Exception {
        account.setAccountNumber(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapper.writeValueAsString(account)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenCreateAccount_thenReturns201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapper.writeValueAsString(account)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
