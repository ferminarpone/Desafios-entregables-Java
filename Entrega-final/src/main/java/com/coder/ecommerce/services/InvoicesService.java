package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.repositories.InvoicesRepository;
import com.coder.ecommerce.repositories.InoviceDetailsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoicesService {
    @Autowired private InvoicesRepository repository;
    @Autowired private InoviceDetailsRepository invoiceDetailsRepository;

    public Invoice createInvoice(@NonNull Long cartId) throws Exception{
        Optional<Invoice_details> foundCart = invoiceDetailsRepository.findById(cartId);
        if(!foundCart.isPresent()) throw new Exception("Cart not found with id: " + cartId);
        Invoice_details cart = foundCart.get();
        Invoice invoice = new Invoice();
        invoice.setClient(cart.getClient());
        Double total = cart.getAmount() * cart.getProduct().getPrice();
        invoice.setTotal(total);
        invoice.setCreated_at(LocalDateTime.now());
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
