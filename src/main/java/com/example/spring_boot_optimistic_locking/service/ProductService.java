package com.example.spring_boot_optimistic_locking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.example.spring_boot_optimistic_locking.dao.ProductDao;
import com.example.spring_boot_optimistic_locking.entity.Product;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Transactional
    public void purchase(Long productId) {

        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() <= 0) {
            throw new RuntimeException("Out of stock");
        }

        product.setQuantity(product.getQuantity() - 1);
        // No explicit save() needed – Hibernate flushes automatically
    }

    @Retryable(
        value = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 100)
    )
    @Transactional
    public void purchaseWithRetry(Long productId) {
        purchase(productId);
}
}
