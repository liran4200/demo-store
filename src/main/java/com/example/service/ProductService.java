package com.example.service;

import com.example.entity.Product;

import java.util.List;

public interface ProductService {

    public Product createProduct(Product product);

    public Product retrieveProduct(Long id) throws ProductNotFoundException;

    public List<Product> retrieveAllProducts();

    public void updateProduct(Long id, Product product) throws ProductNotFoundException;

    public void deleteProduct(Long id) throws ProductNotFoundException;

}
