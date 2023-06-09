package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.model.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import java.time.LocalDateTime

class OfferBuilder {
    private var asset: Asset? = null
    private var quantity: Double? = null
    private var unitPrice: Double? = null
    private var totalAmount: Double? = null
    private var user: User? = null
    private var operation: OfferType? = null
    private var isActive: Boolean? = null
    private var created: LocalDateTime? = null

    fun build(): Offer {
        var offer = Offer()
        offer.asset = asset
        offer.quantity = quantity
        offer.unitPrice = unitPrice
        offer.totalAmount = totalAmount
        offer.user = user
        offer.operation = operation
        offer.isActive = isActive
        offer.created = created
        return offer
    }

    fun withAsset(asset: Asset?): OfferBuilder {
        this.asset = asset
        return this
    }

    fun withQuantity(amount: Double?): OfferBuilder {
        this.quantity = amount
        return this
    }

    fun withUnitPrice(amount: Double?): OfferBuilder {
        this.unitPrice = amount
        return this
    }

    fun withTotalAmount(amount: Double?): OfferBuilder {
        this.totalAmount = amount
        return this
    }

    fun withUser(user: User?): OfferBuilder {
        this.user = user
        return this
    }

    fun withOperation(operation: OfferType?): OfferBuilder {
        this.operation = operation
        return this
    }

    fun withActive(isActive: Boolean?): OfferBuilder {
        this.isActive = isActive
        return this
    }

    fun withCreated(dayTime: LocalDateTime?): OfferBuilder {
        this.created = dayTime
        return this
    }
}
