package com.anz.accounts.service;

import com.anz.accounts.api.Accounts;
import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountRepository accountRepository;

    public List<Accounts> getAccounts(long customerId){



        List<Account> accounts = this.accountRepository.getAccounts(customerId);
        return accounts.stream().map(a->Accounts.builder().accountName(a.getAccountName()).accountNumber(a.getAccountNumber()).build()).collect(Collectors.toList());
    }
}
