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
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "offerUser")
    @JsonProperty
    var user: User? = null


    @Column(nullable = false)
    @JsonProperty
    var operation: OfferType? = null

    @Column(nullable = false)
    @NotNull
    @JsonProperty
    var isActive: Boolean? = null

    @Column(nullable = false)
    @DateTimeFormat
    @NotNull
    @JsonProperty
    var created: LocalDateTime? = null
}

enum class OfferType {
    BUY, SELL
}
