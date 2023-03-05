package com.anz.accounts.repository;

import com.anz.accounts.repository.model.AccountList;
import com.anz.accounts.repository.provider.AccountRepositoryProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccountRepository {

    private final AccountRepositoryProvider accountRepositoryProvider;

    public List<AccountList> getAccountsByCustomerId(long customerId) {
        return accountRepositoryProvider.findAllByCustomerId(customerId);
    }
}
