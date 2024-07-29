package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    //Agregar Delivered??

    @Getter @Setter @ManyToOne @JoinColumn(name = "client_id") @JsonIgnore
    private Client client;

    @Getter @Setter @ManyToOne @JoinColumn(name = "product_id") @JsonIgnore
    private Product product;

    @JsonProperty("productId")
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }

    @JsonProperty("clientId")
    public Long getClientId() {
        return client != null ? client.getId() : null;
    }
}
