package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@JsonPropertyOrder("id", "user", "rating")
class UserTransactionRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    var id: Long? = null

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty
    var user: User? = null

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty
    var transaction: Transaction? = null

    @Column(nullable = false)
    @NotNull
    @JsonProperty
    var rating: Int? = null

    @Column(nullable = false)
    @NotNull
    @CreationTimestamp
    @JsonProperty
    var created: LocalDateTime? = null
}
