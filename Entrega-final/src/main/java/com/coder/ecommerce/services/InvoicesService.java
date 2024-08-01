package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.repositories.ClientsRepository;
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
    @Autowired
    private InvoicesRepository repository;
    @Autowired
    private InoviceDetailsRepository invoiceDetailsRepository;
    @Autowired
    private ClientsRepository clientsRepository;

    public Invoice createInvoice(@NonNull Long clientId) throws Exception {
        Optional<Client> foundClient = clientsRepository.findById(clientId);
        if (foundClient.isEmpty()) throw new Exception("Client not found with id: " + clientId);
        List<Invoice_details> carts = invoiceDetailsRepository.findByClientAndDelivered(foundClient.get(), false);
        if (carts.isEmpty()) throw new Exception("This client doesn't have any cart to deliver.");
        //Generar invoice
        Invoice invoice = new Invoice();
        Client client = foundClient.get();
        invoice.setClient(client);
        invoice.setCreated_at(LocalDateTime.now());
        double total = 0.0;
        for (Invoice_details cart : carts) {
            total += cart.getAmount() * cart.getPrice();
            cart.setDelivered(true);
        }
        invoice.setTotal(total);
        return repository.save(invoice);
    }

    public List<Invoice> readAllInvoices() {
        return repository.findAll();
    }

    public Optional<Invoice> readInvoiceById(@NonNull Long id) {
        return repository.findById(id);
    }

    public void deleteInvoice(@NonNull Long id) {
        repository.deleteById(id);
    }

    public Invoice readLastInvoiceByClientId(@NonNull Long clientId) throws Exception {
        Optional<Client> foundClient = clientsRepository.findById(clientId);
        if (foundClient.isEmpty()) throw new Exception("Client not found with id: " + clientId);
        List<Invoice> clientInvoices = foundClient.get().getInvoices();
        if (clientInvoices.isEmpty()) throw new Exception("This client hasn't got any invoices yet.");
        Invoice lastInvoice = clientInvoices.get(clientInvoices.size() - 1);
        return lastInvoice;
    }
}
