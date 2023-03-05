package com.anz.accounts.service;

import com.anz.accounts.api.Account;
import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.controller.exception.ResourceNotFoundException;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.model.AccountList;
import com.anz.accounts.service.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public List<Account> getAccounts(HttpHeaders httpHeaders) {
        long customerId = this.userService.getCustomerIdByToken(httpHeaders.getFirst(MandatoryHeaders.ACCESS_TOKEN));

        List<AccountList> accounts = this.accountRepository.getAccountsByCustomerId(customerId);
        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("Accounts not found for customer id ".concat(String.valueOf(customerId)));
        }
        return accounts.stream().map(a -> Account.builder()
                        .accountName(a.getAccountName())
                        .accountNumber(a.getAccountNumber())
                        .accountType(a.getAccountType())
                        .currency(a.getCurrency())
                        .balanceAmount(a.getBalanceAmount())
                        .balancedDateTime(a.getBalancedDateTime())
                        .customerId(a.getCustomerId())
                        .build())
                .collect(Collectors.toList());
    }
}
