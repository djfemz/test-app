package com.passwordmanagementSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class User {

    @Id
    private String email;
    private String userPassword;
    List<WebsitePassword> passwords = new ArrayList<>();
    private boolean isLoggedIn;

}
