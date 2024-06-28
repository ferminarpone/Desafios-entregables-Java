package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.repositories.ClientsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {
    @Autowired private ClientsRepository repository;

    public Client createClient(@NonNull Client client){
        return repository.save(client);
    }

    public List<Client> readAllClients(){
        return repository.findAll();
    }

    public Optional<Client> readClientById(@NonNull Long id){
        return repository.findById(id);
    }

    public void deleteClient(@NonNull Long id){
        repository.deleteById(id);
    }
}
