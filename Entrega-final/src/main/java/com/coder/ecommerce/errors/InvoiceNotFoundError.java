package com.coder.ecommerce.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class InvoiceNotFoundError {
    @Schema(description = "Message error", example = "Invoice with id: 1 not found")
    @Getter
    @Setter
    private String message;

    public InvoiceNotFoundError(String message) {
        this.message = message;
    }
}
