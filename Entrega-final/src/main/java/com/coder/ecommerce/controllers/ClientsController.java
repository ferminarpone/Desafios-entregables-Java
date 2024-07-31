package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.errors.ClientNotFoundError;
import com.coder.ecommerce.errors.FieldMissingError;
import com.coder.ecommerce.services.ClientsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/clients")
@Tag(name="Client routes.", description = "CRUD of clients.")
public class ClientsController {
    @Autowired
    private ClientsService service;

    @GetMapping
    @Operation(summary = "Read all created clients.", description = "It returns a List of clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "204", description = "Clients not content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> readAllClients(){
        try {
            List<Client> clientList = service.readAllClients();
            if ( clientList.isEmpty() ) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(clientList, HttpStatus.OK);
        } catch(Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " +exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{clid}")
    @Operation(summary = "Read a single created client.", description = "This route requires the client ID as a parameter. It returns the client's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> readClientById(@NonNull @PathVariable Long clid) {
        try {
            Optional<Client> client = service.readClientById(clid);
            if (!client.isPresent()) return new ResponseEntity<>(new ClientNotFoundError("Client with id: " + clid + " not found."), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " +exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/register")
    @Operation(summary = "Create a client.", description = "This route requires the complete client data in the body (Name, Last name, Doc. number). It returns the created client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FieldMissingError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> createClient(@RequestBody @NonNull Client data){
        try {
            if (data.getName() == null || data.getLastName() == null || data.getDocNumber() == null)
                return new ResponseEntity<>(new FieldMissingError("Required field is missing."), HttpStatus.BAD_REQUEST);
            Client client =  service.saveClient(data);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("auth/me/{clid}")
    @Operation(summary = "Update a client.", description = "This route requires the client ID as a parameter and the client data you want to update in the body. It returns the updated client.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "application/json" ,schema = @Schema(implementation = ClientNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> updateClient(@PathVariable Long clid, @RequestBody Client data){
        try {
            Optional<Client> foundClient =  service.readClientById(clid);
            if (!foundClient.isPresent()) return new ResponseEntity<>(new ClientNotFoundError("Client with id: " + clid + " not found"), HttpStatus.NOT_FOUND);
            Client updatedClient = foundClient.get();
            updatedClient.setName(data.getName());
            updatedClient.setLastName(data.getLastName());
            updatedClient.setDocNumber(data.getDocNumber());
            service.saveClient(updatedClient);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{clid}")
    @Operation(summary = "Delete a client.", description = "This route requires the client ID as a parameter.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Client successfully deleted", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> deleteById(@PathVariable Long clid){
        try {
            Optional<Client> foundClient = service.readClientById(clid);
            if (!foundClient.isPresent()) return new ResponseEntity<>(new ClientNotFoundError("Client with id: " + clid + " not found"), HttpStatus.NOT_FOUND);
            service.deleteClient(clid);
            return new ResponseEntity<>("Client successfully deleted", HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
