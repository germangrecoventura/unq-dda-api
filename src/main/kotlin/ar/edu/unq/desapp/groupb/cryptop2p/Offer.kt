package ar.edu.unq.desapp.groupb.cryptop2p

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

@Entity
@Table(name = "offer")
@JsonPropertyOrder("id", "asset", "nominalAmount", "priceOfAsset", "amountOfPesos", "user", "operation")
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
    var nominalAmount: Double? = null

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    @JsonProperty
    var priceOfAsset: Double? = null

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    @JsonProperty
    var amountOfPesos: Double? = null


    //TODO: CORREGIR ESTE ERROR DE JOIN
    
    @Column(nullable = false)
    @NotNull
    @JsonProperty
    @ManyToOne
    @JoinColumn(name="offer_user")
    var user: User? = null


    @Column(nullable = false)
    @JsonProperty
    var operation: OfferType? = null
}

enum class OfferType {
    BUY, SELL
}
