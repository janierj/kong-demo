package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by janier on 20/09/17.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
