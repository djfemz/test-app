package com.passwordmanagementSystem.dtos.requests;

import lombok.Data;

@Data
public class AccountCreationRequest {

    private String email;
    private String uniquePassword;
    private String userName;

}
