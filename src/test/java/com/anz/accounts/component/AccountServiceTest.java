package com.anz.accounts.component;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.Account;
import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.controller.exception.ResourceNotFoundException;
import com.anz.accounts.repository.entity.AccountType;
import com.anz.accounts.repository.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AccountsApplication.class)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/cleanup.sql", "/db/accounts.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountServiceTest {

    @Autowired
    private AccountsService accountsService;

    @Test
    void getAccounts_withUserId300_returnAccounts() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "300");
        List<Account> accounts = accountsService.getAccounts(httpHeaders);
        assertEquals(accounts.size(), 1);
        Account account = accounts.get(0);
        assertEquals(account.getAccountName(), "AUCurrent626");
        assertEquals(account.getAccountNumber(), "385309209");
        assertEquals(account.getAccountType(), AccountType.CURRENT);
        assertEquals(account.getBalanceAmount().toString(), "84372.51");
        assertEquals(account.getCurrency(), Currency.AUD);
        assertEquals(account.getBalancedDateTime(), "04/23/2022 - 15:12:56 AEST");
        assertEquals(account.getCustomerId(), 300);
    }

    @Test
    void getAccounts_withUserId200_returnTwoAccounts() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "200");
        List<Account> accounts = accountsService.getAccounts(httpHeaders);
        assertEquals(accounts.size(), 2);
    }

    @Test
    void getAccounts_withUserWithoutAccounts_throwResourceNotFound() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "101");
        assertThatThrownBy(() -> accountsService.getAccounts(httpHeaders))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Accounts not found for customer id 101");
    }

}
