package com.anz.accounts.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String type;
}
