package com.anz.accounts.blackbox;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.Accounts;
import com.anz.accounts.api.ApiError;
import com.anz.accounts.api.MandatoryHeaders;
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
public class AccountsTest {

    @Autowired
    private WebTestClient webTestClient;
    public static final String V_1_ACCOUNTS = "http://localhost:8080/v1/accounts";

    @Test
    public void getAccounts_200() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Accounts.class);
    }

    @Test
    public void getAccounts_400() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "ABC")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }
}
