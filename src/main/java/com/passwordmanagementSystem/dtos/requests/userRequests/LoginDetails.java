package com.passwordmanagementSystem.dtos.requests.userRequests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginDetails {
    @NotEmpty(message = "Id could not be empty or null.")
    @Size(min = 1, max = 36, message = "Id must contains exactly out of 36 characters.")
    @Email
    private String email;
    private String password;
}
