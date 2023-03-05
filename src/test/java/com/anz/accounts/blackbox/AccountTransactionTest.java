package com.anz.accounts.blackbox;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.ApiError;
import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.api.Transactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = AccountsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/cleanup.sql", "/db/accounts.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountTransactionTest {

    public static final String V_1_ACCOUNTS = "http://localhost:8080/v1/accounts/";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getTransactions_200() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("100/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Transactions.class);
    }

    @Test
    void getTransactions101_404() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS.concat("101/transactions"))
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }
}
