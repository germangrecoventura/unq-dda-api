package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.model.AssetPrice
import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
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
class AssetController(private val assetService: AssetService) {
    @PostMapping
    @Operation(
        summary = "Registers a asset",
        description = "Registers a asset using the name as the unique identifier",
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
        summary = "Get a assets prices",
        description = "Returns all current asset prices",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [Content(
                    mediaType = "application/json", examples = [ExampleObject(
                        value = "[\n" +
                                "  {\n" +
                                "    \"id\": 1,\n" +
                                "    \"name\": \"ALICEUSDT\",\n" +
                                "    \"unitPrice\": 1.519,\n" +
                                "    \"created\": \"2023-04-24T12:42:49.475498\",\n" +
                                "    \"updated\": \"2023-04-24T12:42:49.475498\"\n" +
                                "  }\n" +
                                "]"
                    )]
                )]
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
    fun getAssetsPrices(): ResponseEntity<Set<AssetPrice>> {
        return ResponseEntity.ok().body(assetService.getAssetPrices())
    }
}
