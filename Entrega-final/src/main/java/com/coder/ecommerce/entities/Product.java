package com.coder.ecommerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(description = "Product representation")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique product identifier", example = "1")
    @Getter
    @Setter
    Long id;

    @Schema(description = "Product description", example = "Iphone 15 Pro")
    @Getter
    @Setter
    String description;

    @Schema(description = "Product code", example = "Apple1")
    @Getter
    @Setter
    String code;

    @Schema(description = "Product price", example = "1000")
    @Getter
    @Setter
    Double price;

    @Schema(description = "Product available stock", example = "10")
    @Getter
    @Setter
    Integer stock;

    @Schema(description = "Cart identifier where product is located")
    @Getter
    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice_details> invoiceDetails = new ArrayList<>();
}
