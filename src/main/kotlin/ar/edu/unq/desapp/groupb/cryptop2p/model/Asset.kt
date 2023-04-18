package ar.edu.unq.desapp.groupb.cryptop2p.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
class Asset(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @field:Column(nullable = false)
    @field:NotBlank(message = "The name cannot be blank")
    @field:Pattern(regexp = "[A-Z]+", message = "The name can only contain capital letters")
    var name: String? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:DateTimeFormat
    var created: LocalDateTime? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:DateTimeFormat
    var updated: LocalDateTime? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:OneToMany(mappedBy = "asset")
    val prices: List<AssetPrice>? = null,
)
