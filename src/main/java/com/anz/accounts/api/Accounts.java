package com.anz.accounts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Accounts {

    private String customerName;
    private String accountNumber;
    private String accountName;
    private String accountType;
    private ZonedDateTime balancedDateTime;
    private String currency;
    private String balanceAmount;

}
