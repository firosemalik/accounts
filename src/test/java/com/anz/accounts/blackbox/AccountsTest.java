package com.anz.accounts.blackbox;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.Account;
import com.anz.accounts.api.Accounts;
import com.anz.accounts.api.ApiError;
import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.repository.entity.AccountType;
import com.anz.accounts.repository.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AccountsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/cleanup.sql", "/db/accounts.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountsTest {

    @Autowired
    private WebTestClient webTestClient;
    public static final String V_1_ACCOUNTS = "http://localhost:8080/v1/accounts";

    @Test
    public void getAccounts_withUserHoldingAccount_200() {
        EntityExchangeResult<Accounts> response = webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "300")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Accounts.class)
                .returnResult();
        Accounts accounts = response.getResponseBody();
        assertEquals(accounts.getAccounts().size(), 1);
        Account account = accounts.getAccounts().get(0);
        assertEquals(account.getAccountName(), "AUCurrent626");
        assertEquals(account.getAccountNumber(), "385309209");
        assertEquals(account.getAccountType(), AccountType.CURRENT);
        assertEquals(account.getBalanceAmount().toString(), "84372.51");
        assertEquals(account.getCurrency(), Currency.AUD);
        assertEquals(account.getBalancedDateTime(), "04/23/2022 - 15:12:56 AEST");
        assertEquals(account.getCustomerId(), 300);
    }

    @Test
    public void getAccounts_withUserHoldingMultipleAccounts_200() {
        EntityExchangeResult<Accounts> response = webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "200")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Accounts.class)
                .returnResult();
        Accounts accounts = response.getResponseBody();
        assertEquals(accounts.getAccounts().size(), 2);
    }

    @Test
    public void getAccounts_withUserNotHoldingAccounts_404() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .header(MandatoryHeaders.ACCESS_TOKEN, "100")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    public void getAccounts_withoutAccessToken_401() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.TRACE_ID, "123")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    public void getAccounts_withInvalidAccessToken_400() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.ACCESS_TOKEN, "ABC")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    public void getAccounts_withoutTraceHeader_400() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.ACCESS_TOKEN, "123")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }

    @Test
    public void getAccounts_withEmptyTraceHeader_400() {
        webTestClient.get()
                .uri(V_1_ACCOUNTS)
                .accept(MediaType.APPLICATION_JSON)
                .header(MandatoryHeaders.ACCESS_TOKEN, "123")
                .header(MandatoryHeaders.TRACE_ID, "")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class);
    }


}
