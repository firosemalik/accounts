package com.anz.accounts.controller;

import com.anz.accounts.api.Accounts;
import com.anz.accounts.controller.validator.RequiredHeaders;
import com.anz.accounts.component.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class AccountsController extends BaseController {

    private final AccountsService accountsService;

    @GetMapping(value = "v{version}/accounts")
    public ResponseEntity<Accounts> getAccounts(@PathVariable final int version,
                                                @RequestHeader @RequiredHeaders final HttpHeaders httpHeaders) {
        Accounts accounts = Accounts.builder().accounts(this.accountsService.getAccounts(httpHeaders)).build();
        accounts.add(getAccountsLink(version, httpHeaders).withSelfRel());
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}
