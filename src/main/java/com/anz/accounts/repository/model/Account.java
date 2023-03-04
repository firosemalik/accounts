package com.anz.accounts.repository.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    private long customerId;
    private String accountNumber;
    private String accountName;
    private String accountType;
    private ZonedDateTime balancedDateTime;
    private String currency;
    private String balanceAmount;


}
