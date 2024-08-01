package com.coder.ecommerce.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(description = "Client representation")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique client identifier", example = "1")
    @Getter
    @Setter
    private Long id;

    @Schema(description = "Client name", example = "Sergio")
    @Getter
    @Setter
    private String name;

    @Schema(description = "Client last name", example = "Vargas")
    @Getter
    @Setter
    private String lastName;

    @Schema(description = "Client document number", example = "39218")
    @Getter
    @Setter
    private Integer docNumber;

    @Schema(description = "Inovice identifiers")
    @Column
    @Getter
    @Setter
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();

    @Schema(description = "Client cart identifiers")
    @Column
    @Getter
    @Setter
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice_details> invoiceDetails = new ArrayList<>();
}


