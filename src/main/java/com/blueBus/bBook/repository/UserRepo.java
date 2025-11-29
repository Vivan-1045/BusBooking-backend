package com.blueBus.bBook.repository;

import com.blueBus.bBook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByUserName(String username);
    boolean existsByEmail(String email);
}
