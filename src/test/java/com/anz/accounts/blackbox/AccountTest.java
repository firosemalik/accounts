package com.anz.accounts.blackbox;


import com.anz.accounts.AccountsApplication;
import com.anz.accounts.api.Accounts;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/cleanup.sql"})
public class AccountTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getAccounts_200() {
        ResponseEntity<Accounts> responseEntity = this.testRestTemplate.getForEntity("http://localhost:8080/v1/accounts/200", Accounts.class);
        Accounts body = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(body);
        assertEquals(2, body.getAccounts().size());
    }


    @Test
    void getAccounts_400() {
        ResponseEntity<Accounts> responseEntity = this.testRestTemplate.getForEntity("http://localhost:8080/v1/accounts/ABC", Accounts.class);
        Accounts body = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(body);
    }
}
