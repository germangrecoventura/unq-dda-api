package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import ar.edu.unq.desapp.groupb.cryptop2p.service.UserService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserRequestDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
@Tag(name = "users", description = "Endpoints for managing users")
@RequestMapping("users")
class UserController(@Autowired val userService: UserService) {
    @PostMapping
    @Operation(
        summary = "Registers a user",
        description = "Registers a user using the email address as the unique identifier",
    )
    @ApiResponses(
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
    )
    fun createUser(@RequestBody userRequestDTO: UserRequestDTO): ResponseEntity<User> {
        var userSaved = userService.save(userRequestDTO)
        return ResponseEntity.ok().body(userSaved)
    }
}
