package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@JsonPropertyOrder("id", "user", "rating")
class UserTransactionRating(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    @field:JsonProperty
    var id: Long? = null,

    @field:NotNull
    @field:OneToOne(fetch = FetchType.LAZY)
    @field:JsonProperty
    var user: User? = null,

    @field:NotNull
    @field:OneToOne(fetch = FetchType.LAZY)
    @field:JsonProperty
    var transaction: Transaction? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:Min(value = -20, message = "The rating cannot be lower than -20")
    @field:Max(value = 10, message = "The rating cannot be greater than 10")
    @field:JsonProperty
    var rating: Int? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:CreationTimestamp
    @field:JsonProperty
    var created: LocalDateTime? = null,
)
