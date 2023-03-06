package com.anz.accounts.repository.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "ACCOUNT_LIST")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "CUSTOMER_ID")
    @NotNull
    private Long customerId;

    @Column(name = "ACCOUNT_NUMBER")
    @Length(max = 20)
    @NotNull
    private String accountNumber;

    @Column(name = "ACCOUNT_NAME")
    @Length(max = 64)
    @NotNull
    private String accountName;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountType accountType;

    @Column(name = "BALANCED_TIME")
    @NotNull
    private ZonedDateTime balancedDateTime;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currency;

    @Column(name = "AVAILABLE_BALANCE")
    @NotNull
    private BigDecimal balanceAmount;

}
