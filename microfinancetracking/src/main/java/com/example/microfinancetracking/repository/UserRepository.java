package com.example.microfinancetracking.repository;

import com.example.microfinancetracking.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    Object findByEmail(String email);
}