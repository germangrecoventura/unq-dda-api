package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@JsonPropertyOrder("id", "asset", "unitPrice")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
class AssetPrice(
    @field:NotNull(message = "The asset cannot be blank")
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JsonProperty
    var asset: Asset? = null,

    @field:Column(nullable = false)
    @field:NotNull(message = "The unit price cannot be blank")
    @field:DecimalMin(value = "0.0", message = "The unit price cannot be less than zero")
    @field:JsonProperty
    var unitPrice: Double? = null,

    @field:Column(nullable = false)
    @field:NotNull(message = "The create date cannot be blank")
    @field:DateTimeFormat
    @field:CreationTimestamp
    @field:JsonProperty
    var created: LocalDateTime? = null,
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    @field:JsonProperty
    @field:Schema(example = "1")
    var id: Long? = null
}
