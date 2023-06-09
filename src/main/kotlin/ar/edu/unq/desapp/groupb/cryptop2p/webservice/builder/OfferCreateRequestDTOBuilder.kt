package ar.edu.unq.desapp.groupb.cryptop2p.webservice.builder

import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferRequestDTO

class OfferCreateRequestDTOBuilder {
    private var asset: String? = null
    private var quantity: Double? = null
    private var unitPrice: Double? = null
    private var user: Long? = null
    private var operation: String? = null

    fun build(): OfferRequestDTO {
        return OfferRequestDTO(
            asset,
            quantity,
            unitPrice,
            user,
            operation,
        )
    }

    fun withAsset(asset: String?): OfferCreateRequestDTOBuilder {
        this.asset = asset
        return this
    }

    fun withQuantity(quantity: Double?): OfferCreateRequestDTOBuilder {
        this.quantity = quantity
        return this
    }

    fun withUnitPrice(unitPrice: Double?): OfferCreateRequestDTOBuilder {
        this.unitPrice = unitPrice
        return this
    }

    fun withUser(user: Long?): OfferCreateRequestDTOBuilder {
        this.user = user
        return this
    }

    fun withOperation(operation: String?): OfferCreateRequestDTOBuilder {
        this.operation = operation
        return this
    }
}
