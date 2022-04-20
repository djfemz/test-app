package com.passwordmanagementSystem.dtos.responses.passwordResponses;

import lombok.Data;

@Data
public class FindPassword {
    private String username;
    private String password;

}
