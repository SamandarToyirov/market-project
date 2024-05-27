package com.example.marketproject.repo;


import com.example.marketproject.entity.IncomeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IncomeProductRepo extends JpaRepository<IncomeProduct, UUID> {
    List<IncomeProduct> findAllByProductId(UUID productId);
}

