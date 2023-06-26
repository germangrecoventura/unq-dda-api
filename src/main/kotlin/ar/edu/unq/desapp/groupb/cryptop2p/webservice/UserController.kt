package ar.edu.unq.desapp.groupb.cryptop2p.webservice

import ar.edu.unq.desapp.groupb.cryptop2p.model.User
import ar.edu.unq.desapp.groupb.cryptop2p.security.JwtUtilService
import ar.edu.unq.desapp.groupb.cryptop2p.service.UserService
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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
    @Autowired
    private lateinit var jwtUtilService: JwtUtilService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/register")
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
    @SecurityRequirement(name = "bearerAuth")
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
    fun getUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAll()
        return ResponseEntity.ok().body(users)
    }

    @PostMapping("/login")
    @Operation(
        summary = "Login the user",
        description = "Login the user",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = TokenInfo::class)),
                    )
                ]
            )
        ]
    )
    fun authenticate(@Valid @RequestBody loginDTO: LoginDTO): ResponseEntity<TokenInfo> {
        val authentication =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginDTO.email, loginDTO.password))
        SecurityContextHolder.getContext().authentication = authentication

        val token = jwtUtilService.generateToken(authentication)
        return ResponseEntity.ok().body(TokenInfo(token))
    }
}
