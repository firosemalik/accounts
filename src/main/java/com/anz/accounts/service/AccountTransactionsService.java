package com.anz.accounts.service;

import com.anz.accounts.api.Transaction;
import com.anz.accounts.controller.exception.AuthorisationException;
import com.anz.accounts.controller.exception.ResourceNotFoundException;
import com.anz.accounts.repository.AccountTransactionRepository;
import com.anz.accounts.repository.model.AccountTransaction;
import com.anz.accounts.service.external.AuthorisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountTransactionsService {
    private final AccountTransactionRepository transactionRepository;
    private final AuthorisationService authorisationService;

    public List<Transaction> getTransactionsByAccount(long accountId, HttpHeaders httpHeaders) {

        boolean entitled = this.authorisationService.isEntitled(accountId, httpHeaders);
        if (!entitled) {
            throw new AuthorisationException("User not entitled to read the account");
        }

        List<AccountTransaction> transactions = this.transactionRepository.getTransactionsByAccount(accountId);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("Transactions not found for account id ".concat(String.valueOf(accountId)));
        }
        return transactions.stream().map(t -> Transaction.builder()
                .accountNumber(t.getAccount().getAccountNumber())
                .accountName(t.getAccount().getAccountName())
                .transactionType(t.getTransactionType())
                .transactionNarrative(t.getTransactionNarrative())
                .currency(t.getAccount().getCurrency())
                .creditAmount(t.getCreditAmount())
                .debitAmount(t.getDebitAmount())
                .valueDate(t.getValueDate()).build()).collect(Collectors.toList());
    }
}
