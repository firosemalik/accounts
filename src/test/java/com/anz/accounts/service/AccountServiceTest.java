package com.anz.accounts.service;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.Account;
import com.anz.accounts.api.MandatoryHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "200");
        List<Account> accounts = accountsService.getAccounts(httpHeaders);
        assertEquals(accounts.size(), 2);
    }
}
