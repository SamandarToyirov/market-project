package com.example.marketproject.repo;

import com.example.marketproject.entity.Category;
import com.example.marketproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
    List<Category> findAllByNameContainingIgnoreCase(String search);
}
