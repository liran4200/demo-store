package com.example.data;

import com.example.dao.CategoryRepository;
import com.example.dao.ProductRepository;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public DataProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product)  {
        return productRepository.save(product);
    }

    @Override
    public Product retrieveProduct(Long id) throws ProductNotFoundException {
        Optional<Product> op = this.productRepository.findById(id);
        if (op.isPresent()) {
            return op.get();
        }else {
            throw new ProductNotFoundException("Product: "+ id + " not found");
        }
    }

    @Override
    public List<Product> retrieveAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public void updateProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> op = this.productRepository.findById(id);

        if (!op.isPresent()){
            throw new ProductNotFoundException("Product: "+ id + " not found");
        }

        Product existing = op.get();
        if( product.getName()!=null &&
                !product.getName().equals(existing.getName())) {
            existing.setName(product.getName());
        }

        if( product.getDescription()!=null &&
                !product.getDescription().equals(existing.getDescription())) {
            existing.setDescription(product.getDescription());
        }

        if( product.getPrice() != (existing.getPrice())) {
            existing.setPrice(product.getPrice());
        }

        this.productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> op = this.productRepository.findById(id);
        if (op.isPresent()) {
            this.productRepository.delete(op.get());
        }else {
            throw new ProductNotFoundException("Product: "+ id + " not found");
        }

    }

    @Override
    public void assignToCategory(Long productId, Long categoryId) throws CategoryNotFoundException, ProductNotFoundException {
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryId);

        if (!optionalCategory.isPresent()){
            throw new CategoryNotFoundException("Category: "+ categoryId + " not found");
        }

        Optional<Product> optionalProduct = this.productRepository.findById(productId);

        if (!optionalProduct.isPresent()){
            throw new ProductNotFoundException("Product: "+ productId + " not found");
        }

        Category c = optionalCategory.get();
        Product p = optionalProduct.get();
        p.setCategory(c);
        c.addProduct(p);
        this.categoryRepository.save(c);
    }
}
