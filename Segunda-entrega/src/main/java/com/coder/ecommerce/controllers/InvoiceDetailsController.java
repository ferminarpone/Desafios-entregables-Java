package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.services.InvoiceDetailsService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/carts")
public class InvoiceDetailsController {
    @Autowired
    private InvoiceDetailsService service;

    @GetMapping
    public ResponseEntity<?> readAllCarts() {/**/
        try {
            List<Invoice_details> cartList = service.readAllInvoiceDetails();
            if (cartList.isEmpty()) throw new Exception("Cart list is empty");
            return new ResponseEntity<>(cartList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readCartById(@NonNull @PathVariable Long id) {
        try {
            Optional<Invoice_details> cart = service.readInvoiceDetailById(id);
            if (!cart.isPresent()) throw new Exception("Cart with id: " + id + " not found");
            return new ResponseEntity<>(cart.get(), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCart(Invoice_details data) {
        try {
            Invoice_details cart = service.saveInvoiceDetails(data);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


   /* @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product data) {
        try {
            Optional<Product> foundProduct = service.readProductById(id);
            if (!foundProduct.isPresent()) throw new Exception("Client with id: " + id + " not found");
            Product updatedProduct = foundProduct.get();
            if (data.getDescription() != null) updatedProduct.setDescription(data.getDescription());
            if (data.getCode() != null) updatedProduct.setCode(data.getCode());
            if (data.getPrice() != null) updatedProduct.setPrice(data.getPrice());
            if (data.getStock() != null) updatedProduct.setStock(data.getStock());
            service.saveProduct(updatedProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartById(@PathVariable Long id) {
        try {
            Optional<Invoice_details> foundCart = service.readInvoiceDetailById(id);
            if (!foundCart.isPresent()) throw new Exception("Cart with id: " + id + " not found");
            service.deleteInvoiceDetail(id);
            return new ResponseEntity<>("Cart successfully deleted", HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
