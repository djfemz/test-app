package com.passwordmanagementSystem.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
@Data
public class PasswordManagerRegister {


    private String email;
    private String uniquePassword;
    private String userName;
    List<UserPasswords> passwords = new ArrayList<UserPasswords>();

}
