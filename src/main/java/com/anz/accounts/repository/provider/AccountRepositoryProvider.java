package com.anz.accounts.repository.provider;

import com.anz.accounts.repository.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepositoryProvider extends CrudRepository<Account, Long> {

    List<Account> findAllByCustomerId(long customerId);

}
