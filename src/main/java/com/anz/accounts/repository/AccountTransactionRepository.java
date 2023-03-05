package com.anz.accounts.repository;

import com.anz.accounts.repository.model.AccountTransaction;
import com.anz.accounts.repository.provider.AccountTransactionRepositoryProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccountTransactionRepository {
    private final AccountTransactionRepositoryProvider provider;

    public List<AccountTransaction> getTransactionsByAccount(long accountId) {
        return this.provider.findAllByAccountId(accountId);
    }
}
