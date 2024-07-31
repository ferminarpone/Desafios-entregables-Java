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

    public Invoice_details saveInvoiceDetails(Invoice_details invoiceDetails){
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

    public Invoice_details addProductToCart(@NonNull Long productId, @NonNull Long clientId, Integer quantity) throws Exception {
        Optional<Product> foundProduct = productsRepository.findById(productId);
        if (!foundProduct.isPresent()) throw new Exception("Product not found with id: " + productId);
        Optional<Client> foundClient = clientsRepository.findById(clientId);
        if (!foundClient.isPresent()) throw new Exception("Client not found with id: " + clientId);
        Product product = foundProduct.get();
        Client client = foundClient.get();
        //Verificacion de Stock
        Integer stock = product.getStock() - quantity;
        if(stock < 0) throw new Exception("Stock insuficiente para agregar el producto.");
        product.setStock(stock);
        //Creación de carrito
        Invoice_details invoiceDetails = new Invoice_details();
        invoiceDetails.setProduct(product);
        invoiceDetails.setClient(client);
        invoiceDetails.setAmount(quantity);
        invoiceDetails.setPrice(product.getPrice());
        invoiceDetails.setDelivered(false);
        repository.save(invoiceDetails);

        return invoiceDetails;
    }

    public void deleteProductFromCart(Long productId, Long cartId) throws Exception{
        Optional<Product> foundProduct = productsRepository.findById(productId);
        if (!foundProduct.isPresent()) throw new Exception("Product not found with id: " + productId);
        Optional<Invoice_details> foundCart = repository.findById(cartId);
        if (!foundCart.isPresent()) throw new Exception("Cart not found with id: " + cartId);
        Product product = foundProduct.get();
        Invoice_details cart = foundCart.get();
        if(!cart.getProduct().equals(product)) throw new Exception("Product not found in cart.");
        repository.deleteById(cartId);
    }

    public List<Invoice_details> readCartsFromClient(Long clientId) throws Exception{
        Optional<Client> foundClient = clientsRepository.findById(clientId);
        if (!foundClient.isPresent()) throw new Exception("Client not found with id: " + clientId);
        List<Invoice_details> cartList = repository.findByClientAndDelivered(foundClient.get(), false);
        return cartList;
    }
}