package com.example.spring_boot_optimistic_locking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot_optimistic_locking.service.ProductService;

@RestController
@RequestMapping("/backoffice/fares")
public class ProductController {
    @Autowired
    ProductService productService;

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLock() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Product was updated by another user. Please retry.");
    }
}