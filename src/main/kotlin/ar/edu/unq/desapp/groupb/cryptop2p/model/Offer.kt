package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "offer")
@JsonPropertyOrder("id", "asset", "quantity", "unitPrice", "totalAmount", "user", "operation", "isActive", "created")
class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    var id: Long? = null

    @Column(nullable = false)
    @NotBlank(message = "The asset name cannot be blank")
    @Pattern(regexp = "[A-Z]+", message = "The asset name can only contain capital letters")
    @JsonProperty
    var asset: String? = null

    @Column(nullable = false)
    @NotNull(message = "The quantity cannot be blank")
    @DecimalMin("0.0", message = "The quantity can't be negative")
    @JsonProperty
    var quantity: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The unit price cannot be blank")
    @DecimalMin("0.0", message = "The unit price can't be negative")
    @JsonProperty
    var unitPrice: Double? = null

    @Column(nullable = false)
    @NotNull(message = "The total amount cannot be blank")
    @DecimalMin("0.0", message = "The total amount can't be negative")
    @JsonProperty
    var totalAmount: Double? = null

    @NotNull(message = "The user cannot be blank")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "offerUser")
    @JsonProperty
    var user: User? = null


    @Column(nullable = false)
    @JsonProperty
    var operation: OfferType? = null

    @Column(nullable = false)
    @NotNull(message = "The active cannot be blank")
    @JsonProperty
    var isActive: Boolean? = null

    @Column(nullable = false)
    @DateTimeFormat
    @NotNull(message = "The dateTime cannot be blank")
    @JsonProperty
    var created: LocalDateTime? = null
}

enum class OfferType {
    BUY, SELL
}
