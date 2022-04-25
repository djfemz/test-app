package com.passwordmanagementSystem.repository;

import com.passwordmanagementSystem.model.WebsitePassword;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SitePasswordRepo extends MongoRepository<WebsitePassword,String> {
    Optional<WebsitePassword> findByUrl(String url);

    void deleteByUrl(String url);
}
