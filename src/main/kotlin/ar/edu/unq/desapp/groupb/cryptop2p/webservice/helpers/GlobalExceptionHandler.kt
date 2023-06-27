package ar.edu.unq.desapp.groupb.cryptop2p.webservice.helpers

import ar.edu.unq.desapp.groupb.cryptop2p.model.ModelException
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorDTO
import ar.edu.unq.desapp.groupb.cryptop2p.webservice.dto.ValidationErrorResponseDTO
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.BadCredentialsException
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
    protected fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<*> {
        var logger: Logger = LoggerFactory.getLogger(MethodArgumentNotValidException::class.java)
        val errors = ex.bindingResult
            .fieldErrors
            .map { obj: FieldError -> ValidationErrorDTO(obj.field, obj.defaultMessage ?: "") }

        val body = ValidationErrorResponseDTO(errors)
        errors.forEach { error -> logger.error(error.message) }
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<*> {
        var logger: Logger = LoggerFactory.getLogger(ConstraintViolationException::class.java)
        val errors = ex.constraintViolations
            .map(ValidationErrorDTO::of)
        val body = ValidationErrorResponseDTO(errors)
        errors.forEach { error -> logger.error(error.message) }
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(ModelException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserEmailAddressAlreadyRegisteredException(ex: ModelException): ResponseEntity<*> {
        var logger: Logger = LoggerFactory.getLogger(ModelException::class.java)
        val error = ValidationErrorDTO(ex.source, ex.message ?: "")
        logger.error(ex.message ?: "")
        val body = ValidationErrorResponseDTO(listOf(error))
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(BadCredentialsException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleNoSuchElementException(ex: BadCredentialsException): ResponseEntity<*> {
        var logger: Logger = LoggerFactory.getLogger(BadCredentialsException::class.java)
        val error = ValidationErrorDTO("user", ex.message ?: "")
        logger.error(ex.message ?: "")
        val body = ValidationErrorResponseDTO(listOf(error))
        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun userNotFound(ex: UsernameNotFoundException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message!!)
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleException(ex: AuthenticationCredentialsNotFoundException): ResponseEntity<*> {
        var logger: Logger = LoggerFactory.getLogger(BadCredentialsException::class.java)
        logger.error(ex.message!!)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.message!!)
    }
}
