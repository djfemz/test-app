package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.AccountCreationRequest;
import com.passwordmanagementSystem.dtos.requests.SitePasswordAdditionRequest;
import com.passwordmanagementSystem.dtos.responses.AccountCreationResponse;
import com.passwordmanagementSystem.exception.InvalidDetailsException;
import com.passwordmanagementSystem.exception.InvalidPasswordException;
import com.passwordmanagementSystem.model.PasswordManagerRegister;
import com.passwordmanagementSystem.model.UserPasswords;
import com.passwordmanagementSystem.repository.PasspalRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class PasswordManagerServiceImpl implements PasswordManagerService{

    PasswordManagerRegister manager1 = new PasswordManagerRegister();

    @Autowired
    private PasspalRepo userPasswords;


    @Override
    public AccountCreationResponse createAccount(AccountCreationRequest request) {
             if (userPasswords.existsByEmail(request.getEmail())) {
              userPasswords.deleteByEmail(request.getEmail());
            }


                validateUserEmail(request);
                validateUserPassword(request);

                manager1.setUserName(request.getUserName());

                PasswordManagerRegister manager2 = userPasswords.save(manager1);
 log.info(String.valueOf(manager1));

                AccountCreationResponse response = new AccountCreationResponse();
                response.setPassword(manager2.getUniquePassword());
                response.setUsername(manager2.getUserName());

      return response;
    }
    private  void validateUserEmail(AccountCreationRequest request) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getEmail());
        if(matcher.matches()) {
          manager1.setEmail(request.getEmail());
        } else throw new InvalidDetailsException("enter a valid email");
    }

    private  void validateUserPassword(AccountCreationRequest request) {
        String regex = "^(?=[^A-Z\\s]*[A-Z])(?=[^a-z\\s]*[a-z])(?=[^\\d\\s]*\\d)(?=\\w*[\\W_])\\S{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getUniquePassword());
        if(matcher.matches()) {
          manager1.setUniquePassword(request.getUniquePassword());
        } else throw new InvalidDetailsException("password must contain capital letter " +
                "and special characters");
    }

    @Override
    public long count() {
        log.info(String.valueOf(userPasswords.count()));
        return userPasswords.count();
    }

    @Override
    public void addPassword(SitePasswordAdditionRequest  siteDetails) {
        if(!(manager1.getUniquePassword().equals(siteDetails.getUniquePassword()))){
           throw new InvalidPasswordException("password does not match");
        }
          UserPasswords passwords = new UserPasswords(siteDetails.getUniquePassword(),
                siteDetails.getName(), siteDetails.getUrl(),siteDetails.getSiteUsername(),
                siteDetails.getSitePassword(), siteDetails.getEmail());

        for (int i = 0; i < manager1.getPasswords().size(); i++) {
            if(manager1.getPasswords().get(i).getUrl().equals(passwords.getUrl())) {
                manager1.getPasswords().remove(manager1.getPasswords().get(i));
            }
        }

            if (userPasswords.existsByEmail(siteDetails.getEmail())) {
               manager1.getPasswords().add(passwords);
//                log.info(String.valueOf(manager1));

            }
            else{
                log.info("Please create an account");
            }


    }

    @Override
    public int listOfPasswords() {
        return manager1.getPasswords().size();
    }

    @Override
    public String getPassword(String url) {
        for (int i = 0; i < manager1.getPasswords().size(); i++) {
            if (manager1.getPasswords().get(i).getUrl().equals(url)){
                return manager1.getPasswords().get(i).getSitePassword();
            }
        }
        return null;
    }


}
