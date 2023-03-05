package com.anz.accounts.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
    SAVINGS("SAVINGS"),
    CURRENT("CURRENT");

    private final String type;


}
