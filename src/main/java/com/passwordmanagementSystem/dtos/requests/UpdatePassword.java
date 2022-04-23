package com.passwordmanagementSystem.dtos.requests;

import lombok.Data;

@Data
public class UpdatePassword {

    private String oldPassword;
    private String newPassword;
    private String url;
}
