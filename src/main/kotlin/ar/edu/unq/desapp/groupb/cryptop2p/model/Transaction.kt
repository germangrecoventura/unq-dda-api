package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

val ERROR_MESSAGE = "The seller is not the same as the offer"

@Entity
@JsonPropertyOrder("id", "asset", "seller", "buyer")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "1")
    @JsonProperty
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The asset name cannot be blank")
    @JsonProperty
    var asset: Asset? = null

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    @JsonProperty
    var quantity: Double? = null

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    @JsonProperty
    var unitPrice: Double? = null

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    @JsonProperty
    var totalAmount: Double? = null

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty
    var offer: Offer? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty
    var seller: User? = null
        set(value) {
            this.validateSeller(value)
            field = value
        }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty
    var buyer: User? = null
        set(value) {
            this.validateBuyer(value)
            field = value
        }

    @Column(nullable = false)
    @DateTimeFormat
    @CreationTimestamp
    @NotNull
    @JsonProperty
    var created: LocalDateTime? = null

    @Column(nullable = true)
    @NotNull
    @JsonProperty
    var address: String? = null

    @Column(nullable = false)
    @NotNull
    @JsonProperty
    var status: TransactionStatus? = TransactionStatus.WAITING

    private fun validateSeller(value: User?) {
        if (offer != null && offer!!.operation == OfferType.SELL && offer!!.user != value) {
            throw RuntimeException(ERROR_MESSAGE)
        }
    }

    private fun validateBuyer(value: User?) {
        if (offer != null && offer!!.operation == OfferType.BUY && offer!!.user != value) {
            throw RuntimeException(ERROR_MESSAGE)
        }
    }

    fun fromModel(
        asset: Asset,
        quantity: Double,
        unitPrice: Double,
        totalAmount: Double,
        offer: Offer,
        seller: User,
        buyer: User
    ): Transaction {
        if (seller == buyer) {
            throw ModelException("The seller cannot be the same buyer", "transaction.seller")
        }
        if (offer.operation == OfferType.SELL && offer.user != seller) {
            throw ModelException(ERROR_MESSAGE, "transaction.type")
        }
        if (offer.operation == OfferType.BUY && offer.user != buyer) {
            throw ModelException("The buyer is not the same as the offer", "transaction.buyer")
        }

        val transaction = Transaction()
        transaction.asset = asset
        transaction.quantity = quantity
        transaction.unitPrice = unitPrice
        transaction.totalAmount = totalAmount
        transaction.offer = offer
        transaction.seller = seller
        transaction.buyer = buyer
        transaction.created = LocalDateTime.now()
        transaction.status = TransactionStatus.WAITING
        if (offer.operation == OfferType.BUY) {
            transaction.address = buyer.cryptoWalletAddress
        } else {
            transaction.address = seller.cvu
        }
        return transaction
    }

    fun transferred() {
        this.status = TransactionStatus.TRANSFERRED
    }

    fun confirmed() {
        this.status = TransactionStatus.CONFIRMED
    }
}

enum class TransactionStatus {
    WAITING, TRANSFERRED, CONFIRMED, CANCELED
}
