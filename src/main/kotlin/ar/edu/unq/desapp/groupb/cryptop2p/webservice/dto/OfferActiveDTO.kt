package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import java.time.LocalDateTime

class OfferActiveDTO {
    var date: LocalDateTime? = null
    var asset: Asset? = null
    var quantity: Double? = null
    var unitPrice: Double? = null
    var totalAmount: Double? = null
    var firstName: String? = null
    var lastName: String? = null
    var operation: OfferType? = null
    var sizeOperations: Int? = null
    var rating: String? = null
}