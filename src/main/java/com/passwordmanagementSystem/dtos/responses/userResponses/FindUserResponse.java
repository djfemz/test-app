package com.passwordmanagementSystem.dtos.responses.userResponses;


import com.passwordmanagementSystem.model.User;
import lombok.Data;

@Data
public class FindUserResponse {
    private String email;
    private String password;
}
