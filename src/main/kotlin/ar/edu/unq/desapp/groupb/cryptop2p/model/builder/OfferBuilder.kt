package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.OfferType
import ar.edu.unq.desapp.groupb.cryptop2p.model.User

class OfferBuilder {
    private var asset: String? = null
    private var nominalAmount: Double? = null
    private var priceOfAsset: Double? = null
    private var amountOfPesos: Double? = null
    private var user: User? = null
    private var operation: OfferType? = null

    fun build(): Offer {
        var offer = Offer()
        offer.asset = asset
        offer.nominalAmount = nominalAmount
        offer.priceOfAsset = priceOfAsset
        offer.amountOfPesos = amountOfPesos
        offer.user = user
        offer.operation = operation
        return offer
    }

    fun withAsset(asset: String?): OfferBuilder {
        this.asset = asset
        return this
    }

    fun withNominalAmount(amount: Double?): OfferBuilder {
        this.nominalAmount = amount
        return this
    }

    fun withPriceOfAsset(amount: Double?): OfferBuilder {
        this.priceOfAsset = amount
        return this
    }

    fun withAmountOfPesos(amount: Double??): OfferBuilder {
        this.amountOfPesos = amount
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
}
