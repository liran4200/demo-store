package com.example.service;

import com.example.entity.Product;

public class ProductAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
