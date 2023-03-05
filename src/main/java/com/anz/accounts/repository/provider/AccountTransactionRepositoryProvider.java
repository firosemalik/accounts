package com.anz.accounts.repository.provider;

import com.anz.accounts.repository.entity.AccountTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountTransactionRepositoryProvider extends CrudRepository<AccountTransaction, Long> {

    List<AccountTransaction> findAllByAccountId(Long accountId);

}
