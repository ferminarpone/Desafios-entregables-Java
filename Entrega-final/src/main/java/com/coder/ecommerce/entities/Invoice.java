package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@NoArgsConstructor @EqualsAndHashCode @ToString
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    @Getter @Setter private Double total;

    @Getter @Setter @ManyToOne @JoinColumn(name = "client_id")
    private Client client;

}
