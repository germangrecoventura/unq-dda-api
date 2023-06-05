package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import ar.edu.unq.desapp.groupb.cryptop2p.service.UserService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserCreateRequestDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.UserDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
@Tag(name = "users", description = "Endpoints for managing users")
@RequestMapping("users")
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
class UserController(private val userService: UserService) {
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
            )
        ]
    )
    fun createUser(@RequestBody userCreateRequestDTO: UserCreateRequestDTO): ResponseEntity<User> {
        val user = userService.save(userCreateRequestDTO)
        return ResponseEntity.ok().body(user)
    }

    @GetMapping
    @Operation(
        summary = "Lists all users",
        description = "Lists all users",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = UserDTO::class)),
                    )
                ]
            )
        ]
    )
    fun getUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAll()
        return ResponseEntity.ok().body(users)
    }
}
