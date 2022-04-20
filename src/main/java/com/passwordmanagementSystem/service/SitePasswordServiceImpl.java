package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.FindPassword;
import com.passwordmanagementSystem.dtos.responses.passwordResponses.PasswordResponse;
import com.passwordmanagementSystem.exception.PasswordNotFoundException;
import com.passwordmanagementSystem.model.User;
import com.passwordmanagementSystem.model.WebsitePassword;
import com.passwordmanagementSystem.repository.SitePasswordRepo;
import com.passwordmanagementSystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SitePasswordServiceImpl implements SitePasswordService{

    @Autowired
//    private UserService userService;
        private UserRepo userRepo;
    @Autowired
    private SitePasswordRepo sitePassword;

    @Override
    public PasswordResponse createAccount(Password request) {
        WebsitePassword password = new WebsitePassword();
        password.setUrl(request.getUrl());
        password.setWebsiteUserName(request.getUsername());
        password.setWebsitePassword(request.getPassword());
        password.setEmail(request.getEmail());
        Optional<User> user = userRepo.findById(request.getEmail());
        if(user.isPresent()) {
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
    public FindPassword findPasswordByUrl(String url) {
      Optional<WebsitePassword> password=  sitePassword.findById(url);
        if (password.isPresent()) {
            FindPassword foundPassword = new FindPassword();
            foundPassword.setUsername(password.get().getWebsiteUserName());
            foundPassword.setPassword(password.get().getWebsitePassword());

            return foundPassword;
        }
        throw new PasswordNotFoundException("password not found ");
    }

}
