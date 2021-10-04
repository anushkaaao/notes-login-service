package com.anushka.notesloginservice.repositories;


import com.anushka.notesloginservice.models.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

@Configuration
public interface UserRepo extends MongoRepository<User,String> {

    boolean existsByEmail(String email);
    User findByEmail(String email);





}
