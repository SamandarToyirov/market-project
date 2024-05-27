package com.example.marketproject.repo;

import com.example.marketproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
