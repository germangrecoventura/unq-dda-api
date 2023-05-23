package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.AssetPriceDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
@Tag(name = "assets", description = "Endpoints for managing assets")
@RequestMapping("assets")
@ApiResponses(
    value = [
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
class AssetController(private val assetService: AssetService) {
    @PostMapping
    @Operation(
        summary = "Registers an asset",
        description = "Registers an asset using the name as a unique identifier",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Asset::class),
                    )
                ]
            ),
        ]
    )
    fun createAsset(
        @RequestParam @NotBlank(message = "The name cannot be blank") @Pattern(
            regexp = "^[A-Z0-9-_.]{1,20}$",
            message = "The asset name is not in a valid format"
        ) assetName: String
    ): ResponseEntity<Asset> {
        val user = assetService.save(assetName)
        return ResponseEntity.ok().body(user)
    }

    @GetMapping("prices")
    @Operation(
        summary = "Lists latest assets prices",
        description = "Lists latest assets prices",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = AssetPriceDTO::class)),
                    )
                ]
            ),
        ]
    )
    fun getAssetsPrices(): ResponseEntity<Collection<AssetPriceDTO>> {
        val prices = assetService.getAssetPrices().map { AssetPriceDTO.fromModel(it) }
        return ResponseEntity.ok().body(prices)
    }
}
