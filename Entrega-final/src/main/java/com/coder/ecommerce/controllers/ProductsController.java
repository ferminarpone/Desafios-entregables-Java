package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.errors.FieldMissingError;
import com.coder.ecommerce.errors.ProductNotFoundError;
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
@RequestMapping(path = "api/v1/products")
@Tag(name = "Product routes", description = "CRUD of products.")
public class ProductsController {
    @Autowired
    private ProductsService service;

    @GetMapping
    @Operation(summary = "Read all created products.", description = "It returns a List of products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "204", description = "Products not content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> readAllProducts() {
        try {
            List<Product> productList = service.readAllProducts();
            if (productList.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Read a single created product.", description = "This route requires the product ID as a parameter. It returns the product's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> readProductById(@NonNull @PathVariable Long id) {
        try {
            Optional<Product> product = service.readProductById(id);
            if (!product.isPresent())
                return new ResponseEntity<>("Product with id: " + id + " not found", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a product.", description = "This route requires the complete product data in the body (Description, Code, Price, Stock). It returns the created product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FieldMissingError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @NonNull Product data) {
        try {
            if (data.getDescription() == null || data.getCode() == null || data.getPrice() == null || data.getStock() == null)
                return new ResponseEntity<>(new FieldMissingError("Required field is missing."), HttpStatus.BAD_REQUEST);
            Product product = service.saveProduct(data);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a product.", description = "This route requires the product ID as a parameter and the product data you want to update in the body. It returns the updated product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product data) {
        try {
            Optional<Product> foundProduct = service.readProductById(id);
            if (!foundProduct.isPresent())
                return new ResponseEntity<>(new ProductNotFoundError("Product with id: " + id + " not found"), HttpStatus.NOT_FOUND);
            Product updatedProduct = foundProduct.get();
            if (data.getDescription() != null) updatedProduct.setDescription(data.getDescription());
            if (data.getCode() != null) updatedProduct.setCode(data.getCode());
            if (data.getPrice() != null) updatedProduct.setPrice(data.getPrice());
            if (data.getStock() != null) updatedProduct.setStock(data.getStock());
            service.saveProduct(updatedProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product.", description = "This route requires the product ID as a parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductNotFoundError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain"))
    })
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        try {
            Optional<Product> foundProduct = service.readProductById(id);
            if (!foundProduct.isPresent())
                return new ResponseEntity<>("Product with id: " + id + " not found", HttpStatus.NOT_FOUND);
            service.deleteProduct(id);
            return new ResponseEntity<> (HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
