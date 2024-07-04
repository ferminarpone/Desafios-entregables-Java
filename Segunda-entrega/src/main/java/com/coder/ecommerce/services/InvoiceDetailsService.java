package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.repositories.ClientsRepository;
import com.coder.ecommerce.repositories.InoviceDetailsRepository;
import com.coder.ecommerce.repositories.ProductsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailsService {
    @Autowired
    private InoviceDetailsRepository repository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ClientsRepository clientsRepository;

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

    public Product addProductToCart(@NonNull Long productId, @NonNull Long clientId, Integer amount) throws Exception {
        //Optional<Product> foundProduct = productsService.readProductById(productId);
        Optional<Product> foundProduct = productsRepository.findById(productId);
        if (!foundProduct.isPresent()) throw new Exception("Product not found with id: " + productId);
        Optional<Client> foundClient = clientsRepository.findById(clientId);
        if (!foundClient.isPresent()) throw new Exception("Client not found with id: " + clientId);

        Product product = foundProduct.get();
        Client client = foundClient.get();

        Invoice_details invoiceDetails = new Invoice_details();
        invoiceDetails.setProduct(product);
        invoiceDetails.setClient(client);
        invoiceDetails.setAmount(amount);
        invoiceDetails.setPrice(product.getPrice());
        repository.save(invoiceDetails);

        return product;
    }
}