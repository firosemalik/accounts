package com.anz.accounts.api;

import com.anz.accounts.repository.entity.Currency;
import com.anz.accounts.repository.entity.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Transaction {
    private String accountNumber;
    private String accountName;
    private TransactionType transactionType;
    private ZonedDateTime valueDate;
    private Currency currency;
    private BigDecimal creditAmount;
    private BigDecimal debitAmount;
    private String transactionNarrative;
}
