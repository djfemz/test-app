package com.passwordmanagementSystem.repository;

import com.passwordmanagementSystem.model.PasswordManagerRegister;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasspalRepo extends MongoRepository <PasswordManagerRegister,String>{
   Boolean existsByEmail(String email);
}
