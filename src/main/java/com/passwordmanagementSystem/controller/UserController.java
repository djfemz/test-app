package com.passwordmanagementSystem.controller;


import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.requests.userRequests.UpdateUserPasswordRequest;
import com.passwordmanagementSystem.dtos.requests.userRequests.UserRequest;
import com.passwordmanagementSystem.exception.InvalidDetailsException;
import com.passwordmanagementSystem.service.SitePasswordService;
import com.passwordmanagementSystem.service.UserService;
import com.passwordmanagementSystem.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
@ControllerAdvice
public class UserController {
@Autowired
UserService service;
    @Autowired
    SitePasswordService passwordService;


@PostMapping("/createPasswordAccount")
@ExceptionHandler(value = InvalidDetailsException.class)
    public ResponseEntity<?> accountCreationResponse(@Validated @RequestBody UserRequest createAccount){
//    log.info(createAccount.toString());
    try{
        return new ResponseEntity<>(service.createAccount(createAccount), HttpStatus.CREATED);
    }
    catch(InvalidDetailsException ex){
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
}
    @PostMapping ("/password/password")
    public ResponseEntity<?> accountCreationResponse( @RequestBody Password request){
//    log.info(createAccount.toString());
        try{
            return new ResponseEntity<>(passwordService.createAccount(request), HttpStatus.CREATED);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

@GetMapping("/{email}")
    public ResponseEntity<?> findUserResponse(@PathVariable  String email){
    try{
        return new ResponseEntity<>(service.findUserByDetails(email), HttpStatus.FOUND);
    }
    catch(InvalidDetailsException ex){
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

@PatchMapping("/updateAccount")
    public ResponseEntity<?> updateUserAccount(@RequestBody UpdateUserPasswordRequest request){
    try{
        return new ResponseEntity<>(service.updateUserAccount(request), HttpStatus.OK);
    }
    catch(InvalidDetailsException ex){
        return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
}

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            return new ResponseEntity<>(service.findAll(), HttpStatus.FOUND);
        }
        catch(InvalidDetailsException ex){
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
