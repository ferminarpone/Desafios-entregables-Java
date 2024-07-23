package com.coder.ecommerce.controllers;

import com.coder.ecommerce.dto.AmountProduct;
import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.services.ClientsService;
import com.coder.ecommerce.services.InvoiceDetailsService;
import com.coder.ecommerce.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/carts")
@Tag(name="Cart routes", description = "CRUD of carts.")
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
    @Operation(summary = "Read all carts from the database.", description = "It returns a List of carts.")
    public ResponseEntity<?> readAllCarts() {
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
    @Operation(summary = "Read a single created cart.", description = "This route requires the cart ID as a parameter. It returns the cart's data.")
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
    @Operation(summary = "Create a cart.", description = "It returns the created cart.")
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
    @Operation(summary = "Delete a cart.", description = "This route requires the cart ID as a parameter.")
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

    @PostMapping("/{clientId}/{productId}/{quantity}")
    @Operation(summary = "Add product to cart.", description = "This route requires the client ID, product ID and the quantity of product you want to buy as a parameter. It returns the cart's data.")
    public ResponseEntity<?> addProductToCart(@PathVariable Long clientId, @PathVariable Long productId , @PathVariable Integer quantity){
        try {
            Optional<Product> product = productsService.readProductById(productId);
            if (product.isEmpty()) return new ResponseEntity<>("Product with id: " + productId + " not found.", HttpStatus.NOT_FOUND);
            Optional<Client> client = clientsService.readClientById(clientId);
            if (client.isEmpty()) return new ResponseEntity<>("Client with id: " + clientId + " not found.", HttpStatus.NOT_FOUND);
            Invoice_details cart =  service.addProductToCart(productId, clientId, quantity);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{productId}/{cartId}")
    @Operation(summary = "Delete product from cart.", description = "This route requires the product ID and cart Id as a parameter.")
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
