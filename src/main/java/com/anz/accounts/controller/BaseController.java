package com.anz.accounts.controller;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class BaseController {

    protected WebMvcLinkBuilder getTransactionsLink(int version, Long accountId, HttpHeaders httpHeaders) {
        return linkTo(methodOn(AccountTransactionsController.class).getTransactions(version, accountId, httpHeaders));
    }

    protected WebMvcLinkBuilder getAccountsLink(int version, HttpHeaders httpHeaders) {
        return linkTo(methodOn(AccountsController.class).getAccounts(version, httpHeaders));
    }
}
