package ar.edu.unq.desapp.groupb.backenddesappapi.webservice

import ar.edu.unq.desapp.groupb.backenddesappapi.model.User
import ar.edu.unq.desapp.groupb.backenddesappapi.service.UserService
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.UserRequestDTO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping
    fun createTeacher(@RequestBody userRequestDTO: UserRequestDTO): User {
        return userService.save(userRequestDTO)
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