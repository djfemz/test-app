package com.passwordmanagementSystem.repository;

import com.passwordmanagementSystem.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository <User,String>{

    void deleteByEmail(String email);
}
