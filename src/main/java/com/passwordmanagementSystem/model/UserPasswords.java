package com.passwordmanagementSystem.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPasswords {
    private String yourPassword;
    private String name;
    @Id
    private String url;
    private String siteUsername;
    private String sitePassword;
    private String email;
}
