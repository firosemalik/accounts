package com.anz.accounts.repository;

import com.anz.accounts.repository.model.Account;
import com.anz.accounts.repository.provider.AccountRepositoryProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccountRepository {

    private final AccountRepositoryProvider accountRepositoryProvider;

    public List<Account> getAccounts(long customerId) {

        accountRepositoryProvider.save(Account.builder().accountName("checking").accountNumber("12345").build());

        return accountRepositoryProvider.findAllByCustomerId(customerId);
    }
}
