package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@NoArgsConstructor @EqualsAndHashCode @ToString @AllArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;
    @Getter @Setter @NonNull  private String name;
    @Getter @Setter @NonNull  private String lastName;
    @Getter @Setter @NonNull  private Integer docNumber;

}


