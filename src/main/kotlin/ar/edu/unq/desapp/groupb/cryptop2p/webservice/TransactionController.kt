package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.Transaction
import ar.edu.unq.desapp.groupb.cryptop2p.service.TransactionService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.TransactionCancelDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.TransactionDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
@Tag(name = "transaction", description = "Endpoints for managing transaction")
@RequestMapping("transaction")
class TransactionController(private val transactionService: TransactionService) {
    @PostMapping
    @Operation(
        summary = "Registers a new transaction",
        description = "Registers a transaction",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Transaction::class),
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ValidationErrorResponseDTO::class),
                    )
                ]
            )
        ]
    )
    fun createTransaction(@RequestBody @Valid transactionDTO: TransactionDTO): ResponseEntity<Transaction> {
        return ResponseEntity(transactionService.save(transactionDTO), HttpStatus.OK)
    }

    @PutMapping
    @Operation(
        summary = "Cancel transaction",
        description = "Cancel a transaction",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Transaction::class),
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ValidationErrorResponseDTO::class),
                    )
                ]
            )
        ]
    )
    fun cancelTransaction(@RequestBody @Valid transactionCancelDTO: TransactionCancelDTO): ResponseEntity<Transaction> {
        return ResponseEntity(transactionService.cancelTransaction(transactionCancelDTO), HttpStatus.OK)
    }
}
