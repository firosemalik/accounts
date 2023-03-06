package com.anz.accounts.component.external;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * This class is only for demonstration purpose
 * This should be a separate microservice where ms-account should make the call to access this.
 */
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

    public boolean isEntitled(long accountId, HttpHeaders httpHeaders) {
        if (accountId == 101) {
            return false;
        }
        return true;
    }
}
