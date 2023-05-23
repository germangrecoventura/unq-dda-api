package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.TradedVolumeReport
import ar.edu.unq.desapp.groupb.cryptop2p.service.ReportService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.View
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@CrossOrigin
@Tag(name = "reports", description = "Endpoints for managing reports")
@RequestMapping("reports")
class ReportController(private val reportService: ReportService) {

    @GetMapping("{userId}/traded-volume")
    @Operation(
        summary = "Generates a traded volume report",
        description = "Generates a traded volume report for the given user",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TradedVolumeReport::class),
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
    @JsonView(View.Public::class)
    fun reportTradingVolume(
        @PathVariable userId: Long,
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate
    ): ResponseEntity<TradedVolumeReport> {
        val report = reportService.generateTradedVolumeReport(userId, startDate, endDate)
        return ResponseEntity.ok().body(report)
    }
    
}