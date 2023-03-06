package com.anz.accounts.component;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.api.Transaction;
import com.anz.accounts.controller.exception.AuthorisationException;
import com.anz.accounts.controller.exception.ResourceNotFoundException;
import com.anz.accounts.repository.entity.Currency;
import com.anz.accounts.repository.entity.TransactionType;
import com.anz.accounts.service.AccountTransactionsService;
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
public class AccountTransactionsServiceTest {

    @Autowired
    private AccountTransactionsService transactionsService;

    @Test
    void getTransactions_withAccountId102_returnTransactions() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "300");
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        List<Transaction> transactions = transactionsService.getTransactionsByAccount(102, httpHeaders);
        Transaction transaction = transactions.get(0);
        assertEquals(transaction.getTransactionType(), TransactionType.CREDIT);
        assertEquals(transaction.getAccountName(), "AUCurrent626");
        assertEquals(transaction.getAccountNumber(), "385309209");
        assertEquals(transaction.getCurrency(), Currency.AUD);
        assertEquals(transaction.getCreditAmount(), null);
        assertEquals(transaction.getDebitAmount().toString(), "3000.29");
        assertEquals(transaction.getValueDate(), "04/25/2022 - 15:12:56 AEST");
        assertEquals(transaction.getTransactionNarrative(), null);
    }

    @Test
    void getTransactions_withAccountId101_notEntitled() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "300");
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        assertThatThrownBy(() -> transactionsService.getTransactionsByAccount(101, httpHeaders))
                .isInstanceOf(AuthorisationException.class)
                .hasMessage("User not entitled to read the account");
    }

    @Test
    void getTransactions_withAccountId201_RecordsNotFound() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "300");
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        assertThatThrownBy(() -> transactionsService.getTransactionsByAccount(201, httpHeaders))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Transactions not found for account id 201");
    }
}
