package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Cart representation")
public class Invoice_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique cart identifier", example = "1")
    @Getter
    @Setter
    private Long id;

    @Schema(description = "Amount of product in cart", example = "2")
    @Getter
    @Setter
    private Integer amount;

    @Schema(description = "Price of product in cart", example = "100")
    @Getter
    @Setter
    private Double price;

    @Schema(description = "Boolean indicating whether the product was delivered", example = "false")
    @Getter
    @Setter
    private Boolean delivered;

    @Schema(description = "Cart owner client identifier", example = "4")
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @Schema(description = "Product identifier located in cart", example = "2")
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Schema(description = "Cart owner client identifier", example = "4")
    @JsonProperty("clientId")
    public Long getClientId() {
        return client != null ? client.getId() : null;
    }

    @Schema(description = "Product identifier located in cart", example = "2")
    @JsonProperty("productId")
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }
}
