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



@RestController
@RequestMapping("/api")
public class PasswordController {
    @Autowired
    SitePasswordService passwordService;


    @PostMapping ("/login")
    public ResponseEntity<?> accountCreationResponse( @RequestBody Password request){
        try{
            return new ResponseEntity<>(passwordService.createAccount(request), HttpStatus.CREATED);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping("/{url}")
    public ResponseEntity<?> findUserPasswordByUrl(@PathVariable  String url, @RequestBody LoginDetails details){
        try{
            return new ResponseEntity<>(passwordService.findPasswordByUrl(url,details), HttpStatus.FOUND);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{url}/{email}")
    public ResponseEntity<?> DeletePassword(@PathVariable  String url,@PathVariable String email){
        try{
            return new ResponseEntity<>(passwordService.deletePassword(url, email), HttpStatus.OK);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{email}")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePassword request, @PathVariable String email){
        try{
            return new ResponseEntity<>(passwordService.updatePassword(request, email), HttpStatus.OK);
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
