package com.anz.accounts.controller;

import com.anz.accounts.api.AccountList;
import com.anz.accounts.api.Accounts;
import com.anz.accounts.component.service.AccountsService;
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
public class AccountsController {

    private final AccountsService accountsService;

    @GetMapping(value = "v{version}/accounts/{customerId}")
    public ResponseEntity<AccountList> getAccounts(@PathVariable final int version, @PathVariable final long customerId,
                                                   @RequestHeader final HttpHeaders httpHeaders) {
        List<Accounts> accounts = this.accountsService.getAccounts(customerId);
        return new ResponseEntity<>(AccountList.builder().accounts(accounts).build(), HttpStatus.OK);
    }
}
