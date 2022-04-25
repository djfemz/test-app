package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.UpdatePassword;
import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.requests.userRequests.UserRequest;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.FindPassword;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.UpdatePasswordResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.DeleteResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.LoginResponse;
import com.passwordmanagementSystem.model.WebsitePassword;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SitePasswordServiceImplTest {
@Autowired
SitePasswordService passwordService;
@Autowired
    UserService service;

    @Test
    void passwordCanBeCreated(){
        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        service.createAccount(newAccount);


        LoginDetails request1 = new LoginDetails();
        request1.setEmail("agbonirojacinta@gmail.com");
        request1.setPassword("JayEst1-");

        Password request = new Password();
        request.setSitePassword("Jacinth1_");
        request.setUsername("JayEst");
        request.setUrl("https:facebook.com");
        request.setEmail("agbonirojacinta@gmail.com");
        request.setUserPassword("JayEst1-");

        passwordService.createAccount(request);

        LoginResponse result1 = service.login(request1);
        assertThat(result1.getMessage(), is("Welcome agbonirojacinta@gmail.com"));

        assertThat(passwordService.count(), is(1L));
    }

    @Test
    void passwordCanBeFound(){

        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        service.createAccount(newAccount);

        Password request = new Password();
        request.setSitePassword("Jacinth1_");
        request.setUsername("JayEst");
        request.setUrl("https:facebook.com");
        request.setEmail("agbonirojacinta@gmail.com");
        request.setUserPassword("JayEst1-");



        LoginDetails request1 = new LoginDetails();
        request1.setEmail("agbonirojacinta@gmail.com");
        request1.setPassword("JayEst1-");
        passwordService.createAccount(request);

        FindPassword result = passwordService.findPasswordByUrl("https:facebook.com",
                 request1);

        LoginResponse result1 = service.login(request1);
        assertThat(result1.getMessage(), is("Welcome agbonirojacinta@gmail.com"));
        assertThat(result.getPassword(), is("Jacinth1_"));
        assertThat(result.getUsername(), is("JayEst"));


    }
    @Test
    void passwordCanBeDeleted(){

        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        service.createAccount(newAccount);

        Password request = new Password();
        Password request1 = new Password();

        request.setSitePassword("Jacinth1_");
        request.setUsername("JayEst");
        request.setUrl("https:facebook.com");
        request.setEmail("agbonirojacinta@gmail.com");



        request1.setSitePassword("Jacinth1_");
        request1.setUsername("JayJay");
        request1.setUrl("https:instagram.com");
        request1.setEmail("agbonirojacinta@gmail.com");
        request1.setUserPassword("JayEst1-");


        passwordService.createAccount(request);
        passwordService.createAccount(request1);


        DeleteResponse result = passwordService.deletePassword("https:instagram.com"
        ,"agbonirojacinta@gmail.com");

        assertThat(result.getResponse(), is("deleted successfully"));
        assertThat(passwordService.count(), is(1L));


    }

    @Test
    void passwordCanBeUpdated(){
        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        service.createAccount(newAccount);

        Password request = new Password();

        request.setSitePassword("Jacinth1_");
        request.setUsername("JayEst");
        request.setUrl("https:facebook.com");
        request.setEmail("agbonirojacinta@gmail.com");
        request.setUserPassword("JayEst1-");

        passwordService.createAccount(request);

        UpdatePassword updatedPassword = new UpdatePassword();
        updatedPassword.setOldPassword("Jacinth1_");
        updatedPassword.setNewPassword("Jacinth2$");
        updatedPassword.setUrl("https:facebook.com");

        UpdatePasswordResponse result = passwordService.updatePassword(updatedPassword,
                "agbonirojacinta@gmail.com");

        assertThat(result.getNewPassword(), is("Jacinth2$"));
        assertThat(result.getUrl(), is("https:facebook.com"));

    }

    @Test
    void findAllPassword(){
        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        service.createAccount(newAccount);


        Password request = new Password();

        request.setSitePassword("Jacinth1_");
        request.setUsername("JayEst");
        request.setUrl("https:facebook.com");
        request.setEmail("agbonirojacinta@gmail.com");
        request.setUserPassword("JayEst1-");

        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setEmail("agbonirojacinta@gmail.com");
        loginDetails.setPassword("JayEst1-");

        passwordService.createAccount(request);

        List<WebsitePassword> passwords = passwordService.findAll();
        assertThat(passwords.size(), is(1));

    }

    @AfterEach
    void tearDown(){
        passwordService.deleteAll();
    }

}