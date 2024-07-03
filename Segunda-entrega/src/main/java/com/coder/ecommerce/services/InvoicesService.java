package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.repositories.InvoicesRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoicesService {
    @Autowired private InvoicesRepository repository;
    public Invoice saveInvoice(@NonNull Invoice invoice){
        return repository.save(invoice);
    }

    public List<Invoice> readAllInvoices(){
        return repository.findAll();
    }

    public Optional<Invoice> readInvoiceById(@NonNull Long id){
        return repository.findById(id);
    }

    public void deleteInvoice(@NonNull Long id){
        repository.deleteById(id);
    }
}
