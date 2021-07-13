package com.houseofo.data.repository;

import com.houseofo.data.model.Role;
import com.houseofo.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    List<User> findUsersByRole(Role role);
    Optional<User> findUserByDesignerBrand(String designerBrand);
    User findUserById(String userId);
    //todo find user by firstname lastname username
}
