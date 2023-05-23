package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class OfferActiveDTO {
    var date: LocalDateTime? = null
    var asset: Asset? = null

    @Schema(example = "2")
    var quantity: Double? = null

    @Schema(example = "1.33")
    var unitPrice: Double? = null

    @Schema(example = "2.66")
    var totalAmount: Double? = null

    @Schema(example = "Homero")
    var firstName: String? = null

    @Schema(example = "Simpson")
    var lastName: String? = null
    
    var operation: OfferType? = null

    @Schema(example = "4")
    var sizeOperations: Int? = null

    @Schema(example = "20")
    var rating: String? = null
}
