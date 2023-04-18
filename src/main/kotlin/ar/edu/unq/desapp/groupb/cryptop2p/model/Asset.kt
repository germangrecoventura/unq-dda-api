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
    @field:NotBlank(message = "The symbol cannot be blank")
    @field:Pattern(regexp = "[A-Z]+", message = "The symbol can only contain capital letters")
    var symbol: String? = null,

    @field:Column(nullable = false)
    @field:NotNull
    @field:DateTimeFormat
    var created: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "asset")
    @field:OrderBy("created DESC")
    val prices: MutableSet<AssetPrice> = mutableSetOf(),
)
