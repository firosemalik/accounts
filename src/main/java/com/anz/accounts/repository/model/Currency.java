package com.anz.accounts.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    SGD("SGD"),
    AUD("AUD");

    private final String currency;
}
