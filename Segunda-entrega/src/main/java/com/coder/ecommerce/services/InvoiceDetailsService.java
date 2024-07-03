package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.repositories.InoviceDetailsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailsService {
    @Autowired
    private InoviceDetailsRepository repository;
    public Invoice_details saveInvoiceDetails(@NonNull Invoice_details invoiceDetails){
        return repository.save(invoiceDetails);
    }

    public List<Invoice_details> readAllInvoiceDetails(){
        return repository.findAll();
    }

    public Optional<Invoice_details> readInvoiceDetailById(@NonNull Long id){
        return repository.findById(id);
    }

    public void deleteInvoiceDetail(@NonNull Long id){
        repository.deleteById(id);
    }
}