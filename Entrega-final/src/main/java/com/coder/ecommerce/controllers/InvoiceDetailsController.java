package com.coder.ecommerce.controllers;

import com.coder.ecommerce.dto.AmountProduct;
import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.services.ClientsService;
import com.coder.ecommerce.services.InvoiceDetailsService;
import com.coder.ecommerce.services.ProductsService;
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
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ClientsService clientsService;
    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @GetMapping
    public ResponseEntity<?> readAllCarts() {/**/
        try {
            List<Invoice_details> cartList = service.readAllInvoiceDetails();
            if (cartList.isEmpty()) return new ResponseEntity<>("Cart list is empty", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(cartList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> readCartById(@NonNull @PathVariable Long cartId) {
        try {
            Optional<Invoice_details> cart = service.readInvoiceDetailById(cartId);
            if (!cart.isPresent()) return new ResponseEntity<>("Cart with id: " + cartId + " not found", HttpStatus.NOT_FOUND);
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

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCartById(@PathVariable Long cartId) {
        try {
            Optional<Invoice_details> foundCart = service.readInvoiceDetailById(cartId);
            if (!foundCart.isPresent()) return new ResponseEntity<>("Cart with id: " + cartId + " not found", HttpStatus.NOT_FOUND);
            service.deleteInvoiceDetail(cartId);
            return new ResponseEntity<>("Cart successfully deleted", HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/product/{productId}/client/{clientId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long productId, @PathVariable Long clientId, @RequestBody AmountProduct amount){
        try {
            Optional<Product> product = productsService.readProductById(productId);
            if (product.isEmpty()) return new ResponseEntity<>("Product with id: " + productId + " not found.", HttpStatus.NOT_FOUND);
            Optional<Client> client = clientsService.readClientById(clientId);
            if (client.isEmpty()) return new ResponseEntity<>("Client with id: " + clientId + " not found.", HttpStatus.NOT_FOUND);
            Invoice_details cart =  service.addProductToCart(productId, clientId, amount.getAmount());
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{productId}/cart/{cartId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long productId, @PathVariable Long cartId){
        try {
            Optional<Product> foundProduct = productsService.readProductById(productId);
            if (foundProduct.isEmpty())
                return new ResponseEntity<>("Product with id: " + productId + " not found.", HttpStatus.NOT_FOUND);
            Optional<Invoice_details> foundCart = invoiceDetailsService.readInvoiceDetailById(cartId);
            if (foundCart.isEmpty())
                return new ResponseEntity<>("Cart with id: " + cartId + " not found.", HttpStatus.NOT_FOUND);
            Invoice_details cart = foundCart.get();
            Product product = foundProduct.get();
            if (!cart.getProduct().equals(product))
                return new ResponseEntity<>("Product not found in cart.", HttpStatus.NOT_FOUND);
            service.deleteProductFromCart(productId, cartId);
            return new ResponseEntity<>("Product successfully deleted from cart.", HttpStatus.OK);
        } catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
