package com.passwordmanagementSystem.repository;

import com.passwordmanagementSystem.model.WebsitePassword;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitePasswordRepo extends MongoRepository<WebsitePassword,String> {
}
