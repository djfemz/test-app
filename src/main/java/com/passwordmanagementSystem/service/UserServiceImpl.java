package com.passwordmanagementSystem.service;

import com.passwordmanagementSystem.dtos.requests.userRequests.DeleteUser;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.requests.userRequests.UpdateUserPasswordRequest;
import com.passwordmanagementSystem.dtos.requests.userRequests.UserRequest;
import com.passwordmanagementSystem.dtos.responses.userResponses.*;
import com.passwordmanagementSystem.exception.InvalidDetailsException;
import com.passwordmanagementSystem.exception.InvalidUserException;
import com.passwordmanagementSystem.model.User;
import com.passwordmanagementSystem.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    User user = new User();

    @Autowired
    private UserRepo usersRepo;


    @Override
    public AccountResponse createAccount(UserRequest request) {

        if(usersRepo.existsById(request.getEmail())){
            log.info("User already exists");
            usersRepo.deleteByEmail(request.getEmail());
        }
        user.setEmail(request.getEmail());
//        validateUserEmail(request);
        validateUserPassword(request);

        User savedUser = usersRepo.save(user);

//        log.info(String.valueOf(user));

        AccountResponse response = new AccountResponse();
        response.setPassword(savedUser.getUserPassword());
        response.setEmail(savedUser.getEmail());

        return response;
    }

    private void validateUserEmail(UserRequest request) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getEmail());
        if (matcher.matches()) {
            user.setEmail(request.getEmail());
        } else throw new InvalidDetailsException("enter a valid email");
    }

    private void validateUserPassword(UserRequest request) {
        String regex = "^(?=[^A-Z\\s]*[A-Z])(?=[^a-z\\s]*[a-z])(?=[^\\d\\s]*\\d)(?=\\w*[\\W_])\\S{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getPassword());
        if (matcher.matches()) {
            user.setUserPassword(request.getPassword());
        } else throw new InvalidDetailsException("password must contain capital letter " +
                "and special characters");
    }

    @Override
    public long count() {
        return usersRepo.count();
    }



    @Override
    public FindUserResponse findUserByDetails(String email) {
       Optional<User> user = usersRepo.findById(email);
       if(user.isPresent()){
           FindUserResponse response = new FindUserResponse();
           response.setEmail(user.get().getEmail());
           response.setPassword(user.get().getUserPassword());
           return response;
       }
       throw new InvalidUserException(email + " not found");
    }

    @Override
    public UpdateUserPasswordResponse updateUserAccount(UpdateUserPasswordRequest request) {
        Optional<User> userFound = usersRepo.findById(request.getEmail());
        boolean isValid = userFound.isPresent() && userFound.get().
                getUserPassword().equals(request.getOldPassword());
        if(isValid){
            userFound.get().setEmail(request.getEmail());
//            userFound.get().setUserPassword(request.getNewPassword());
            validateUpdatedPassword(request, userFound);
            usersRepo.save(userFound.get());
            UpdateUserPasswordResponse response = new UpdateUserPasswordResponse();
            response.setEmail(userFound.get().getEmail());
            response.setNewPassword(userFound.get().getUserPassword());
            return response;
        }
        throw new InvalidUserException(request.getEmail() + " not found");
    }

    private void validateUpdatedPassword(UpdateUserPasswordRequest request, Optional<User> userFound) {
        String regex = "^(?=[^A-Z\\s]*[A-Z])(?=[^a-z\\s]*[a-z])(?=[^\\d\\s]*\\d)(?=\\w*[\\W_])\\S{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getNewPassword());
        if (matcher.matches()) {
            userFound.get().setUserPassword(request.getNewPassword());
        } else throw new InvalidDetailsException("password must contain capital letter " +
                "and special characters");
    }

    @Override
    public LoginResponse login(LoginDetails request) {

        Optional<User> userFound = usersRepo.findById(request.getEmail());
        boolean isValid = userFound.isPresent() && userFound.get().
                getUserPassword().equals(request.getPassword());

        if(isValid){
            LoginResponse response = new LoginResponse();
            response.setMessage("Welcome " + userFound.get().getEmail());
            return response;
        }
        throw new InvalidUserException("Invalid Details");
    }

    @Override
    public DeleteResponse deleteAccount(DeleteUser request) {
        Optional<User> userFound = usersRepo.findById(request.getEmail());
        boolean isValid = userFound.isPresent() && userFound.get().
                getUserPassword().equals(request.getPassword());

        if(isValid){
            usersRepo.delete(userFound.get());
            DeleteResponse response = new DeleteResponse();
            response.setResponse("deleted successfully");
            return response;
        }
        throw new InvalidUserException(request.getEmail() + " not found");
    }

    @Override
    public List<User> findAll() {
        return usersRepo.findAll();
    }


}
