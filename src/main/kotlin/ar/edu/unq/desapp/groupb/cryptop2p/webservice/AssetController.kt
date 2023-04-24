package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.Asset
import ar.edu.unq.desapp.groupb.cryptop2p.service.AssetService
import io.swagger.v3.oas.annotations.Operation
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
            /* @ApiResponses(
                 value = [
                     ApiResponse(
                         responseCode = "200",
                         description = "success",
                         content = [
                             Content(
                                 mediaType = "application/json",
                                 schema = Schema(implementation = User::class),
                             )
                         ]
                     ),
                     ApiResponse(
                         responseCode = "400",
                         description = "bad request",
                         content = [
                             Content(
                                 mediaType = "application/json",
                                 schema = Schema(implementation = ValidationErrorResponseDTO::class),
                             )
                         ]
                     )
                 ]
             )*/
    fun createAsset(@RequestParam @NotBlank @Pattern(regexp ="^[A-Z0-9-_.]{1,20}$" ) assetName: String): ResponseEntity<Asset> {
        val user = assetService.save(assetName)
        return ResponseEntity.ok().body(user)
    }
}
