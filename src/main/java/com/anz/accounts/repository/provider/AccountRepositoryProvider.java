package com.anz.accounts.repository.provider;

import com.anz.accounts.repository.model.AccountList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepositoryProvider extends CrudRepository<AccountList, Long> {

    List<AccountList> findAllByCustomerId(long customerId);

}
