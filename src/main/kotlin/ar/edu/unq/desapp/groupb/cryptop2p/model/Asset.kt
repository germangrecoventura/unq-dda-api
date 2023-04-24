package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@JsonPropertyOrder("id","name", "prices","created")
class Asset(
    @field:Column(nullable = false)
    @field:NotBlank(message = "The name cannot be blank")
    @field:NotBlank(message = "The name asset cannot be blank")
    @field:Pattern(regexp = "^[A-Z0-9-_.]{1,20}$", message = "The asset name is not in a valid format")
    var name: String? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:DateTimeFormat
    var created: LocalDateTime? = null,

    @field:OneToMany(/*mappedBy = "asset"*/)
    val prices: MutableSet<AssetPrice> = mutableSetOf(),
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}
