package ar.edu.unq.desapp.groupb.cryptop2p.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "asset")
@JsonPropertyOrder("id", "name", "dayTime","unitPrice")
class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Schema(example = "1")
    @JsonProperty
    var id: Long? = null

    @Column(nullable = false)
    @NotBlank(message = "The first name cannot be blank")
    @Pattern(regexp = "[A-Z]+", message = "The name can only contain capital letters")
    // @Schema(example = "Homero")
    @JsonProperty
    var name: String? = null

    @Column(nullable = false)
    @NotNull
    @DateTimeFormat
    // @Schema(example = "Homero")
    @JsonProperty
    var dayTime: LocalDateTime? = null

    @Column(nullable = false)
    @NotNull
    @DecimalMin("0.0")
    // @Schema(example = "Homero")
    @JsonProperty
    var unitPrice: Double? = null
}
