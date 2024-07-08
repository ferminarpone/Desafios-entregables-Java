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

    @GetMapping("/{id}")
    public ResponseEntity<?> readCartById(@NonNull @PathVariable Long id) {
        try {
            Optional<Invoice_details> cart = service.readInvoiceDetailById(id);
            if (!cart.isPresent()) return new ResponseEntity<>("Cart with id: " + id + " not found", HttpStatus.NOT_FOUND);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartById(@PathVariable Long id) {
        try {
            Optional<Invoice_details> foundCart = service.readInvoiceDetailById(id);
            if (!foundCart.isPresent()) return new ResponseEntity<>("Cart with id: " + id + " not found", HttpStatus.NOT_FOUND);
            service.deleteInvoiceDetail(id);
            return new ResponseEntity<>("Cart successfully deleted", HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/product/{pid}/client/{cid}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long pid, @PathVariable Long cid, @RequestBody AmountProduct amount){
        try {
            Optional<Product> product = productsService.readProductById(pid);
            if (product.isEmpty()) return new ResponseEntity<>("Product with id: " + pid + " not found.", HttpStatus.NOT_FOUND);
            Optional<Client> client = clientsService.readClientById(cid);
            if (client.isEmpty()) return new ResponseEntity<>("Client with id: " + cid + " not found.", HttpStatus.NOT_FOUND);
            Invoice_details cart =  service.addProductToCart(pid, cid, amount.getAmount());
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{pid}/cart/{cid}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long pid, @PathVariable Long cid){
        try {
            Optional<Product> foundProduct = productsService.readProductById(pid);
            if (foundProduct.isEmpty())
                return new ResponseEntity<>("Product with id: " + pid + " not found.", HttpStatus.NOT_FOUND);
            Optional<Invoice_details> foundCart = invoiceDetailsService.readInvoiceDetailById(cid);
            if (foundCart.isEmpty())
                return new ResponseEntity<>("Cart with id: " + cid + " not found.", HttpStatus.NOT_FOUND);
            Invoice_details cart = foundCart.get();
            Product product = foundProduct.get();
            if (!cart.getProduct().equals(product))
                return new ResponseEntity<>("Product not found in cart.", HttpStatus.NOT_FOUND);
            service.deleteProductFromCart(pid, cid);
            return new ResponseEntity<>("Product successfully deleted from cart.", HttpStatus.OK);
        } catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
