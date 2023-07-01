package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class TransactionCreateRequestDTO(
    @field:NotNull(message = "The user id cannot be blank")
    @field:Schema(example = "1")
    var userId: Long? = null,

    @field:NotNull(message = "The offer id cannot be blank")
    @field:Schema(example = "1")
    var offerId: Long? = null
)
