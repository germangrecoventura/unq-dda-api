package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@JsonPropertyOrder("id", "name", "prices", "created")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
class Asset(
    @field:Column(nullable = false)
    @field:NotBlank(message = "The name cannot be blank")
    @field:NotBlank(message = "The name asset cannot be blank")
    @field:Pattern(regexp = "^[A-Z0-9-_.]{1,20}$", message = "The asset name is not in a valid format")
    @field:JsonProperty
    @field:Schema(example = "ALICEUSDT")
    var name: String? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:DateTimeFormat
    @field:CreationTimestamp
    @field:JsonProperty
    var created: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "asset", cascade = [CascadeType.ALL])
    @field:JsonProperty
    val prices: MutableSet<AssetPrice> = mutableSetOf(),
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    @field:JsonProperty
    @field:Schema(example = "1")
    var id: Long? = null
}
