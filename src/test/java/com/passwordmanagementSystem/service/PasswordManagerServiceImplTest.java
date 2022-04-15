package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.AccountCreationRequest;
import com.passwordmanagementSystem.dtos.requests.SitePasswordAdditionRequest;
import com.passwordmanagementSystem.dtos.responses.AccountCreationResponse;
import com.passwordmanagementSystem.model.UserPasswords;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@Slf4j
@SpringBootTest
class PasswordManagerServiceImplTest {
    @Autowired
    PasswordManagerService passwordManager;

    @Test
    void userCanCreateAccount(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacint01-");
        newAccount.setUserName("Jacy");
        passwordManager.createAccount(newAccount);

        assertThat(passwordManager.count(), is(1L));
    }

    @Test
    void registeredUserCanAddPassword(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacint01-");
        newAccount.setUserName("Jacy");
       AccountCreationResponse res = passwordManager.createAccount(newAccount);
        SitePasswordAdditionRequest request = new SitePasswordAdditionRequest();

        request.setEmail("agbonirojacinta@gmail.com");
        request.setName("Esther");
        request.setSitePassword("happy");
        request.setUrl("https:facebook.com");
        request.setYourPassword("Jacint01-");
        request.setSiteUsername("Jay");
        passwordManager.addPassword(request);

        assertThat(passwordManager.count(), is(1L));
        assertThat(passwordManager.listOfPasswords(), is(1));
    }


}