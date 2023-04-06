package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import jakarta.validation.constraints.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class OfferActiveUserDTO {
    @DateTimeFormat
    @NotNull(message = "The dateTime cannot be blank")
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

    @Min(value = 0, message = "The operation count can't be negative")
    @NotNull(message = "The operation count cannot be blank")
    var operationCount: Int? = null

    @NotBlank(message = "The reputation cannot be blank")
    var reputation: String? = null

    fun toOfferActive(offer: Offer, operationCount: Int, reputation: String): OfferActiveUserDTO {
        var offerActive = OfferActiveUserDTO()
        offerActive.created = offer.created
        offerActive.asset = offer.asset
        offerActive.quantity = offer.quantity
        offerActive.unitPrice = offer.unitPrice
        offerActive.totalAmount = offer.totalAmount
        offerActive.user = offer.user
        offerActive.operationCount = operationCount
        offerActive.reputation = reputation
        return offerActive
    }

}
