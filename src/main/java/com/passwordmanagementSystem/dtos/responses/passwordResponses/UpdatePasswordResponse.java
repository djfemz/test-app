package com.passwordmanagementSystem.dtos.responses.passwordResponses;


import lombok.Data;

@Data
public class UpdatePasswordResponse {
    private String newPassword;
    private String url;
}
