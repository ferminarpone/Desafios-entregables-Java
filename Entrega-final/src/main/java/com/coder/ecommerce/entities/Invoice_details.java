package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice_details {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter private Integer amount;

    @Getter @Setter private Double price;

    @Getter @Setter @ManyToOne @JoinColumn(name = "client_id")
    private Client client;

    @Getter @Setter @ManyToOne @JoinColumn(name = "product_id")
    private Product product;
}
