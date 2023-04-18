package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@JsonPropertyOrder("id", "asset", "unitPrice")
class AssetPrice(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    @field:JsonProperty
    var id: Long? = null,

    @field:NotNull(message = "The asset cannot be blank")
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "asset_id", nullable = false)
    @field:JsonProperty
    var asset: Asset?,

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

    @field:Column(nullable = false)
    @field:NotNull(message = "The updated date cannot be blank")
    @field:DateTimeFormat
    @field:UpdateTimestamp
    @field:JsonProperty
    var updated: LocalDateTime? = null,
)
