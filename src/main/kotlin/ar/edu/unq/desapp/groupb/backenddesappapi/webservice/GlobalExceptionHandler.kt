package ar.edu.unq.desapp.groupb.backenddesappapi.webservice

import ar.edu.unq.desapp.groupb.backenddesappapi.service.UserEmailAddressAlreadyRegisteredException
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.ValidationErrorDTO
import ar.edu.unq.desapp.groupb.backenddesappapi.webservice.dtos.ValidationErrorResponseDTO
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(UserEmailAddressAlreadyRegisteredException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserEmailAddressAlreadyRegisteredException(ex: UserEmailAddressAlreadyRegisteredException): ResponseEntity<*> {
        val error = ValidationErrorDTO(ex.source, ex.message ?: "")
        val body = ValidationErrorResponseDTO(listOf(error))
        return ResponseEntity.badRequest().body(body)
    }
}