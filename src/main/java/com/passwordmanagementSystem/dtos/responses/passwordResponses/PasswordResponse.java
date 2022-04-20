package com.passwordmanagementSystem.dtos.responses.passwordResponses;


import lombok.Data;

@Data
public class PasswordResponse {
    private String username;
    private String password;
    private String url;
}
