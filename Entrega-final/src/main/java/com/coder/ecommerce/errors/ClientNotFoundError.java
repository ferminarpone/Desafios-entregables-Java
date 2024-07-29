package com.coder.ecommerce.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ClientNotFoundError {
    @Schema(description = "Message error", example = "Client with id: 1 not found")
    @Getter
    @Setter
    private String message;

    public ClientNotFoundError(String message) {
        this.message = message;
    }
}
