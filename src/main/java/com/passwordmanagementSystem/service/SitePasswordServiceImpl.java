package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.UpdatePassword;
import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.FindPassword;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.PasswordResponse;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.UpdatePasswordResponse;
import com.passwordmanagementSystem.dtos.responses.userResponses.DeleteResponse;
import com.passwordmanagementSystem.exception.InvalidPasswordException;
import com.passwordmanagementSystem.exception.InvalidUserException;
import com.passwordmanagementSystem.exception.PasswordNotFoundException;
import com.passwordmanagementSystem.model.User;
import com.passwordmanagementSystem.model.WebsitePassword;
import com.passwordmanagementSystem.repository.SitePasswordRepo;
import com.passwordmanagementSystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SitePasswordServiceImpl implements SitePasswordService{

    @Autowired
        private UserRepo userRepo;
    @Autowired
    private SitePasswordRepo sitePassword;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public PasswordResponse createAccount(Password request, LoginDetails details) {
        WebsitePassword password = new WebsitePassword();
        password.setUrl(request.getUrl());
        password.setWebsiteUserName(request.getUsername());
        password.setWebsitePassword(request.getPassword());

        Optional<User> user = userRepo.findById(request.getEmail());

        boolean userIsValid=user.isPresent() && Objects.equals(details.getPassword(), user.get().getUserPassword());
        if(userIsValid) {
            user.get().getPasswords().add(password);
            userRepo.save(user.get());
        }

         WebsitePassword savedPassword= sitePassword.save(password);

         PasswordResponse response = new PasswordResponse();
         response.setPassword(savedPassword.getWebsitePassword());
         response.setUsername(savedPassword.getWebsiteUserName());
         response.setUrl(savedPassword.getUrl());

//         User user = userService.findUserByDetails(request.getEmail());
//        SitePassword mapped = Mapper.map(request);
//        var saved = sitePassword.save(mapped);
//        User user = userService.findUser(request.getUserName());
//        user.getPassword.add(saved);
//        userService.save(user);

        return response;
    }

    @Override
    public Long count() {
        return sitePassword.count();
    }

    @Override
    public FindPassword findPasswordByUrl(String url, LoginDetails details) {
      Optional<WebsitePassword> password=  sitePassword.findById(url);
        Optional<User> user = userRepo.findById(details.getEmail());
            boolean userIsValid = user.isPresent() && Objects.equals(user.get().getUserPassword(), details.getPassword());
        if (password.isPresent()&& userIsValid) {

                user.get().getPasswords().add(password.get());
                userRepo.save(user.get());


            FindPassword foundPassword = new FindPassword();
            foundPassword.setUsername(password.get().getWebsiteUserName());
            foundPassword.setPassword(password.get().getWebsitePassword());

            return foundPassword;
        }
        throw new PasswordNotFoundException("password not found ");
    }

    @Override
    public DeleteResponse deletePassword(String url,LoginDetails details) {
        Optional<User> user = userRepo.findById(details.getEmail());

        if (user.isPresent()&& sitePassword.existsById(url)){
           sitePassword.deleteById(url);

            DeleteResponse response = new DeleteResponse();
            response.setResponse("deleted successfully");
            return response;
        }
        throw new InvalidPasswordException(url + " not found");

    }

    @Override
    public UpdatePasswordResponse updatePassword(UpdatePassword updatePassword, LoginDetails details) {
        Optional<WebsitePassword> passwordFound = sitePassword.findById(updatePassword.getUrl());
        if(passwordFound.isPresent()) {
            passwordFound.get().setWebsitePassword(updatePassword.getNewPassword());

            passwordFound.get().setUrl(updatePassword.getUrl());

            sitePassword.save(passwordFound.get());

            UpdatePasswordResponse response = new UpdatePasswordResponse();

            response.setNewPassword(passwordFound.get().getWebsitePassword());
            response.setUrl(passwordFound.get().getUrl());

            return response;
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
