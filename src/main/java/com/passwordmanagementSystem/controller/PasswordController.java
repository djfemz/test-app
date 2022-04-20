package com.passwordmanagementSystem.controller;

import com.passwordmanagementSystem.dtos.requests.passwordRequests.Password;
import com.passwordmanagementSystem.dtos.requests.userRequests.UserRequest;
import com.passwordmanagementSystem.exception.InvalidDetailsException;
import com.passwordmanagementSystem.service.SitePasswordService;
import com.passwordmanagementSystem.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping
//public class PasswordController {
//
//
//
//
//}
