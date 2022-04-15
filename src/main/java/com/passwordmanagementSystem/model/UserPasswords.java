package com.passwordmanagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Data
public class UserPasswords {
    private String yourPassword;
    private String name;
    private String url;
    private String siteUsername;
    private String sitePassword;
    private String email;
}
