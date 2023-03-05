package com.anz.accounts.controller;

import com.anz.accounts.api.Transaction;
import com.anz.accounts.api.Transactions;
import com.anz.accounts.controller.validator.RequiredHeaders;
import com.anz.accounts.service.AccountTransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class AccountTransactionsController {

    private final AccountTransactionsService service;

    @GetMapping(value = "v{version}/accounts/{accountId}/transactions")
    public ResponseEntity<Transactions> getTransactions(@PathVariable final int version, @PathVariable final long accountId,
                                                        @RequestHeader @RequiredHeaders final HttpHeaders httpHeaders) {
        List<Transaction> transactions = this.service.getTransactionsByAccount(accountId);
        return new ResponseEntity<>(Transactions.builder().transactions(transactions).build(), HttpStatus.OK);
    }
}
