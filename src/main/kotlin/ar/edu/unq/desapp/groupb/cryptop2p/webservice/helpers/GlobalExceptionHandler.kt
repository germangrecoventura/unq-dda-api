package ar.edu.unq.desapp.groupb.cryptop2p.webservice.helpers

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<*> {
        val errors = ex.bindingResult
            .fieldErrors
            .map { obj: FieldError -> ValidationErrorDTO(obj.field, obj.defaultMessage ?: "") }
        val body = ValidationErrorResponseDTO(errors)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<*> {
        val errors = ex.constraintViolations
            .map(ValidationErrorDTO::of)
        val body = ValidationErrorResponseDTO(errors)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ModelException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserEmailAddressAlreadyRegisteredException(ex: ModelException): ResponseEntity<*> {
        val error = ValidationErrorDTO(ex.source, ex.message ?: "")
        val body = ValidationErrorResponseDTO(listOf(error))
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(BadCredentialsException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleNoSuchElementException(ex: BadCredentialsException): ResponseEntity<*> {
        val error = ValidationErrorDTO("user", ex.message ?: "")
        val body = ValidationErrorResponseDTO(listOf(error))
        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUsernameNotFoundException(ex: UsernameNotFoundException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message ?: "")
    }

    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.message ?: "")
    }
}
