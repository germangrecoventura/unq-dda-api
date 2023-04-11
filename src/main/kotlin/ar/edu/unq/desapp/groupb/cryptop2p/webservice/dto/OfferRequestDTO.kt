package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class OfferRequestDTO {
    @DateTimeFormat
    @NotNull(message = "The created date time cannot be blank")
    var created: LocalDateTime? = null

    @NotBlank(message = "The asset name cannot be blank")
    @Pattern(regexp = "[A-Z]+", message = "The asset name can only contain capital letters")
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
    var user: User? = null
}
