package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.Transaction
import ar.edu.unq.desapp.groupb.cryptop2p.service.TransactionService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.TransactionCreateRequestDTO
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
@Tag(name = "transaction", description = "Endpoints for managing transactions")
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
    fun createTransaction(@RequestBody @Valid transactionDTO: TransactionCreateRequestDTO): ResponseEntity<Transaction> {
        return ResponseEntity(transactionService.save(transactionDTO), HttpStatus.OK)
    }

    @PutMapping("/transfer")
    @Operation(
        summary = "Transfer transaction",
        description = "Transfer a transaction",
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
    fun transferTransaction(@RequestBody @Valid transactionDTO: TransactionDTO): ResponseEntity<Transaction> {
        return ResponseEntity(transactionService.transferTransaction(transactionDTO), HttpStatus.OK)
    }

    @PutMapping("/confirm")
    @Operation(
        summary = "Confirm transfer to transaction",
        description = "Confirm transfer to transaction",
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
    fun confirmTransferTransaction(@RequestBody @Valid transactionDTO: TransactionDTO): ResponseEntity<Transaction> {
        return ResponseEntity(transactionService.confirmTransaction(transactionDTO), HttpStatus.OK)
    }

    @PutMapping("cancel")
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
    fun cancelTransaction(@RequestBody @Valid transactionDTO: TransactionDTO): ResponseEntity<Transaction> {
        return ResponseEntity(transactionService.cancelTransaction(transactionDTO), HttpStatus.OK)
    }
}
