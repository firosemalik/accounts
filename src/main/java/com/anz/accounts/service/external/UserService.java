package com.anz.accounts.service.external;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public long getCustomerIdByToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Invalid token");
        }

        long customerId;
        try {
            customerId = Long.parseLong(token);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid token");
        }
        return customerId;
    }
}
