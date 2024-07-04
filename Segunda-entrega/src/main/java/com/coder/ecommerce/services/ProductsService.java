package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.repositories.ProductsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productRepository;

    public Product saveProduct(@NonNull Product product) {
        return productRepository.save(product);
    }

    public List<Product> readAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> readProductById(@NonNull Long id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(@NonNull Long id) {
        productRepository.deleteById(id);
    }

}
