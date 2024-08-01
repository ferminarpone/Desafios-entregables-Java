package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(description = "Invoice representation")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique invoice identifier", example = "1")
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Invoice date")
    private LocalDateTime created_at;

    @Schema(description = "Total invoice price", example = "4000")
    @Getter
    @Setter
    private Double total;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @Schema(description = "Invoice owner client identifier", example = "4")
    @JsonProperty("clientId")
    public Long getClientId() {
        return client != null ? client.getId() : null;
    }

}
