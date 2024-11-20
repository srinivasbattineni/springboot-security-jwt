package com.dailycodebuffer.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dailycodebuffer.security.entity.Product;

/**
 * Repository interface for performing CRUD operations on Product entities.
 * 
 * Author: Srini Battineni
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Additional custom query methods (if required) can be added here.
}
