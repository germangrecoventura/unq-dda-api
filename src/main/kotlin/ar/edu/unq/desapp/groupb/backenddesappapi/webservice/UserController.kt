package ar.edu.unq.desapp.groupb.backenddesappapi.webservice

import ar.edu.unq.desapp.groupb.backenddesappapi.service.UserService
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.MessageDTO
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("users")
class UserController(val userService: UserService) {
    @PostMapping
    @Operation(summary = "Register user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", content = [
                    (Content(
                        mediaType = "application/json", examples = [ExampleObject(
                            value = "{\n" +
                                    "  \"id\": 0,\n" +
                                    "  \"firstname\": \"string\",\n" +
                                    "  \"lastname\": \"string\",\n" +
                                    "  \"emailAddress\": \"string@string\",\n" +
                                    "  \"address\": \"string\",\n" +
                                    "  \"password\": \"string\",\n" +
                                    "  \"cvump\": \"string\",\n" +
                                    "  \"cryptoWalletAddress\": 0\n" +
                                    "}"
                        )]
                    ))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [Content(
                    mediaType = "application/json", examples = [ExampleObject(
                        value = "{\n" +
                                "  \"message\": \"error explanation\"\n" +
                                "}"
                    )]
                )]
            )]
    )
    fun createUser(@RequestBody userRequestDTO: UserRequestDTO): ResponseEntity<Any> {
        return try {
            var userSaved = userService.save(userRequestDTO)
            ResponseEntity.ok().body(userSaved)
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(MessageDTO(e.message!!))
        }
    }
}


@ControllerAdvice
class Handler {
    @ExceptionHandler(java.lang.Exception::class)
    fun handle(
        ex: java.lang.Exception?,
        request: HttpServletRequest?, response: HttpServletResponse?
    ): ResponseEntity<Any> {
        return if (ex is NullPointerException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        } else
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex?.localizedMessage!!)
    }
}
