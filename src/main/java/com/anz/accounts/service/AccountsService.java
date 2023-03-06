package com.anz.accounts.service;

import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.controller.exception.ResourceNotFoundException;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.entity.Account;
import com.anz.accounts.service.external.UserService;
import com.anz.accounts.service.util.DateTimeFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountsService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public List<com.anz.accounts.api.Account> getAccounts(HttpHeaders httpHeaders) {
        long customerId = this.userService.getCustomerIdByToken(httpHeaders.getFirst(MandatoryHeaders.ACCESS_TOKEN));

        List<Account> accounts = this.accountRepository.getAccountsByCustomerId(customerId);
        if (accounts.isEmpty()) {
            log.warn("trace_id={} Account not found for customer_id={}", httpHeaders.getFirst(MandatoryHeaders.TRACE_ID), customerId);
            throw new ResourceNotFoundException("Accounts not found for customer id ".concat(String.valueOf(customerId)));
        }
        return mapToAccounts(accounts);
    }

    private List<com.anz.accounts.api.Account> mapToAccounts(List<Account> accounts) {
        return accounts.stream().map(a -> com.anz.accounts.api.Account.builder()
                        .accountName(a.getAccountName())
                        .accountNumber(a.getAccountNumber())
                        .accountType(a.getAccountType())
                        .currency(a.getCurrency())
                        .balanceAmount(a.getBalanceAmount())
                        .balancedDateTime(a.getBalancedDateTime().format(DateTimeFormat.FORMATTER))
                        .customerId(a.getCustomerId())
                        .build())
                .collect(Collectors.toList());
    }
}
