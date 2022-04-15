package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.AccountCreationRequest;
import com.passwordmanagementSystem.dtos.requests.SitePasswordAdditionRequest;
import com.passwordmanagementSystem.dtos.responses.AccountCreationResponse;
import com.passwordmanagementSystem.model.PasswordManagerRegister;
import com.passwordmanagementSystem.model.UserPasswords;
import com.passwordmanagementSystem.repository.PasspalRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PasswordManagerServiceImpl implements PasswordManagerService{

    PasswordManagerRegister manager1 = new PasswordManagerRegister();

    @Autowired
    private PasspalRepo userPasswords;


    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest request) {

        manager1.setEmail(request.getEmail()) ;
        manager1.setPassword(request.getUniquePassword());
        manager1.setUserName(request.getUserName());
      PasswordManagerRegister manager2=  userPasswords.save(manager1);


      AccountCreationResponse response = new AccountCreationResponse();
      response.setPassword(manager2.getPassword());
      response.setUsername(manager2.getUserName());

      return response;
    }

    @Override
    public long count() {
        return userPasswords.count();
    }

    @Override
    public void addPassword(SitePasswordAdditionRequest siteDetails) {

        UserPasswords passwords = new UserPasswords(siteDetails.getYourPassword(),
                siteDetails.getName(), siteDetails.getUrl(),siteDetails.getSiteUsername(),
                siteDetails.getYourPassword(), siteDetails.getEmail());



            if (userPasswords.existsByEmail(siteDetails.getEmail())) {
               manager1.getPasswords().add(passwords);
                log.info(String.valueOf(manager1));

//                userPasswords.save(manager1);
            }
            else{
                log.info("Please create an account");
            }

    }

    @Override
    public int listOfPasswords() {
        return manager1.getPasswords().size();
    }
}
