package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.*
import java.time.LocalDateTime

class TransactionBuilder {
    private var asset: Asset? = null
    private var quantity: Double? = null
    private var unitPrice: Double? = null
    private var seller: User? = null
    private var buyer: User? = null
    private var offer: Offer? = null
    private var created: LocalDateTime? = null
    private var status: TransactionStatus? = null

    fun build(): Transaction {
        val transaction = Transaction()
        transaction.asset = asset!!
        transaction.quantity = quantity!!
        transaction.unitPrice = unitPrice!!
        transaction.offer = offer!!
        transaction.seller = seller!!
        transaction.buyer = buyer!!
        transaction.created = created!!
        transaction.status = status!!
        return transaction
    }

    fun withAsset(asset: Asset?): TransactionBuilder {
        this.asset = asset
        return this
    }

    fun withQuantity(amount: Double?): TransactionBuilder {
        this.quantity = amount
        return this
    }

    fun withUnitPrice(amount: Double?): TransactionBuilder {
        this.unitPrice = amount
        return this
    }

    fun withSeller(user: User?): TransactionBuilder {
        this.seller = user
        return this
    }

    fun withBuyer(user: User?): TransactionBuilder {
        this.buyer = user
        return this
    }

    fun withOffer(offer: Offer?): TransactionBuilder {
        this.offer = offer
        return this
    }

    fun withCreated(dayTime: LocalDateTime?): TransactionBuilder {
        this.created = dayTime
        return this
    }

    fun withStatus(status: TransactionStatus?): TransactionBuilder {
        this.status = status
        return this
    }
}
