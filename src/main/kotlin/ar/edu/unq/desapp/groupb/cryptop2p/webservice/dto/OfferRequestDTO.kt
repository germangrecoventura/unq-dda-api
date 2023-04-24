package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

class OfferRequestDTO {
    @NotBlank(message = "The name cannot be blank")
    @Pattern(
        regexp = "^[A-Z0-9-_.]{1,20}$",
        message = "The asset name is not in a valid format"
    )
    @Schema(example = "ALICEUSDT")
    var asset: String? = null

    @NotNull(message = "The quantity cannot be blank")
    @DecimalMin("0.0", message = "The quantity can't be negative")
    var quantity: Double? = null

    @NotNull(message = "The unit price cannot be blank")
    @DecimalMin("0.0", message = "The unit price can't be negative")
    var unitPrice: Double? = null

    @NotNull(message = "The total amount cannot be blank")
    @DecimalMin("0.0", message = "The total amount can't be negative")
    var totalAmount: Double? = null

    @NotNull(message = "The user cannot be blank")
    var user: Long? = null

    @NotBlank(message = "The operation type cannot be blank")
    var operation: String? = null
}
