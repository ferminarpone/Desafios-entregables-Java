package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@NoArgsConstructor @EqualsAndHashCode @ToString
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String lastName;
    @Getter @Setter private Integer docNumber;
}
