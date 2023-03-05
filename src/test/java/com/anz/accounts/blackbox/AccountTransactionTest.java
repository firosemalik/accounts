package com.anz.accounts.blackbox;

import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.api.Transactions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = AccountsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/cleanup.sql", "/db/accounts.sql"})
//@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountTransactionTest {

    public static final String V_1_ACCOUNTS = "http://localhost:8080/v1/accounts/";
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getAccounts_200() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "200");
        ResponseEntity<Transactions> responseEntity = this.testRestTemplate
                .exchange(V_1_ACCOUNTS + "100/transactions", HttpMethod.GET, new HttpEntity<>(httpHeaders), Transactions.class);
        Transactions body = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(body);
        assertEquals(2, body.getTransactions().size());
    }

    @Test
    void getAccounts101_404() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(MandatoryHeaders.TRACE_ID, "123");
        httpHeaders.add(MandatoryHeaders.ACCESS_TOKEN, "200");
        ResponseEntity<Transactions> responseEntity = this.testRestTemplate
                .exchange(V_1_ACCOUNTS + "101/transactions", HttpMethod.GET, new HttpEntity<>(httpHeaders), Transactions.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
