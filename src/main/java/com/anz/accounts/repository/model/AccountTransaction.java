package com.anz.accounts.repository.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "ACCOUNT_TRANSACTION")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private long transactionId;

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "ACCOUNT_ID", updatable = false, insertable = false)
    @Setter(AccessLevel.PRIVATE)
    private AccountList account;

    private long accountId;

    @Column(name = "VALUE_DATE")
    @NotNull
    private ZonedDateTime valueDate;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currency;

    @Column(name = "CREDIT_AMOUNT")
    private BigDecimal creditAmount;

    @Column(name = "DEBIT_AMOUNT")
    private BigDecimal debitAmount;

    @Column(name = "TRANSACTION_TYPE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType transactionType;

    @Column(name = "TRANSACTION_NARRATIVE")
    private String transactionNarrative;
}
