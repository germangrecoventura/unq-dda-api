package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class OfferCreateRequestDTO(
    @field:NotNull(message = "The asset id cannot be blank")
    var assetId: Long? = null,

    @field:NotNull(message = "The quantity cannot be blank")
    @field:DecimalMin("0.0", message = "The quantity can't be negative")
    var quantity: Double? = null,

    @field:NotNull(message = "The unit price cannot be blank")
    @field:DecimalMin("0.0", message = "The unit price can't be negative")
    var unitPrice: Double? = null,

    @field:NotNull(message = "The user id cannot be blank")
    var userId: Long? = null,

    @field:NotBlank(message = "The type cannot be blank")
    var type: OfferType? = null,

    @field:DateTimeFormat
    @field:NotNull(message = "The created date time cannot be blank")
    var created: LocalDateTime? = null,
)
