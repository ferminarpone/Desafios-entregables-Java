package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.errors.ClientNotFoundError;
import com.coder.ecommerce.services.ClientsService;
import com.coder.ecommerce.services.InvoiceDetailsService;
import com.coder.ecommerce.services.InvoicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invoices")
@Tag(name="Invoices routes", description = "CRUD of inovices.")
public class InovicesController {
    @Autowired
    private InvoicesService service;
    @Autowired
    private InvoiceDetailsService invoiceDetailsService;
    @Autowired
    private ClientsService clientsService;

    @GetMapping
    @Operation(summary = "Read all invoices from data base.", description = "It returns a List of invoices.")

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

    @GetMapping("/invoiceId/{id}")
    @Operation(summary = "Read a single invoice.", description = "This route requires the invoice ID as a parameter. It returns the invoice's data.")
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

    @GetMapping("/{clid}")
    public ResponseEntity<?> readLastInvoiceByClientId(@PathVariable Long clid){
        try {
            Optional<Client> client = clientsService.readClientById(clid);
            if (client.isEmpty()) return new ResponseEntity<>(new ClientNotFoundError("Client with id: " + clid + " not found."), HttpStatus.NOT_FOUND);
            Invoice invoice = service.readLastInvoiceByClientId(clid);
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{clid}")
    @Operation(summary = "Create an invoice.", description = "This route requires the client ID as a parameter. It returns the created invoice.")
    public ResponseEntity<?> createInvoice(@PathVariable @NonNull Long clid){
        try {
            Optional<Client> client = clientsService.readClientById(clid);
            if (client.isEmpty()) return new ResponseEntity<>(new ClientNotFoundError("Client with id: " + clid + " not found."), HttpStatus.NOT_FOUND);
            List<Invoice_details> carts = invoiceDetailsService.readCartsFromClient(clid);
            if (carts.isEmpty()) return new ResponseEntity<>(new ClientNotFoundError("This client doesn't have any cart to deliver."),HttpStatus.NOT_FOUND);
            Invoice invoice = service.createInvoice(clid);
            return new ResponseEntity<>(invoice, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an invoice.", description = "This route requires the invoice ID as a parameter.")
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
