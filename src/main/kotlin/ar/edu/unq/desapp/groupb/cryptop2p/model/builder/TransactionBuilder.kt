package ar.edu.unq.desapp.groupb.cryptop2p.model.builder

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.model.Transaction
import ar.edu.unq.desapp.groupb.cryptop2p.model.TransactionStatus
import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import java.time.LocalDateTime

class TransactionBuilder {
    private var asset: String? = null
    private var quantity: Double? = null
    private var unitPrice: Double? = null
    private var totalAmount: Double? = null
    private var seller: User? = null
    private var buyer: User? = null
    private var offer: Offer? = null
    private var created: LocalDateTime? = null
    private var status: TransactionStatus? = null

    fun build(): Transaction {
        var transaction = Transaction().fromModel(
            asset!!,
            quantity!!,
            unitPrice!!,
            totalAmount!!,
            offer!!,
            seller!!,
            buyer!!
        )
        return transaction
    }

    fun withAsset(asset: String?): TransactionBuilder {
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

    fun withTotalAmount(amount: Double?): TransactionBuilder {
        this.totalAmount = amount
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
