package com.anz.accounts.repository.provider;

import com.anz.accounts.repository.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepositoryProvider extends CrudRepository<Account, Long> {

    List<Account> findAllByCustomerId(Long customerId);

}
