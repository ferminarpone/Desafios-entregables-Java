package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.services.InvoiceDetailsService;
import com.coder.ecommerce.services.InvoicesService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invoices")
public class InovicesController {
    @Autowired
    private InvoicesService service;
    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @GetMapping
    public ResponseEntity<?> readAllInvoices(){
        try {
            List<Invoice> invoicesList = service.readAllInvoices();
            if ( invoicesList.isEmpty() ) return new ResponseEntity<>("Invoice list is empty.", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(invoicesList, HttpStatus.OK);
        } catch(Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " +exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readInvoicetById(@NonNull @PathVariable Long id) {
        try {
            Optional<Invoice> invoice = service.readInvoiceById(id);
            if (!invoice.isPresent()) return new ResponseEntity<>("Invoice with id: " + id + " not found.", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(invoice.get(), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " +exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cart/{cartId}")
    public ResponseEntity<?> createInvoice(@PathVariable @NonNull Long cartId){
        try {
            Optional<Invoice_details> foundCart = invoiceDetailsService.readInvoiceDetailById(cartId);
            if(foundCart.isEmpty())  return new ResponseEntity<>("Cart with id: " + cartId + " not found.", HttpStatus.NOT_FOUND);
            Invoice invoice = service.createInvoice(cartId);
            return new ResponseEntity<>(invoice, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            Optional<Invoice> invoice = service.readInvoiceById(id);
            if (!invoice.isPresent()) return new ResponseEntity<>("Invoice with id: " + id + " not found", HttpStatus.NOT_FOUND);
            service.deleteInvoice(id);
            return new ResponseEntity<>("Invoice successfully deleted", HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
