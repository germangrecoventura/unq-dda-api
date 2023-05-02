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
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
@Tag(name = "offers", description = "Endpoints for managing offers")
@RequestMapping("offers")
class OfferController(private val offerService: OfferService) {
    @PostMapping
    @Operation(
        summary = "Registers a offers",
        description = "Register an offer with an already registered user",
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
    fun createOffer(@RequestBody @Valid offerRequestDTO: OfferRequestDTO): ResponseEntity<Offer> {
        val offer = offerService.save(offerRequestDTO)
        return ResponseEntity.ok().body(offer)
    }

    @GetMapping
    @Operation(
        summary = "Get a offers active",
        description = "Returns all offers active",
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
                                "    \"user\": {\n" +
                                "      \"id\": 1,\n" +
                                "      \"emailAddress\": \"homero.simpson@springfield.com\",\n" +
                                "      \"firstName\": \"Homero\",\n" +
                                "      \"lastName\": \"Simpson\",\n" +
                                "      \"address\": \"Evergreen 123\",\n" +
                                "      \"cvu\": \"0011223344556677889900\",\n" +
                                "      \"cryptoWalletAddress\": \"12345678\"\n" +
                                "    },\n" +
                                "    \"isActive\": true,\n" +
                                "    \"asset\": {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"ALICEUSDT\",\n" +
                                "      \"prices\": [\n" +
                                "        {\n" +
                                "          \"id\": 1,\n" +
                                "          \"name\": \"ALICEUSDT\",\n" +
                                "          \"unitPrice\": 1.532,\n" +
                                "          \"created\": \"2023-04-24T15:28:27.278749\",\n" +
                                "          \"updated\": \"2023-04-24T15:28:27.278749\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"created\": \"2023-04-24T15:28:27.255748\"\n" +
                                "    },\n" +
                                "    \"quantity\": 0,\n" +
                                "    \"unitPrice\": 0,\n" +
                                "    \"totalAmount\": 0,\n" +
                                "    \"operation\": \"BUY\",\n" +
                                "    \"created\": \"2023-04-24T15:29:53.102573\"\n" +
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
    fun getOffersActive(): ResponseEntity<List<OfferActiveDTO>> {
        return ResponseEntity.ok().body(offerService.getOffersActive())
    }
}
