package com.passwordmanagementSystem.dtos.requests.passwordRequests;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class Password {

    @UniqueElements
    private String username;
    private String password;
    private String url;
    private String email;
}
