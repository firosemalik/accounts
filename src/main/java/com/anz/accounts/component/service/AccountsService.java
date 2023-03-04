package com.anz.accounts.component.service;

import com.anz.accounts.api.Accounts;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.model.AccountList;
import com.anz.accounts.repository.model.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountRepository accountRepository;

    public List<Accounts> getAccounts(long customerId) {
        List<AccountList> accounts = this.accountRepository.getAccounts(customerId);
        return accounts.stream().map(a -> Accounts.builder()
                        .accountName(a.getAccountName())
                        .accountNumber(a.getAccountNumber())
                        .accountType(a.getAccountType())
                        .currency(a.getCurrency())
                        .balanceAmount(a.getBalanceAmount())
                        .balancedDateTime(a.getBalancedDateTime())
                        .customerName("XC")
                        .build())
                .collect(Collectors.toList());
    }
}
