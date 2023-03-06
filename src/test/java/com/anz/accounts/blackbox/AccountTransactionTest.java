package com.anz.accounts.blackbox;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.ApiError;
import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.api.Transaction;
import com.anz.accounts.api.Transactions;
import com.anz.accounts.repository.entity.Currency;
import com.anz.accounts.repository.entity.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = AccountsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/cleanup.sql", "/db/accounts.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountTransactionTest {

    public static final String V_1_ACCOUNTS = "http://localhost:8080/v1/accounts/";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getTransactions_withAccountHoldingTransaction_200() {
        EntityExchangeResult<Transactions> response = webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("102/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "102")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Transactions.class).returnResult();
        Transactions transactions = response.getResponseBody();
        assertEquals(transactions.getTransactions().size(), 1);

        Transaction transaction = transactions.getTransactions().get(0);
        assertEquals(transaction.getTransactionType(), TransactionType.CREDIT);
        assertEquals(transaction.getAccountName(), "AUCurrent626");
        assertEquals(transaction.getAccountNumber(), "385309209");
        assertEquals(transaction.getCurrency(), Currency.AUD);
        assertNull(transaction.getCreditAmount());
        assertEquals(transaction.getDebitAmount().toString(), "3000.29");
        assertEquals(transaction.getValueDate(), "04/25/2022 - 15:12:56 AEST");
        assertNull(transaction.getTransactionNarrative());
    }

    @Test
    void getTransactions_withAccountHoldingMultipleTransactions_200() {
        EntityExchangeResult<Transactions> response = webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("100/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Transactions.class).returnResult();
        Transactions transactions = response.getResponseBody();
        assertEquals(transactions.getTransactions().size(), 2);
    }

    @Test
    void getTransactions_withAccountHoldingNoTransactions_404() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("201/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    void getTransactions_withInvalidAccountId_400() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("ABC/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    void getTransactions_withoutTraceId_400() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("100/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    void getTransactions_withoutNullAccountId_400() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("null/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "200")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    void getTransactions_withoutAccessToken_401() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("100/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }
}
