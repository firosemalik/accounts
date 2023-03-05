package com.anz.accounts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Transactions {
    private List<Transaction> transactions;
}
