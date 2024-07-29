package com.coder.ecommerce.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductFieldMissingError {
    @Schema(description = "Message error", example = "Required field is missing.")
    @Getter
    @Setter
    private String message;

    public ProductFieldMissingError(String message) {
        this.message = message;
    }
}
