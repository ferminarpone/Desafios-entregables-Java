package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.services.ClientsService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/clients")
public class ClientsController {
    @Autowired
    private ClientsService service;

    @GetMapping
    public List<Client> readAllClients(){
        try {
            return service.readAllClients();
        } catch(Exception exception){
            System.out.println(exception);
            throw new RuntimeException("READ ALL ERROR");
        }
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client data){
        try {
            Client newClient = new Client(data.getId(), data.getName(), data.getLastName(), data.getDocNumber());
            //VER TEMA DE ERRORES
            Client client =  service.createClient(newClient);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }catch (Exception exception){
            //System.out.println(exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
