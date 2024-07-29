package com.coder.ecommerce.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FieldMissingError {
    @Schema(description = "Message error", example = "Required field is missing.")
    @Getter
    @Setter
    private String message;

    public FieldMissingError(String message) {
        this.message = message;
    }
}
