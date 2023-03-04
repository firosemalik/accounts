package com.anz.accounts.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String type;
}
