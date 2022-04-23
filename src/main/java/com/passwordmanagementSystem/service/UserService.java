package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.userRequests.DeleteUser;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.requests.userRequests.UpdateUserPasswordRequest;
import com.passwordmanagementSystem.dtos.requests.userRequests.UserRequest;
import com.passwordmanagementSystem.dtos.responses.userResponses.*;
import com.passwordmanagementSystem.model.User;

import java.util.List;


public interface UserService {
    AccountResponse createAccount(UserRequest request);

    long count();

    FindUserResponse findUserByDetails(String email);

    UpdateUserPasswordResponse updateUserAccount(UpdateUserPasswordRequest request);

    LoginResponse login(LoginDetails request);

    DeleteResponse deleteAccount(DeleteUser request);


    List<User> findAll();

    void deleteAll();
}

