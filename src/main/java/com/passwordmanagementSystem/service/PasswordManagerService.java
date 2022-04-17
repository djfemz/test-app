package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.AccountCreationRequest;
import com.passwordmanagementSystem.dtos.requests.SitePasswordAdditionRequest;
import com.passwordmanagementSystem.dtos.responses.AccountCreationResponse;


public interface PasswordManagerService {
    AccountCreationResponse createAccount(AccountCreationRequest request);

    long count();

    void addPassword(SitePasswordAdditionRequest siteDetails);

    int listOfPasswords();


    String getPassword(String url);


}

