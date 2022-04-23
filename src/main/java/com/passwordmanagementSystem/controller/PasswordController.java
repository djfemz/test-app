package com.passwordmanagementSystem.controller;

import com.passwordmanagementSystem.dtos.requests.UpdatePassword;
import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.requests.userRequests.LoginDetails;
import com.passwordmanagementSystem.dtos.requests.userRequests.UpdateUserPasswordRequest;
import com.passwordmanagementSystem.dtos.requests.userRequests.UserRequest;
import com.passwordmanagementSystem.exception.InvalidDetailsException;
import com.passwordmanagementSystem.service.SitePasswordService;
import com.passwordmanagementSystem.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000/")

@RestController
@RequestMapping("/login/addPassword")
public class PasswordController {
    @Autowired
    SitePasswordService passwordService;


    @PostMapping ("/login/password")
    public ResponseEntity<?> accountCreationResponse( @RequestBody Password request,LoginDetails details){
//    log.info(createAccount.toString());
        try{
            return new ResponseEntity<>(passwordService.createAccount(request, details), HttpStatus.CREATED);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping("/password/{url}/{LoginDetails}")
    public ResponseEntity<?> findUserPasswordByUrl(@PathVariable  String url, LoginDetails details){
        try{
            return new ResponseEntity<>(passwordService.findPasswordByUrl(url,details), HttpStatus.FOUND);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/password/{url}/{LoginDetails}")
    public ResponseEntity<?> DeletePassword(@PathVariable  String url, LoginDetails details){
        try{
            return new ResponseEntity<>(passwordService.deletePassword(url, details), HttpStatus.OK);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePassword request,LoginDetails details){
        try{
            return new ResponseEntity<>(passwordService.updatePassword(request, details), HttpStatus.OK);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/passwords")

    public ResponseEntity<?> findAllPasswords(){
        try{
            return new ResponseEntity<>(passwordService.findAll(), HttpStatus.FOUND);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
