package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.UpdatePassword;
import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.FindPassword;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.PasswordResponse;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.UpdatePasswordResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.DeleteResponse;
import com.passwordmanagementSystem.model.WebsitePassword;

import java.util.List;

public interface SitePasswordService {
    PasswordResponse createAccount(Password request);

    Long count();

    FindPassword findPasswordByUrl(String url, LoginDetails details);

    DeleteResponse deletePassword(String url,String email);

    UpdatePasswordResponse updatePassword(UpdatePassword updatePassword, String email);

    List<WebsitePassword> findAll();

    void deleteAll();
}
