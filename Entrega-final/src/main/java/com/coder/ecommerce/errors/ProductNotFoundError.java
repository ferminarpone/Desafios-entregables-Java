package com.coder.ecommerce.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductNotFoundError{
    @Schema(description = "Message error", example = "Product with id: 1 not found")
    @Getter @Setter private String message;

    public ProductNotFoundError(String message) {
        this.message = message;
    }
}
