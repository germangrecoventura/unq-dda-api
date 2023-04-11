package ar.edu.unq.desapp.groupb.cryptop2p.model

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class Asset(
    @field:NotBlank(message = "The name cannot be blank")
    @field:Pattern(regexp = "[A-Z]+", message = "The name can only contain capital letters")
    var name: String? = null,

    @field:NotNull
    @field:DecimalMin("0.0")
    var unitPrice: Double? = null,

    @field:NotNull
    @field:DateTimeFormat
    var created: LocalDateTime? = null,

    @field:NotNull
    @field:DateTimeFormat
    var updated: LocalDateTime? = null,
)
