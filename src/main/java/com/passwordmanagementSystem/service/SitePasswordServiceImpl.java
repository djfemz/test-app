package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.UpdatePassword;
import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.FindPassword;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.PasswordResponse;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.UpdatePasswordResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.DeleteResponse;
import com.passwordmanagementSystem.exception.InvalidPasswordException;
import com.passwordmanagementSystem.exception.PasswordExistException;
import com.passwordmanagementSystem.exception.PasswordNotFoundException;
import com.passwordmanagementSystem.model.User;
import com.passwordmanagementSystem.model.WebsitePassword;
import com.passwordmanagementSystem.repository.SitePasswordRepo;
import com.passwordmanagementSystem.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class SitePasswordServiceImpl implements SitePasswordService{

    @Autowired
        private UserRepo userRepo;
    @Autowired
    private SitePasswordRepo sitePassword;

    @Override
    public PasswordResponse createAccount(Password request) {
        WebsitePassword password1 = new WebsitePassword();
        password1.setUrl(request.getUrl());
        password1.setWebsiteUserName(request.getUsername());
        password1.setWebsitePassword(request.getSitePassword());

        Optional<User> user = userRepo.findById(request.getEmail());
        boolean userIsValid=user.isPresent() && Objects.equals(request.getUserPassword(),
                user.get().getUserPassword());
//        if (user.isPresent()&& sitePassword.findByUrl(request.getUrl()).isPresent()){
//            sitePassword.deleteByUrl(request.getUrl());
//        }
        if(userIsValid ) {
            user.get().setLoggedIn(true);
        }

         WebsitePassword savedPassword= sitePassword.save(password1);
        user.get().getPasswords().add(savedPassword);
        userRepo.save(user.get());

         PasswordResponse response = new PasswordResponse();
         response.setPassword(savedPassword.getWebsitePassword());
         response.setUsername(savedPassword.getWebsiteUserName());
         response.setUrl(savedPassword.getUrl());


        return response;
    }

    @Override
    public Long count() {
        return sitePassword.count();
    }

    @Override
    public FindPassword findPasswordByUrl(String url, LoginDetails details) {
        Optional<User> user = userRepo.findById(details.getEmail());
            boolean userIsValid = user.isPresent() && Objects.equals(user.get().getUserPassword(),
                    details.getPassword());
            if(userIsValid) {
                user.get().setLoggedIn(true);
                List<WebsitePassword> passwordToBeFound = user.get().getPasswords();
                for (WebsitePassword passwords : passwordToBeFound) {
                    if (Objects.equals(passwords.getUrl(), url)) {
                        FindPassword foundPassword = new FindPassword();
                        foundPassword.setUsername(passwords.getWebsiteUserName());
                        foundPassword.setPassword(passwords.getWebsitePassword());
                        return foundPassword;
                    }
                }
            }
        throw new PasswordNotFoundException("password not found ");
    }

    @Override
    public DeleteResponse deletePassword(String url,String email) {
        Optional<User> user = userRepo.findById(email);
        Optional<WebsitePassword> password = sitePassword.findByUrl(url);
        if (user.isPresent()&&  password.isPresent()){
            user.get().setLoggedIn(true);
            List<WebsitePassword> passwordToBeFound = user.get().getPasswords();
            log.info(String.valueOf(passwordToBeFound));
            for (WebsitePassword passwords : passwordToBeFound) {
                if (Objects.equals(passwords.getUrl(), url)) {

                    sitePassword.deleteByUrl(url);
                    user.get().getPasswords().remove(passwords);
                        userRepo.save(user.get());
                    DeleteResponse response = new DeleteResponse();
                    response.setResponse("deleted successfully");
                    return response;

                }
            }


        }
        throw new InvalidPasswordException(url + " not found");

    }

    @Override
    public UpdatePasswordResponse updatePassword(UpdatePassword updatePassword, String email) {
        Optional<User> user = userRepo.findById(email);

        Optional<WebsitePassword> passwordFound = sitePassword.findByUrl(updatePassword.getUrl());
        if(passwordFound.isPresent()&& user.isPresent()) {
                     sitePassword.deleteByUrl(passwordFound.get().getUrl());

            user.get().setLoggedIn(true);
            List<WebsitePassword> passwordToBeFound = user.get().getPasswords();
            for (WebsitePassword passwords : passwordToBeFound) {
                if (Objects.equals(passwords.getUrl(), updatePassword.getUrl())) {
                    user.get().getPasswords().remove(passwords);
                    passwordFound.get().setWebsitePassword(updatePassword.getNewPassword());

                    passwordFound.get().setUrl(updatePassword.getUrl());
                    sitePassword.save(passwordFound.get());

                    user.get().getPasswords().add(passwordFound.get());
                    userRepo.save(user.get());


                    UpdatePasswordResponse response = new UpdatePasswordResponse();

                    response.setNewPassword(passwordFound.get().getWebsitePassword());
                    response.setUrl(passwordFound.get().getUrl());

                    return response;
                }}


        }
        throw new InvalidPasswordException (updatePassword.getUrl() + " not found");

    }

    @Override
    public List<WebsitePassword> findAll() {
        return sitePassword.findAll();
    }

    @Override
    public void deleteAll() {
        sitePassword.deleteAll();
    }

}
