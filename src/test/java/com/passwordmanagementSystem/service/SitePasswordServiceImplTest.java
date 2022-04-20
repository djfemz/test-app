package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.FindPassword;
import com.passwordmanagementSystem.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SitePasswordServiceImplTest {
@Autowired
SitePasswordService passwordService;

@Autowired
UserService userService;


    @Test
    void passwordCanBeCreated(){
        Password request = new Password();
        request.setPassword("Jacinth1_");
        request.setUsername("JayEst");
        request.setUrl("https:facebook.com");
        request.setEmail("agbonirojacinta@gmail.com");

        passwordService.createAccount(request);
        assertThat(passwordService.count(), is(1L));
    }

    @Test
    void passwordCanBeFound(){
        Password request = new Password();
        request.setPassword("Jacinth1_");
        request.setUsername("JayEst");
        request.setUrl("https:facebook.com");
        request.setEmail("agbonirojacinta@gmail.com");

        passwordService.createAccount(request);

        FindPassword result = passwordService.findPasswordByUrl("https:facebook.com");

        assertThat(result.getPassword(), is("Jacinth1_"));
        assertThat(result.getUsername(), is("JayEst"));

//      User user=  userService.findUserByDetails(request.getEmail());

    }
    @Test
    void passwordCanBeDeleted(){

    }

}