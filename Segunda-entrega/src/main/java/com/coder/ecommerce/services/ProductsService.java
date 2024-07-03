package com.coder.ecommerce.services;

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
    private ProductsRepository repository;
    public Product saveProduct(@NonNull Product product){
        return repository.save(product);
    }

    public List<Product> readAllProducts(){
        return repository.findAll();
    }

    public Optional<Product> readProductById(@NonNull Long id){
        return repository.findById(id);
    }

    public void deleteProduct(@NonNull Long id){
        repository.deleteById(id);
    }
}
