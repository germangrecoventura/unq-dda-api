package ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

class TransactionDTO {
    @NotNull(message = "The user id cannot be blank")
    @Schema(example = "1")
    var userId: Long? = null

    @NotNull(message = "The transaction id cannot be blank")
    @Schema(example = "1")
    var transactionId: Long? = null
}