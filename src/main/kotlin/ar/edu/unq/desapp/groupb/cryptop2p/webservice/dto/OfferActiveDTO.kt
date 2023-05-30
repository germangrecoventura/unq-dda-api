package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class OfferActiveDTO {
    @Schema(example = "1")
    var id: Long? = null

    @Schema(example = "2023-05-30T14:55:23.390931")
    var created: LocalDateTime? = null

    @Schema(example = "1")
    var assetId: Long? = null

    @Schema(example = "ALICEUSDT")
    var assetName: String? = null

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
    var totalOperations: Int? = null

    @Schema(example = "20")
    var rating: String? = null
}
