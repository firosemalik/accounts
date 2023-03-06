package com.anz.accounts.service;

import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.api.Transaction;
import com.anz.accounts.controller.exception.AuthorisationException;
import com.anz.accounts.controller.exception.ResourceNotFoundException;
import com.anz.accounts.repository.AccountTransactionRepository;
import com.anz.accounts.repository.entity.AccountTransaction;
import com.anz.accounts.service.external.UserService;
import com.anz.accounts.service.util.DateTimeFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionsService {
    private final AccountTransactionRepository transactionRepository;
    private final UserService userService;

    public List<Transaction> getTransactionsByAccount(long accountId, HttpHeaders httpHeaders) {
        final long customerId = userService.getCustomerIdByToken(httpHeaders.getFirst(MandatoryHeaders.ACCESS_TOKEN));
        final String traceId = httpHeaders.getFirst(MandatoryHeaders.TRACE_ID);
        if (!this.userService.isEntitled(accountId, httpHeaders)) {
            log.warn("trace_id={} User not entitled to read the account customer_id={}, account_id={}",
                    traceId, customerId, accountId);
            throw new AuthorisationException("User not entitled to read the account");
        }

        List<AccountTransaction> transactions = this.transactionRepository.getTransactionsByAccount(accountId);
        if (transactions.isEmpty()) {
            log.warn("trace_id={} User not entitled to read the account customer_id={}, account_id={}",
                    traceId, customerId, accountId);
            throw new ResourceNotFoundException("Transactions not found for account id ".concat(String.valueOf(accountId)));
        }

        return mapToTransactions(transactions);
    }

    private List<Transaction> mapToTransactions(List<AccountTransaction> transactions) {
        return transactions.stream().map(t -> Transaction.builder()
                .accountNumber(t.getAccount().getAccountNumber())
                .accountName(t.getAccount().getAccountName())
                .transactionType(t.getTransactionType())
                .transactionNarrative(t.getTransactionNarrative())
                .currency(t.getAccount().getCurrency())
                .creditAmount(t.getCreditAmount())
                .debitAmount(t.getDebitAmount())
                .valueDate(t.getValueDate().format(DateTimeFormat.FORMATTER))
                .build()).collect(Collectors.toList());
    }
}
