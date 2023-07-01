package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class OfferActiveDTO(
    @field:Schema(example = "1")
    var id: Long? = null,

    @field:Schema(example = "2023-05-30T14:55:23.390931")
    var created: LocalDateTime? = null,

    @field:Schema(example = "1")
    var assetId: Long? = null,

    @field:Schema(example = "ALICEUSDT")
    var assetName: String? = null,

    @field:Schema(example = "2")
    var quantity: Double? = null,

    @field:Schema(example = "1.33")
    var unitPrice: Double? = null,

    @field:Schema(example = "2.66")
    var totalAmount: Double? = null,

    @field:Schema(example = "Homero")
    var firstName: String? = null,

    @field:Schema(example = "Simpson")
    var lastName: String? = null,

    var operation: OfferType? = null,

    @field:Schema(example = "4")
    var totalOperations: Int? = null,

    @field:Schema(example = "20")
    var rating: String? = null,
)
