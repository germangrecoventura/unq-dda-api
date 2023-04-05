package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity(name = "transaction")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "1")
    @JsonProperty
    var id: Long? = null

    @Column(nullable = false)
    @NotBlank(message = "The first name cannot be blank")
    @Pattern(regexp = "[A-Z]+", message = "The name can only contain capital letters")
    @JsonProperty
    var asset: String? = null

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
    @JoinColumn(name = "transactionOffer")
    @JsonProperty
    var offer: Offer? = null

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionSeller")
    @JsonProperty
    var seller: User? = null
        set(value) {
            this.validateSeller(value)
            field = value
        }

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionBuyer")
    @JsonProperty
    var buyer: User? = null
        set(value) {
            this.validateBuyer(value)
            field = value
        }


    @Column(nullable = false)
    @DateTimeFormat
    @NotNull
    @JsonProperty
    var created: LocalDateTime? = null

    @Column(nullable = true)
    @NotNull
    @JsonProperty
    var addressee: String? = null

    @Column(nullable = false)
    @NotNull
    @JsonProperty
    var status: TransactionStatus? = TransactionStatus.WAITING

    private fun validateSeller(value: User?) {
        if (offer != null && offer!!.operation == OfferType.SELL && offer!!.user != value) {
            throw RuntimeException("The seller is not the same as the offer")
        }
    }

    private fun validateBuyer(value: User?) {
        if (offer != null && offer!!.operation == OfferType.BUY && offer!!.user != value) {
            throw RuntimeException("The seller is not the same as the offer")
        }
    }

    fun fromModel(
        asset: String,
        quantity: Double,
        unitPrice: Double,
        totalAmount: Double,
        offer: Offer,
        seller: User,
        buyer: User
    ): Transaction {
        if (seller == buyer) {
            throw RuntimeException("The seller cannot be the same buyer")
        }
        if (offer.operation == OfferType.SELL && offer.user != seller) {
            throw RuntimeException("The seller is not the same as the offer")
        }
        if (offer.operation == OfferType.BUY && offer.user != buyer) {
            throw RuntimeException("The buyer is not the same as the offer")
        }

        var transaction = Transaction()
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
            transaction.addressee = buyer.cryptoWalletAddress
        } else {
            transaction.addressee = seller.cvu
        }
        return transaction
    }

    fun transfered() {
        this.status = TransactionStatus.TRANSFERRED
    }

    fun confirmed() {
        this.status = TransactionStatus.CONFIRMED
    }
}

enum class TransactionStatus {
    WAITING, TRANSFERRED, CONFIRMED, CANCELED
}
