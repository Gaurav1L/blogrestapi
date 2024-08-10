package com.blogapp1.repositories;

import com.blogapp1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

    User findByUsername  (String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
