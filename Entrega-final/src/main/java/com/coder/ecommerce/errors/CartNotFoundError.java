package com.coder.ecommerce.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CartNotFoundError {
    @Schema(description = "Message error", example = "Cart with id: 1 not found")
    @Getter
    @Setter
    private String message;

    public CartNotFoundError(String message) {
        this.message = message;
    }
}
