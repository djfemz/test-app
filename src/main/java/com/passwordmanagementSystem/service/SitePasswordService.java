package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.PasswordResponse;

public interface SitePasswordService {
    PasswordResponse createAccount(Password request);

    Long count();
}
