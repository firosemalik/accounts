package com.anz.accounts.service;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest(classes = AccountsApplication.class)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/cleanup.sql", "/db/accounts.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountServiceTest {

    @Autowired
    private AccountsService accountsService;

    @Test
    void testGetAccounts() {
        List<Account> accounts = accountsService.getAccountsByCustomer(200);
        assertEquals(accounts.size(), 2);
    }
}
