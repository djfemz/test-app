package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.userRequests.DeleteUser;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.requests.userRequests.UpdateUserPasswordRequest;
import com.passwordmanagementSystem.dtos.requests.userRequests.UserRequest;
import com.passwordmanagementSystem.dtos.responses.userResponses.DeleteResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.FindUserResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.LoginResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.UpdateUserPasswordResponse;
import com.passwordmanagementSystem.exception.InvalidDetailsException;
import com.passwordmanagementSystem.model.User;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;



    @Test
    void userCanCreateAccount(){
        UserRequest newAccount = new UserRequest();

        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("Jacinth01-");

        userService.createAccount(newAccount);

        assertThat(userService.count(), is(1L));
    }

 @Test
    void moreUsersCanCreateAccount(){
        UserRequest newAccount = new UserRequest();
        UserRequest newAccount1 = new UserRequest();

        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("Jacinth01-");

        userService.createAccount(newAccount);

        newAccount1.setEmail("estherjacinta@gmail.com");
        newAccount1.setPassword("Jacint1_");
        userService.createAccount(newAccount1);
        assertThat(userService.count(), is(2L));
    }



//    @Test
//    void onlyValidEmailCanBeUsedToCreateAccount(){
//        UserRequest newAccount = new UserRequest();
//       newAccount.setEmail("com");
//        newAccount.setPassword("Jacinth01-");
//
////        passwordManager.createAccount(newAccount);
//        assertThrows(InvalidDetailsException.class, ()-> passwordManager.createAccount(newAccount));
//    }


    @Test
    @DisplayName("onlyUniquePasswordWithAtLeastOneUpperCase_LowerCase_SpecialCharacterCanCreate")
    void checkForUniquePassword(){
        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("Jacit0");
        assertThrows(InvalidDetailsException.class, ()-> userService.createAccount(newAccount));
    }

@Test
    void noTwoUserCanHaveSameEmail(){

    UserRequest newAccount = new UserRequest();
    UserRequest newAccount1 = new UserRequest();

    newAccount.setEmail("agbonirojacinta@gmail.com");
    newAccount.setPassword("Jacinth01-");

    userService.createAccount(newAccount);

    newAccount1.setEmail("agbonirojacinta@gmail.com");
    newAccount1.setPassword("Jacint1_");
    userService.createAccount(newAccount1);
    assertThat(userService.count(), is(1L));
}

    @Test
    void userCanBeFound(){

        UserRequest newAccount = new UserRequest();
        UserRequest newAccount1 = new UserRequest();

        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("Jacinth01-");

        userService.createAccount(newAccount);

        newAccount1.setEmail("estheragboniro@gmail.com");
        newAccount1.setPassword("Jacinth01-");

        userService.createAccount(newAccount1);

        assertThat(userService.count(), is(2L));


        FindUserResponse result = userService.findUserByDetails("agbonirojacinta@gmail.com");

        assertThat(result.getEmail(), is("agbonirojacinta@gmail.com"));
        assertThat(result.getPassword(), is("Jacinth01-"));

    }
// @Test
//    void invalidPasswordThrowsException(){
//
//        UserRequest newAccount = new UserRequest();
//        UserRequest newAccount1 = new UserRequest();
//
//        newAccount.setEmail("agbonirojacinta@gmail.com");
//        newAccount.setPassword("Jacinth01-");
//
//        userService.createAccount(newAccount);
//
//        newAccount1.setEmail("estheragboniro@gmail.com");
//        newAccount1.setPassword("Jacinth01-");
//
//        userService.createAccount(newAccount1);
//
//        assertThat(userService.count(), is(2L));
//
//
//     assertThrows(InvalidUserException.class, ()-> userService.
//             findUserByDetails("estheragboniro@gmail.com"));
//    }

    @Test
    void userDetailsCanBeUpdated(){
        UserRequest newAccount = new UserRequest();
        UserRequest newAccount1 = new UserRequest();

        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("Jacinth01-");

        userService.createAccount(newAccount);

        newAccount1.setEmail("estheragboniro@gmail.com");
        newAccount1.setPassword("Jacinth01-");

        userService.createAccount(newAccount1);

        UpdateUserPasswordRequest request = new UpdateUserPasswordRequest();
        request.setEmail("agbonirojacinta@gmail.com");
        request.setOldPassword("Jacinth01-");
        request.setNewPassword("JayEst1_");
        UpdateUserPasswordResponse result = userService.updateUserAccount(request);

        assertThat(result.getNewPassword(), is("JayEst1_"));
        assertThat(result.getEmail(), is("agbonirojacinta@gmail.com"));

    }

    @Test
    void userCanLoginWithValidDetails(){
        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        userService.createAccount(newAccount);

        LoginDetails request = new LoginDetails();
        request.setEmail("agbonirojacinta@gmail.com");
        request.setPassword("JayEst1-");
        LoginResponse result = userService.login(request);
        assertThat(result.getMessage(), is("Welcome agbonirojacinta@gmail.com"));
    }

    @Test
    void userCanDeleteAccount(){
        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        userService.createAccount(newAccount);

        DeleteUser request = new DeleteUser();
        request.setEmail("agbonirojacinta@gmail.com");
        request.setPassword("JayEst1-");
        DeleteResponse result = userService.deleteAccount(request);
        assertThat(result.getResponse(), is("deleted successfully"));
    }


    @Test
    void findAllUser(){
        UserRequest newAccount = new UserRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setPassword("JayEst1-");

        userService.createAccount(newAccount);
       List<User> users = userService.findAll();

       assertThat(users.size(), is(1));
    }

    @AfterEach
    void tearDown(){
        userService.deleteAll();
    }
}