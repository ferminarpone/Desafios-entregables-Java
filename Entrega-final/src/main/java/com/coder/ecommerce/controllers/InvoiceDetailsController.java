package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice_details;
import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.errors.CartNotFoundError;
import com.coder.ecommerce.errors.ClientNotFoundError;
import com.coder.ecommerce.errors.ProductNotFoundError;
import com.coder.ecommerce.services.ClientsService;
import com.coder.ecommerce.services.InvoiceDetailsService;
import com.coder.ecommerce.services.ProductsService;
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
@RequestMapping(path = "api/v1/carts")
@Tag(name = "Cart routes", description = "CRUD of carts.")
public class InvoiceDetailsController {
    @Autowired
    private InvoiceDetailsService service;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ClientsService clientsService;
    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @GetMapping
    @Operation(summary = "Read all carts from the database.", description = "It returns a list of carts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice_details.class))),
            @ApiResponse(responseCode = "204", description = "Carts not content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> readAllCarts() {
        try {
            List<Invoice_details> cartList = service.readAllInvoiceDetails();
            if (cartList.isEmpty()) return new ResponseEntity<>("Cart list is empty", HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(cartList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cid}")
    @Operation(summary = "Read a single created cart.", description = "This route requires the cart ID as a parameter. It returns the cart's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice_details.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> readCartById(@NonNull @PathVariable Long cid) {
        try {
            Optional<Invoice_details> cart = service.readInvoiceDetailById(cid);
            if (!cart.isPresent())
                return new ResponseEntity<>(new CartNotFoundError("Cart with id: " + cid + " not found"), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(cart.get(), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Create a cart.", description = "It returns the created cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cart created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice_details.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> createCart(Invoice_details data) {
        try {
            Invoice_details cart = service.saveInvoiceDetails(data);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cid}")
    @Operation(summary = "Delete a cart.", description = "This route requires the cart ID as a parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart successfully deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> deleteCartById(@PathVariable Long cid) {
        try {
            Optional<Invoice_details> foundCart = service.readInvoiceDetailById(cid);
            if (!foundCart.isPresent())
                return new ResponseEntity<>(new CartNotFoundError("Cart with id: " + cid + " not found"), HttpStatus.NOT_FOUND);
            service.deleteInvoiceDetail(cid);
            return new ResponseEntity<>("Cart successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Add product to cart.", description = "This route requires the client ID, product ID and the quantity of product you want to buy as a parameter. It returns the cart's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added to cart successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice_details.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Products or Client not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/{clid}/{pid}/{quantity}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long clid, @PathVariable Long pid, @PathVariable Integer quantity) {
        try {
            Optional<Client> client = clientsService.readClientById(clid);
            if (client.isEmpty())
                return new ResponseEntity<>(new ClientNotFoundError("Client with id: " + clid + " not found."), HttpStatus.NOT_FOUND);
            Optional<Product> product = productsService.readProductById(pid);
            if (product.isEmpty())
                return new ResponseEntity<>(new ProductNotFoundError("Product with id: " + pid + " not found."), HttpStatus.NOT_FOUND);
            Integer stock = product.get().getStock() - quantity;
            if (stock < 0)
                return new ResponseEntity<>("Insufficient stock to add the product to the cart.", HttpStatus.CONFLICT);
            Invoice_details cart = service.addProductToCart(pid, clid, quantity);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete product from cart.", description = "This route requires the product ID and cart ID as a parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed from cart successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product, Client or Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{pid}/{cid}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long pid, @PathVariable Long cid) {
        try {
            Optional<Product> foundProduct = productsService.readProductById(pid);
            if (foundProduct.isEmpty())
                return new ResponseEntity<>(new ProductNotFoundError("Product with id: " + pid + " not found."), HttpStatus.NOT_FOUND);
            Optional<Invoice_details> foundCart = invoiceDetailsService.readInvoiceDetailById(cid);
            if (foundCart.isEmpty())
                return new ResponseEntity<>(new ProductNotFoundError("Cart with id: " + cid + " not found."), HttpStatus.NOT_FOUND);
            Invoice_details cart = foundCart.get();
            Product product = foundProduct.get();
            if (!cart.getProduct().equals(product))
                return new ResponseEntity<>(new ProductNotFoundError("Product not found in cart."), HttpStatus.NOT_FOUND);
            service.deleteProductFromCart(pid, cid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Read all the client's carts that haven't been delivered yet.", description = "This route requires the client ID as a parameter. It's return a client cart list with delivered false.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carts with delivered false retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice_details.class))),
            @ApiResponse(responseCode = "204", description = "Cart doesn't have products to delivered.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product, Client or Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/client/{clid}")
    public ResponseEntity<?> readCartsToDeliveredByClient(@PathVariable Long clid) {
        try {
            Optional<Client> client = clientsService.readClientById(clid);
            if (client.isEmpty())
                return new ResponseEntity<>(new ClientNotFoundError("Client with id: " + clid + " not found."), HttpStatus.NOT_FOUND);
            List<Invoice_details> clientCarts = invoiceDetailsService.readCartsFromClient(clid);
            if (clientCarts.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(clientCarts, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
