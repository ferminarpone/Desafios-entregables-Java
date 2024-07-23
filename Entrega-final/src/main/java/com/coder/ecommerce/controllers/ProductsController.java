package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.services.ProductsService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductsController {
    @Autowired
    private ProductsService service;

    @GetMapping
    public ResponseEntity<?> readAllProducts() {
        try {
            List<Product> productList = service.readAllProducts();
            if (productList.isEmpty()) return new ResponseEntity<>("Product list is empty", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readProductById(@NonNull @PathVariable Long id) {
        try {
            Optional<Product> product = service.readProductById(id);
            if (!product.isPresent()) return new ResponseEntity<>("Product with id: " + id + " not found", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @NonNull Product data) {
        try {
            if (data.getDescription() == null || data.getCode() == null || data.getPrice() == null || data.getStock() == null)
                return new ResponseEntity<>("Required field is missing", HttpStatus.BAD_REQUEST);
            Product product = service.saveProduct(data);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product data) {
        try {
            Optional<Product> foundProduct = service.readProductById(id);
            if (!foundProduct.isPresent()) return new ResponseEntity<>("Product with id: " + id + " not found", HttpStatus.NOT_FOUND);
            Product updatedProduct = foundProduct.get();
            if(data.getDescription() !=null) updatedProduct.setDescription(data.getDescription());
            if(data.getCode() !=null) updatedProduct.setCode(data.getCode());
            if(data.getPrice() !=null) updatedProduct.setPrice(data.getPrice());
            if(data.getStock() !=null) updatedProduct.setStock(data.getStock());
            service.saveProduct(updatedProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        try {
            Optional<Product> foundProduct = service.readProductById(id);
            if (!foundProduct.isPresent()) return new ResponseEntity<>("Product with id: " + id + " not found", HttpStatus.NOT_FOUND);
            service.deleteProduct(id);
            return new ResponseEntity<>("Product successfully deleted", HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
