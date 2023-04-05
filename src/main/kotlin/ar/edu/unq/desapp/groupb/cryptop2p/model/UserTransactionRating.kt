package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity(name = "userTransactionRating")
class UserTransactionRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "1")
    @JsonProperty
    var id: Long? = null

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ratingUser")
    var user: User? = null

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ratingTransaction")
    var transaction: Transaction? = null

    @NotBlank
    var rating: Integer? = null
}