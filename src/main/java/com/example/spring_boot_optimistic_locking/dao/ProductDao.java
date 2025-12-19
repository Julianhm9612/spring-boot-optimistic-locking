package com.example.spring_boot_optimistic_locking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_boot_optimistic_locking.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    
}
