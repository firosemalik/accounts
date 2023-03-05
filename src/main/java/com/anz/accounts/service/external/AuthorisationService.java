package com.anz.accounts.service.external;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class AuthorisationService {

    public boolean isEntitled(long accountId, HttpHeaders httpHeaders) {
        //checks the user against the account
        return true;
    }

}
