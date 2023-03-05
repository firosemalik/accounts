package com.anz.accounts.controller;

import com.anz.accounts.api.Transaction;
import com.anz.accounts.api.Transactions;
import com.anz.accounts.controller.validator.RequiredHeaders;
import com.anz.accounts.service.AccountTransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequiredArgsConstructor
public class AccountTransactionsController {

    private final AccountTransactionsService service;

    @GetMapping(value = "v{version}/accounts/{accountId}/transactions")
    public ResponseEntity<Transactions> getTransactions(@PathVariable final int version, @PathVariable final long accountId,
                                                        @RequestHeader @RequiredHeaders final HttpHeaders httpHeaders) {
        List<Transaction> transactionList = this.service.getTransactionsByAccount(accountId, httpHeaders);
        Transactions transactions = Transactions.builder().transactions(transactionList).build();
        Link selfRel = linkTo(methodOn(AccountTransactionsController.class).getTransactions(version, accountId, httpHeaders)).withSelfRel();
        Link accounts = linkTo(methodOn(AccountsController.class).getAccounts(version, httpHeaders)).withRel("accounts");
        return new ResponseEntity<>(transactions.add(selfRel, accounts), HttpStatus.OK);
    }
}
