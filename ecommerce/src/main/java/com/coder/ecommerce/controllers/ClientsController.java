package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.services.ClientsService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/clients")
public class ClientsController {
    @Autowired
    private ClientsService service;

    @GetMapping
    public ResponseEntity<?> readAllClients(){
        try {
            List<Client> clientList = service.readAllClients();
            if ( clientList.isEmpty() ) throw new Exception("Client list is empty");
            return new ResponseEntity<>(clientList, HttpStatus.OK);
        } catch(Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readClientById(@NonNull @PathVariable Long id) {
        try {
            Optional<Client> client = service.readClientById(id);
            if (!client.isPresent())  throw new Exception("Client with id: " + id + " not found");
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @NonNull Client data){
        try {
            Client newClient = new Client(data.getId(), data.getName(), data.getLastName(), data.getDocNumber());
            Client client =  service.createClient(newClient);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody @NonNull Client data){
        try {

            Optional<Client> client =  service.readClientById(id);
            if (!client.isPresent())  throw new Exception("Client with id: " + id + " not found");
            // Continuar logica
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
