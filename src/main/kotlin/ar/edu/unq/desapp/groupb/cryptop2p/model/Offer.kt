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
@JsonPropertyOrder("id", "user", "isActive")
class Offer(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    @field:JsonProperty
    var id: Long? = null,

    @field:NotNull(message = "The asset cannot be blank")
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "asset_id", nullable = false)
    @field:JsonProperty
    var asset: Asset? = null,

    @field:Column(nullable = false)
    @field:NotNull(message = "The quantity cannot be blank")
    @field:DecimalMin("0.0", message = "The quantity can't be negative")
    @field:JsonProperty
    var quantity: Double? = null,

    @field:Column(nullable = false)
    @field:NotNull(message = "The unit price cannot be blank")
    @field:DecimalMin("0.0", message = "The unit price cannot be negative")
    @field:JsonProperty
    var unitPrice: Double? = null,

    @field:Column(nullable = false)
    @field:NotNull(message = "The total amount cannot be blank")
    @field:DecimalMin("0.0", message = "The total amount cannot be negative")
    @field:JsonProperty
    var totalAmount: Double? = null,

    @field:NotNull(message = "The user cannot be blank")
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JsonProperty
    var user: User? = null,

    @field:Column(nullable = false)
    @field:JsonProperty
    var operation: OfferType? = null,

    @field:Column(nullable = false)
    @field:NotNull(message = "The active flag cannot be blank")
    @field:JsonProperty
    var isActive: Boolean? = null,

    @field:Column(nullable = false)
    @field:DateTimeFormat
    @field:CreationTimestamp
    @field:NotNull(message = "The created date time cannot be blank")
    @field:JsonProperty
    var created: LocalDateTime? = null,
)

enum class OfferType {
    BUY, SELL
}
