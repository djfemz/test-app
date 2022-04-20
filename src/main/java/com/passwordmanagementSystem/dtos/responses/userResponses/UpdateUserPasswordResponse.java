package com.passwordmanagementSystem.dtos.responses.userResponses;

import lombok.Data;

@Data
public class UpdateUserPasswordResponse {

    private String email;
    private String newPassword;
}
