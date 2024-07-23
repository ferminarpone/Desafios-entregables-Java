package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor @EqualsAndHashCode @ToString
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter Long id;

    @Getter @Setter String description;

    @Getter @Setter String code;

    @Getter @Setter Double price;

    @Getter @Setter Integer stock;

    @Getter @Setter @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice_details> invoiceDetails = new ArrayList<>();
}
