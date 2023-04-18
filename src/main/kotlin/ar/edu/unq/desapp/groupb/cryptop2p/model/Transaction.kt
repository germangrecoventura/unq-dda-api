package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@JsonPropertyOrder("id", "asset", "seller", "buyer")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The asset cannot be blank")
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

    @get:JsonProperty
    val totalAmount: Double
        get() = unitPrice?.let { quantity?.times(it) } ?: 0.0

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonProperty
    var offer: Offer? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonProperty
    var seller: User? = null
        set(value) {
            this.validateTraders(value, this.buyer, "transaction.seller")
            field = value
        }

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonProperty
    var buyer: User? = null
        set(value) {
            this.validateTraders(value, this.seller, "transaction.buyer")
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
    var status: TransactionStatus = TransactionStatus.PENDING

    private fun validateTraders(user: User?, anotherUser: User?, source: String) {
        if (user != null && anotherUser != null && user == anotherUser) {
            throw SameTraderException(source)
        }
    }
}

enum class TransactionStatus {
    PENDING, TRANSFER_COMPLETED, RECEPTION_COMPLETED, CANCELED
}

class SameTraderException(source: String) :
    ModelException("The buyer and the seller cannot be the same user", source)
