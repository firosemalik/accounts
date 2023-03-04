package com.anz.accounts.component.service;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.Accounts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest(classes = AccountsApplication.class)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/accounts.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountsServiceTest {

    @Autowired
    private AccountsService accountsService;

    @Test
    void testGetAccounts() {
        List<Accounts> accounts = accountsService.getAccounts(200);
        assertEquals(accounts.size(), 2);
    }
}
