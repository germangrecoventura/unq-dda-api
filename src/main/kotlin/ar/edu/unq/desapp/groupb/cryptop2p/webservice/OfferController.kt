package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.Offer
import ar.edu.unq.desapp.groupb.cryptop2p.service.OfferService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferActiveDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.OfferRequestDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
@Tag(name = "offers", description = "Endpoints for managing offers")
@RequestMapping("offers")
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
@SecurityRequirement(name = "bearerAuth")
class OfferController(private val offerService: OfferService) {
    @PostMapping
    @Operation(
        summary = "Registers an offer",
        description = "Registers an offer for an already registered user",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Offer::class),
                    )
                ]
            ), ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [Content(
                    mediaType = "application/json", examples = [ExampleObject(
                        value = "{\n" +
                                "  \"errors\": [\n" +
                                "    {\n" +
                                "      \"source\": \"user\",\n" +
                                "      \"message\": \"Full authentication is required to access this resource\"\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}"
                    )]
                )
                ]
            )
        ]
    )
    fun createOffer(@RequestBody @Valid offerRequestDTO: OfferRequestDTO): ResponseEntity<Offer> {
        val offer = offerService.save(offerRequestDTO)
        return ResponseEntity.ok().body(offer)
    }

    @GetMapping
    @Operation(
        summary = "Lists all active offers",
        description = "Lists all active offers",
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
                                "    \"date\": \"2023-05-03T14:06:26.895991\",\n" +
                                "    \"asset\": {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"ALICEUSDT\",\n" +
                                "      \"prices\": [\n" +
                                "        {\n" +
                                "          \"id\": 1,\n" +
                                "          \"name\": \"ALICEUSDT\",\n" +
                                "          \"unitPrice\": 1.488,\n" +
                                "          \"created\": \"2023-05-03T14:06:08.090275\",\n" +
                                "          \"updated\": \"2023-05-03T14:06:08.090275\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"created\": \"2023-05-03T14:06:08.063272\"\n" +
                                "    },\n" +
                                "    \"quantity\": 2,\n" +
                                "    \"unitPrice\": 1.6,\n" +
                                "    \"totalAmount\": 100,\n" +
                                "    \"firstName\": \"Homero\",\n" +
                                "    \"lastName\": \"Simpson\",\n" +
                                "    \"operation\": \"BUY\",\n" +
                                "    \"sizeOperations\": 0,\n" +
                                "    \"rating\": \"Without operations\"\n" +
                                "  }\n" +
                                "]"
                    )]
                )]
            )
        ]
    )
    fun getOffersActive(@RequestParam(required = false) asset: String?): ResponseEntity<List<OfferActiveDTO>> {
        return ResponseEntity.ok().body(offerService.getActiveOffers(asset))
    }
}
