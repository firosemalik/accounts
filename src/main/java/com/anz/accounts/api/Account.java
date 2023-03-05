package com.anz.accounts.api;

import com.anz.accounts.repository.entity.AccountType;
import com.anz.accounts.repository.entity.Currency;
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
public class Account {

    private long customerId;
    private String accountNumber;
    private String accountName;
    private AccountType accountType;
    private ZonedDateTime balancedDateTime;
    private Currency currency;
    private BigDecimal balanceAmount;

}
