package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.AccountCreationRequest;
import com.passwordmanagementSystem.dtos.requests.SitePasswordAdditionRequest;
import com.passwordmanagementSystem.exception.InvalidDetailsException;

import com.passwordmanagementSystem.exception.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;

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
class PasswordManagerServiceImplTest {
    @Autowired
    PasswordManagerService passwordManager;

    @Test
    void userCanCreateAccount(){
        AccountCreationRequest newAccount = new AccountCreationRequest();

        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacinth01-");
        newAccount.setUserName("Jacy");
        passwordManager.createAccount(newAccount);

        assertThat(passwordManager.count(), is(1L));
    }

 @Test
    void moreUsersCanCreateAccount(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        AccountCreationRequest newAccount1 = new AccountCreationRequest();

        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacinth01-");
        newAccount.setUserName("Jacy");
        passwordManager.createAccount(newAccount);

        newAccount1.setEmail("estherjacinta@gmail.com");
        newAccount1.setUniquePassword("Jacint1_");
        newAccount1.setUserName("jay");
        passwordManager.createAccount(newAccount1);
//            log.info(passwordManager.toString());
        assertThat(passwordManager.count(), is(2L));
    }


    @Test
    void onlyValidEmailCanBeUsedToCreateAccount(){
       AccountCreationRequest newAccount = new AccountCreationRequest();
       newAccount.setEmail("a.com");
        newAccount.setUniquePassword("Jacint01-");
        newAccount.setUserName("Jacy");

        assertThrows(InvalidDetailsException.class, ()-> passwordManager.createAccount(newAccount));
    }


    @Test
    @DisplayName("onlyUniquePasswordWithAtLeastOneUpperCase_LowerCase_SpecialCharacterCanCreate")
    void checkForUniquePassword(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacit0");
        newAccount.setUserName("Jacy");

        assertThrows(InvalidDetailsException.class, ()-> passwordManager.createAccount(newAccount));
    }



    @Test
    void registeredUserCanAddPassword(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacinth01-");
        newAccount.setUserName("Jacy");
       passwordManager.createAccount(newAccount);
        SitePasswordAdditionRequest request = new SitePasswordAdditionRequest();

        request.setEmail("agbonirojacinta@gmail.com");
        request.setName("Esther");
        request.setSitePassword("happy");
        request.setUrl("https:facebook.com");
        request.setUniquePassword("Jacinth01-");
        request.setSiteUsername("Jay");
        passwordManager.addPassword(request);

        assertThat(passwordManager.count(), is(1L));
        assertThat(passwordManager.listOfPasswords(), is(1));
    }

        @Test
        void invalidPasswordThrowsException(){
            AccountCreationRequest newAccount = new AccountCreationRequest();
            newAccount.setEmail("agbonirojacinta@gmail.com");
            newAccount.setUniquePassword("Jacinth01-");
            newAccount.setUserName("Jacy");
            passwordManager.createAccount(newAccount);
            SitePasswordAdditionRequest request = new SitePasswordAdditionRequest();


            request.setEmail("agbonirojacinta@gmail.com");
            request.setName("Esther");
            request.setSitePassword("happy");
            request.setUrl("https:facebook.com");
            request.setUniquePassword("Jacinth01_");
            request.setSiteUsername("Jay");

    assertThrows(InvalidPasswordException.class, ()-> passwordManager.addPassword(request));

        }


  @Test
    void registeredUserCanAddMorePasswords(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacinth01-");
        newAccount.setUserName("Jacy");
       passwordManager.createAccount(newAccount);
        SitePasswordAdditionRequest request = new SitePasswordAdditionRequest();

        request.setEmail("agbonirojacinta@gmail.com");
        request.setName("Esther");
        request.setSitePassword("happy");
        request.setUrl("https:facebook.com");
        request.setUniquePassword("Jacinth01-");
        request.setSiteUsername("Jay");
        passwordManager.addPassword(request);

        SitePasswordAdditionRequest request1 = new SitePasswordAdditionRequest();

        request1.setEmail("agbonirojacinta@gmail.com");
        request1.setName("Esther");
        request1.setSitePassword("happy");
        request1.setUrl("https:instagram.com");
        request1.setUniquePassword("Jacinth01-");
        request1.setSiteUsername("Jay");
        passwordManager.addPassword(request1);

        assertThat(passwordManager.count(), is(1L));
        assertThat(passwordManager.listOfPasswords(), is(2));
    }

    @Test
    void passwordCanBeUpdated(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacinth01-");
        newAccount.setUserName("Jacy");
        passwordManager.createAccount(newAccount);
        SitePasswordAdditionRequest request = new SitePasswordAdditionRequest();

        request.setEmail("agbonirojacinta@gmail.com");
        request.setName("Esther");
        request.setSitePassword("happy");
        request.setUrl("https:facebook.com");
        request.setUniquePassword("Jacinth01-");
        request.setSiteUsername("Jay");
        passwordManager.addPassword(request);

        SitePasswordAdditionRequest request1 = new SitePasswordAdditionRequest();

        request1.setEmail("agbonirojacinta@gmail.com");
        request1.setName("Esther");
        request1.setSitePassword("jay");
        request1.setUrl("https:facebook.com");
        request1.setUniquePassword("Jacinth01-");
        request1.setSiteUsername("Jay");
        passwordManager.addPassword(request1);

        assertThat(passwordManager.count(), is(1L));
        assertThat(passwordManager.listOfPasswords(), is(2));
    }

    @Test
    void passwordCanBeGotten(){
        AccountCreationRequest newAccount = new AccountCreationRequest();
        newAccount.setEmail("agbonirojacinta@gmail.com");
        newAccount.setUniquePassword("Jacinth01-");
        newAccount.setUserName("Jacy");
        passwordManager.createAccount(newAccount);
        SitePasswordAdditionRequest request = new SitePasswordAdditionRequest();

        request.setEmail("agbonirojacinta@gmail.com");
        request.setName("Esther");
        request.setSitePassword("jay");
        request.setUrl("https:facebook.com");
        request.setUniquePassword("Jacinth01-");
        request.setSiteUsername("Jay");
        passwordManager.addPassword(request);

        assertThat(passwordManager.getPassword("https:facebook.com"), is("jay"));
    }

//    @Test
//    void allPasswordsCanBeGotten(){
////
////        List<User> all = userRepository.findAll();
////        assertEquals(3,all.size());
//        assertThat(passwordManager.getAllPasswords)
//
//    }
    @Test
    void allUserCanBeGotten(){
        assertThat(passwordManager.count(), is(2L));
    }

}